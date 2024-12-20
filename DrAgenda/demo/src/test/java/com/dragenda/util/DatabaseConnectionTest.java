package com.dragenda.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.cdimascio.dotenv.Dotenv;



public class DatabaseConnectionTest {

    private static Dotenv dotenvMock;

    @BeforeAll
    public static void setup() {
        dotenvMock = mock(Dotenv.class);
        when(dotenvMock.get("DB_URL")).thenReturn("jdbc:mysql://localhost:3306/testdb");
        when(dotenvMock.get("DB_USER")).thenReturn("testuser");
        when(dotenvMock.get("DB_PASSWORD")).thenReturn("testpassword");
    }

    @Test
    public void testGetConnectionSuccess() {
        try (MockedStatic<Dotenv> mockedDotenv = Mockito.mockStatic(Dotenv.class);
            MockedStatic<DriverManager> mockedDriverManager = Mockito.mockStatic(DriverManager.class)) {

            mockedDotenv.when(Dotenv::load).thenReturn(dotenvMock);
            Connection mockConnection = mock(Connection.class);
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            Connection connection = DatabaseConnection.getConnection();
            assertNotNull(connection);
            assertEquals(mockConnection, connection);
        }
    }

    @Test
    public void testGetConnectionClassNotFoundException() {
        try (MockedStatic<Dotenv> mockedDotenv = Mockito.mockStatic(Dotenv.class)) {

            mockedDotenv.when(Dotenv::load).thenReturn(dotenvMock);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                try (MockedStatic<Class> mockedClass = Mockito.mockStatic(Class.class)) {
                    mockedClass.when(() -> Class.forName("com.mysql.cj.jdbc.Driver"))
                            .thenThrow(ClassNotFoundException.class);
                    DatabaseConnection.getConnection();
                }
            });
            assertNotNull(exception);
        }
    }

    @Test
    public void testGetConnectionSQLException() {
        try (MockedStatic<Dotenv> mockedDotenv = Mockito.mockStatic(Dotenv.class);
            MockedStatic<DriverManager> mockedDriverManager = Mockito.mockStatic(DriverManager.class)) {

            mockedDotenv.when(Dotenv::load).thenReturn(dotenvMock);
            mockedDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenThrow(SQLException.class);

            RuntimeException exception = assertThrows(RuntimeException.class, DatabaseConnection::getConnection);
            assertNotNull(exception);
        }
    }
}