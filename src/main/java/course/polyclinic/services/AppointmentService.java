package course.polyclinic.services;

import course.polyclinic.components.Appointment;
import course.polyclinic.repo.AppointmentRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class AppointmentService {
    public  final AppointmentRepo appointmentRepo;
    public void save(Appointment appointment){
        appointmentRepo.save(appointment);
    }
    public List<Appointment> getAppointments(){
        return appointmentRepo.findAll();
    }
    public Appointment find(Long id){
        return appointmentRepo.findAppointmentById(id);
    }

}
