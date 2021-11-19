package course.polyclinic.controllers;

import course.polyclinic.DTO.CustomerDTO;
import course.polyclinic.DTO.DoctorDTO;
import course.polyclinic.components.*;
import course.polyclinic.services.DoctorService;
import course.polyclinic.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final DoctorService doctorService;
    @GetMapping("/lk")
    public String lk() {
        return "lk_adm";
    }
    @GetMapping("/regdoc")
    public String regdoc() {
        return "regdoc";
    }
    @PostMapping("/regdoc")
    public String registration(DoctorDTO doctorDTO, Model model) {
        try {
            User user=new User().setLogin(doctorDTO.getLogin()).setPassword(doctorDTO.getPassword());
            LocalDate date=LocalDate.of(doctorDTO.getYear(),doctorDTO.getMonth(),doctorDTO.getDay());
            Doctor doctor=new Doctor().setGender(doctorDTO.getGender())
                    .setFirstName(doctorDTO.getFirstName()).setLastName(doctorDTO.getLastName())
                    .setNumber(doctorDTO.getNumber())
                    .setCabinet(doctorDTO.getCabinet())
                    .setBirthDate(date).setSpec(doctorDTO.getSpec()).setUser(user);
            userService.saveUser(user,"ROLE_DOCTOR");
            doctorService.add(doctor);
            List<FreeMeet> freeMeetList=new ArrayList<>();
        for(int i=0;i<=10;i++){
            FreeMeet freeMeet= new FreeMeet().setDate(LocalDate.now().plusDays(i+1));
            List<FreeTime> freeTimeList=new ArrayList<>();
            freeMeet.setDoctor(doctor);
            doctorService.addMeets(freeMeet);
            for (int j=0;j<=10;j++){
                FreeTime freeTime=new FreeTime();
                freeTime.setFreeMeet(freeMeet);
                freeTime.setTime(LocalTime.of(10+j,0));
                freeTimeList.add(freeTime);
                doctorService.addMeetsTime(freeTime);
            }
            freeMeet.setFreeTimes(freeTimeList);
            doctorService.addMeets(freeMeet);
        }
            return "redirect:/admin/lk";
       } catch (Exception e) {
           model.addAttribute("status","Ошибка");
           return "regdoc";
       }
    }
}
