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


}
