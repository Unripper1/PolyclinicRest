package course.polyclinic.services;

import course.polyclinic.components.Doctor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {
    private  final DoctorService doctorService;
    private final AppointmentService appointmentService;
    @Scheduled(fixedRate = 360000)
    public void updateSchedule() {
        LocalDate date = LocalDate.now();
        LocalTime time=LocalTime.now();
        doctorService.updateSchedule(time,date);
    }
    @Scheduled(fixedRate = 360000)
    public void updateApp() {
        LocalDate date = LocalDate.now();
        appointmentService.updateApp(date);
    }
    @Scheduled(cron = "* * * 15 * *")
    public void updateSch() {
        LocalDate date = LocalDate.now();
        System.out.println(1);
        for(Doctor doctor:doctorService.getAll()) {
            doctorService.newSchedule(date,doctor);
        }
    }

}
