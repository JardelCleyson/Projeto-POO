@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    public Appointment scheduleAppointment(Long doctorId, Long clinicId, LocalDateTime appointmentDate) throws Exception {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new Exception("Médico não encontrado"));

        Clinic clinic = clinicRepository.findById(clinicId)
            .orElseThrow(() -> new Exception("Clínica não encontrada"));

        // Verifica se o médico está disponível nesse horário
        List<Appointment> existingAppointments = appointmentRepository.findByDoctorAndAppointmentDateBetween(
            doctor, 
            appointmentDate.minusMinutes(30), 
            appointmentDate.plusMinutes(30)
        );

        if (!existingAppointments.isEmpty()) {
            throw new Exception("Médico já possui uma consulta agendada nesse horário.");
        }

        // Cria e salva a consulta
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setClinic(clinic);
        appointment.setAppointmentDate(appointmentDate);

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        return doctor != null ? appointmentRepository.findByDoctorAndAppointmentDateBetween(doctor, LocalDateTime.now(), LocalDateTime.now().plusDays(30)) : new ArrayList<>();
    }
}
