package course.polyclinic.controllers;

import course.polyclinic.components.*;
import course.polyclinic.enums.Status;
import course.polyclinic.services.AppointmentService;
import course.polyclinic.services.CustomerService;
import course.polyclinic.services.DoctorService;
import course.polyclinic.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
//@PreAuthorize("hasAuthority('ROLE_USER')")
public class CustomerController {
    private final DoctorService doctorService;
    private final CustomerService customerService;
    private final AppointmentService appointmentService;
    private final UserService userService;

//    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/lk")
    public String lk(Model model){
        model.addAttribute("appointments",userService.loadUserByUsername(userService.getCurrentUsername()).getCustomer().getAppointments());
        return "lk_с";
    }
//    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/order")
    public String order(Model model){
        model.addAttribute("doctors",doctorService.getAll());
        return "order";
    }
    @GetMapping("/results")
    public String results(Model model){
        model.addAttribute("appointments",userService.loadUserByUsername(userService.getCurrentUsername()).getCustomer().getAppointments());
        return "results";
    }
//    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/setAppointment")
    public String setAppointment(@RequestParam("id") Long id,@RequestParam("date") Long dateId,@RequestParam("time") Long timeId){
        User user= userService.loadUserByUsername(userService.getCurrentUsername());
        Customer customer=user.getCustomer();
        Doctor doctor=doctorService.getById(id);
        FreeMeet freeMeet=doctorService.getMeetById(dateId);
        LocalDate date=freeMeet.getDate();
        FreeTime time=doctorService.getTimeById(timeId);
        LocalTime time2=time.getTime();
        System.out.println(date+" "+time2);

        Appointment appointment=new Appointment().setDoctor(doctor).setCustomer(customer).setDate(date).setTime(time2).setStatus(Status.WAITING);
        appointmentService.save(appointment);
        doctorService.delMeet(time);
        return "redirect:/customer/order";
    }
}
