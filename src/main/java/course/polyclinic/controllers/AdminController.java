package course.polyclinic.controllers;

import course.polyclinic.DTO.CustomerDTO;
import course.polyclinic.DTO.DoctorDTO;
import course.polyclinic.components.Customer;
import course.polyclinic.components.Doctor;
import course.polyclinic.components.User;
import course.polyclinic.services.DoctorService;
import course.polyclinic.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

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
            LocalDate date=LocalDate.of(doctorDTO.getYear(),doctorDTO.getMonth(),doctorDTO.getDay());
            Doctor doctor=new Doctor().setGender(doctorDTO.getGender())
                    .setFirstName(doctorDTO.getFirstName()).setLastName(doctorDTO.getLastName())
                    .setNumber(doctorDTO.getNumber())
                    .setCabinet(doctorDTO.getCabinet())
                    .setBirthDate(date).setSpec(doctorDTO.getSpec());
            User user=new User().setLogin(doctorDTO.getLogin()).setDoctor(doctor).setPassword(doctorDTO.getPassword());
            userService.saveUser(user,"ROLE_DOCTOR");
            doctorService.add(doctor);
            return "redirect:/admin/lk";
        } catch (Exception e) {
            model.addAttribute("status","Ошибка");
            return "regdoc";
        }
    }
}
