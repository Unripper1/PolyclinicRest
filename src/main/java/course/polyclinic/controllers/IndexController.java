package course.polyclinic.controllers;

import course.polyclinic.DTO.CustomerDTO;
import course.polyclinic.components.*;
import course.polyclinic.enums.Gender;
import course.polyclinic.enums.Specialization;
import course.polyclinic.services.CustomerService;
import course.polyclinic.services.DoctorService;
import course.polyclinic.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final CustomerService customerService;
    private final UserService userService;
    private final DoctorService doctorService;
    @GetMapping("/")
    public String home(Model model){
        try {
            UserDetails user = userService.loadUserByUsername(userService.getCurrentUsername());
            Role role = (Role) user.getAuthorities().iterator().next();
            model.addAttribute("status", role.getName());
            return "index";
        }
        catch (Exception e){
            model.addAttribute("status","no");
            return "index";
        }
    }
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @GetMapping("/login")
    public String login(){
        return "redirect:/";
    }
    @PostMapping("/registration")
    public String registrationP(@Valid CustomerDTO customerDTO, Model model) {
        try {
            LocalDate date=LocalDate.of(customerDTO.getYear(),customerDTO.getMonth(),customerDTO.getDay());
            Customer customer=new Customer().setGender(customerDTO.getGender())
                    .setFirstName(customerDTO.getFirstName()).setLastName(customerDTO.getLastName())
                    .setNumber(customerDTO.getNumber())
                    .setInsurance(customerDTO.getInsurance())
                    .setBirthDate(date);
            User user=new User().setLogin(customerDTO.getLogin()).setCustomer(customer).setPassword(customerDTO.getPassword());
            userService.saveUser(user,"ROLE_USER");
            customerService.saveCustomer(customer);
            return "redirect:/customer/lk";
        } catch (Exception e) {
            model.addAttribute("status","Ошибка");
            return "registration";
        }
    }
}
