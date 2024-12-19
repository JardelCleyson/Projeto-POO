# Projeto POO
# Sistema de Agendamento de Consultas Médicas

## Descrição Geral
Este projeto implementa um sistema de agendamento de consultas médicas, desenvolvido em Java utilizando os princípios da Programação Orientada a Objetos (POO). Ele oferece funcionalidades para gerenciar médicos, pacientes, unidades de saúde e agendamentos, organizando e automatizando processos relacionados à gestão de consultas médicas.

A estrutura do sistema foi organizada em camadas distintas para facilitar a manutenção e escalabilidade. O código utiliza o padrão DAO para acesso ao banco de dados e inclui testes automatizados para garantir a confiabilidade das funcionalidades.

---

## Principais Funcionalidades

1. **Cadastro de Pacientes e Médicos:**
   - Permite o registro de informações pessoais, como nome, CPF, especialidade médica (para médicos).

2. **Gerenciamento de Unidades de Saúde:**
   - Armazena informações sobre as unidades, como endereço, horário e dias de funcionamento.

3. **Agendamento de Consultas:**
   - Facilita a marcação de consultas médicas, vinculando médicos, pacientes e unidades, respeitando horários disponíveis.

4. **Histórico de Consultas:**
   - Mantém registros das consultas realizadas, datas de atendimento, também possibilitando incluir patologias diagnosticadas.

5. **Conexão com Banco de Dados:**
   - Gerencia a persistência de dados utilizando classes para conexão e inicialização do banco de dados.

---

## Estrutura do Projeto

O projeto segue a seguinte estrutura de diretórios:

```
src
 ┣ main
 ┃ ┗ java
 ┃ ┃ ┗ com
 ┃ ┃ ┃ ┗ dragenda
 ┃ ┃ ┃ ┃ ┣ dao
 ┃ ┃ ┃ ┃ ┃ ┣ AgendamentoDAO.java
 ┃ ┃ ┃ ┃ ┃ ┣ MedicoDAO.java
 ┃ ┃ ┃ ┃ ┃ ┣ PacienteDAO.java
 ┃ ┃ ┃ ┃ ┃ ┗ UnidadeDAO.java
 ┃ ┃ ┃ ┃ ┣ model
 ┃ ┃ ┃ ┃ ┃ ┣ Agendamento.java
 ┃ ┃ ┃ ┃ ┃ ┣ Medico.java
 ┃ ┃ ┃ ┃ ┃ ┣ Paciente.java
 ┃ ┃ ┃ ┃ ┃ ┣ Pessoa.java
 ┃ ┃ ┃ ┃ ┃ ┗ Unidade.java
 ┃ ┃ ┃ ┃ ┣ util
 ┃ ┃ ┃ ┃ ┃ ┣ DatabaseConnection.java
 ┃ ┃ ┃ ┃ ┃ ┗ DatabaseInitializer.java
 ┃ ┃ ┃ ┃ ┗ view
 ┃ ┃ ┃ ┃ ┃ ┗ Main.java
 ┗ test
 ┃ ┗ java
 ┃ ┃ ┣ AgendamentoTest.java
 ┃ ┃ ┣ MainTest.java
 ┃ ┃ ┣ MedicoTest.java
 ┃ ┃ ┣ PacienteTest.java
 ┃ ┃ ┣ PessoaTest.java
 ┃ ┃ ┗ UnidadeTest.java
```

---

## Requisitos

- **Java 11 ou superior**
- **Maven**
- **Banco de Dados Relacional (Ex.: MySQL)**

---

## Como Executar o Projeto

### Passos para Configuração

1. Clone este repositório em sua máquina local:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   ```

2. Navegue até o diretório do projeto:
   ```bash
   cd <DIRETORIO_DO_PROJETO>
   ```

3. Configure o arquivo `DatabaseConnection.java` com as credenciais do seu banco de dados:
   ```java
   ```O arquivo 'DatabaseConnection.java' realiza a leitura de um .env presente na arvore de aquivos. 
   ```

4. Certifique-se de que o banco de dados esteja rodando e que as tabelas sejam inicializadas corretamente. Você pode configurar isso no método `DatabaseInitializer.java`.

### Comandos para Executar

1. Compile e instale o projeto:
   ```bash
   mvn clean install
   ```

2. Execute o sistema:
   ```bash
   mvn exec:java 
   ```

---

## Testes Automatizados

Este projeto inclui testes unitários para garantir o correto funcionamento das funcionalidades. Os testes estão localizados no diretório `src/test/java` e podem ser executados com o Maven:

```bash
mvn test
```

Os principais casos de teste incluem:
- **AgendamentoTest.java**: Valida a criação e a manipulação de registros de agendamento.
- **MedicoTest.java**: Testa o gerenciamento de médicos e seus atributos específicos.
- **PacienteTest.java**: Verifica a integridade dos dados dos pacientes e do histórico médico.
- **PessoaTest.java**: Valida métodos genéricos de CPF e atributos compartilhados.
- **UnidadeTest.java**: Garante a funcionalidade relacionada à gestão de unidades de saúde.
- **MainTest.java**: Testa o fluxo principal da aplicação e as interações no menu.

---