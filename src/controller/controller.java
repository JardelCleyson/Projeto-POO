package controller;

public class controller {
    
}
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/schedule")
    public ResponseEntity<Appointment> scheduleAppointment(@RequestParam Long doctorId, @RequestParam Long clinicId, @RequestParam String date) {
        try {
            LocalDateTime appointmentDate = LocalDateTime.parse(date);
            Appointment appointment = appointmentService.scheduleAppointment(doctorId, clinicId, appointmentDate);
            return ResponseEntity.ok(appointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        return ResponseEntity.ok(appointments);
    }
}
