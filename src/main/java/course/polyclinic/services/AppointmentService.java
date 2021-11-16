package course.polyclinic.services;

import course.polyclinic.components.Appointment;
import course.polyclinic.components.FreeMeet;
import course.polyclinic.enums.Status;
import course.polyclinic.repo.AppointmentRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
    public void updateApp(LocalDate date) {
        List<Appointment> appointments = appointmentRepo.findAll();
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getDate().isBefore(date)) {
                appointments.get(i).setStatus(Status.SKIPPING);
                appointmentRepo.save(appointments.get(i));
            }
        }
    }

}
