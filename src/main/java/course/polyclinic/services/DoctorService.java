package course.polyclinic.services;


import course.polyclinic.components.Doctor;
import course.polyclinic.components.FreeMeet;
import course.polyclinic.components.FreeTime;
import course.polyclinic.repo.DoctorRepo;
import course.polyclinic.repo.FreeMeetRepo;
import course.polyclinic.repo.TimeRepo;
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
public class DoctorService {
    private final DoctorRepo doctorRepo;
    private final FreeMeetRepo freeMeetRepo;
    private final TimeRepo timeRepo;
    public void add(Doctor doctor){
        doctorRepo.save(doctor);
    }
    public void addMeets(FreeMeet freeMeet){
        freeMeetRepo.save(freeMeet);
    }
    public void addMeetsTime(FreeTime freeTime){
        timeRepo.save(freeTime);
    }
    public List<Doctor> getAll(){
        return doctorRepo.findAll();
    }
    public Doctor getById(Long id){
        return doctorRepo.findDoctorById(id);
    }
    public FreeMeet getMeetById(Long id){
        return freeMeetRepo.findFreeMeetById(id);
    }
    public FreeTime getTimeById(Long id){
        return timeRepo.findFreeTimeById(id);
    }
    public void delMeet(FreeTime time){
        timeRepo.delete(time);
    }
    public void updateSchedule(LocalTime time, LocalDate date) {
        List<FreeMeet> meets = new ArrayList<>();
//        for(FreeMeet meet:freeMeetRepo.findAll())
//            if(meet.getDate().isBefore(date) || meet.getDate().isEqual(date))
//                meets.add(meet);
//        for(Doctor doctor: doctorRepo.findAll()) {
//            boolean b = false;
//            for (FreeMeet meet : doctor.getFreeMeets())
//                if (meet.getDate().isEqual(date.plusDays(7)))
//                    b=true;
//            if(b==true)
//
//        }
        for (int i = 0; i < meets.size(); i++) {
            for (int j = 0; j < meets.get(i).getFreeTimes().size(); j++) {
                if (meets.get(i).getFreeTimes().get(j).getTime().isBefore(time) || meets.get(i).getDate().isBefore(date))
                    timeRepo.delete(meets.get(i).getFreeTimes().get(j));
            }
            if(meets.get(i).getFreeTimes().size()==0){
                freeMeetRepo.delete(meets.get(i));
                System.out.println("ok");
            }
        }
    }

}
