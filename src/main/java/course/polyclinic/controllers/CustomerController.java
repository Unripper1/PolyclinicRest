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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final DoctorService doctorService;
    private final CustomerService customerService;
    private final AppointmentService appointmentService;
    private final UserService userService;

    @GetMapping("/lk")
    public String lk(Model model){

        model.addAttribute("appointments", userService.loadUserByUsername(userService.getCurrentUsername()).getCustomer().getAppointments());
        model.addAttribute("customer",userService.loadUserByUsername(userService.getCurrentUsername()).getCustomer());
        return "lk_с";
    }
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
    @RequestMapping("/delapp/{id}")
    public String del(@PathVariable("id") long id) {
        if (appointmentService.find(id).getCustomer() == userService.loadUserByUsername(userService.getCurrentUsername()).getCustomer() && appointmentService.find(id).getStatus() == Status.WAITING) {
            appointmentService.find(id).setStatus(Status.CANCEL);
            appointmentService.save(appointmentService.find(id));
            FreeTime time = new FreeTime().setTime(appointmentService.find(id).getTime());
            boolean b = false;
            for (FreeMeet meet : appointmentService.find(id).getDoctor().getFreeMeets()) {
                if (meet.getDate().isEqual(appointmentService.find(id).getDate())) {
                    for(int i=0;i<meet.getFreeTimes().size();i++) {
                        if(meet.getFreeTimes().get(i).getTime().isAfter(time.getTime())) {
                            meet.getFreeTimes().add(i,time);
                            time.setFreeMeet(meet);
                            break;
                        }
                        if (i==meet.getFreeTimes().size()-1){
                            meet.getFreeTimes().add(time);
                            time.setFreeMeet(meet);
                        }
                    }
                    for(FreeTime time1:meet.getFreeTimes())
                        System.out.println(time1.getTime());
                    doctorService.addMeetsTime(time);
                    doctorService.addMeets(meet);
                    b = true;
                }
            }
            if (b == false) {
                FreeMeet freeMeet = new FreeMeet();
                freeMeet.getFreeTimes().add(time);
                time.setFreeMeet(freeMeet);
                freeMeet.setDoctor(appointmentService.find(id).getDoctor());
                doctorService.addMeets(freeMeet);
            }
            return "redirect:/customer/lk";
        } else return "redirect:/";
    }
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
