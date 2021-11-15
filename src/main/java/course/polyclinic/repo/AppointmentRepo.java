package course.polyclinic.repo;

import course.polyclinic.components.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    public  Appointment findAppointmentById(Long id);
}
