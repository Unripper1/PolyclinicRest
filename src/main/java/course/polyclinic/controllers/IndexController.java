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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
//        LocalDate date= LocalDate.now();
//        Doctor doctor=new Doctor().setBirthDate(LocalDate.of(2000,11,2)).
//                setFirstName("Test").setLastName("Best").setGender(Gender.MALE).setNumber("8900000000").setCabinet(300).setSpec(Specialization.SURGEON);
//        doctorService.add(doctor);
//        List<FreeMeet> freeMeetList=new ArrayList<>();
//        for(int i=0;i<=10;i++){
//            FreeMeet freeMeet= new FreeMeet().setDate(LocalDate.now().plusDays(i+1));
//            List<FreeTime> freeTimeList=new ArrayList<>();
//            freeMeet.setDoctor(doctor);
//            doctorService.addMeets(freeMeet);
//            for (int j=0;j<=10;j++){
//                FreeTime freeTime=new FreeTime();
//                freeTime.setFreeMeet(freeMeet);
//                freeTime.setTime(LocalTime.of(10+j,0));
//                freeTimeList.add(freeTime);
//                doctorService.addMeetsTime(freeTime);
//            }
//            freeMeet.setFreeTimes(freeTimeList);
//            doctorService.addMeets(freeMeet);
//        }
//        doctor.setFreeMeets(freeMeetList);
//        User user=new User().setPassword("0000").setLogin("doc@doc.com").setDoctor(doctor);
//        userService.saveUser(user,"ROLE_DOCTOR");
//        doctorService.add(doctor);
        UserDetails user=userService.loadUserByUsername(userService.getCurrentUsername());
        Role role=(Role)user.getAuthorities().iterator().next();
        if(role.getName().equals("ROLE_USER"))
        System.out.println(role.getName());
        model.addAttribute("status",role.getName().toString());
        return "index";
    }
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String registrationP(CustomerDTO customerDTO, Model model) {
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
