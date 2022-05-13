package course.polyclinic.controllers;

import course.polyclinic.components.*;
import course.polyclinic.enums.Status;
import course.polyclinic.services.AppointmentService;
import course.polyclinic.services.CustomerService;
import course.polyclinic.services.DoctorService;
import course.polyclinic.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
//@CrossOrigin("*")
public class CustomerController {
    private final DoctorService doctorService;
    private final CustomerService customerService;
    private final AppointmentService appointmentService;
    private final UserService userService;

    @GetMapping("/appointments")
    @PreAuthorize("hasRole('USER')")
    public List<Appointment> appointments(){
        return  userService.loadUserByUsername(userService.getCurrentUsername()).getCustomer().getAppointments().stream().filter(appointment -> appointment.getStatus()==Status.WAITING).collect(Collectors.toList());
    }
    @GetMapping("/order")
    public List<Doctor> getOrder(){
         return doctorService.getAll();
    }

    @GetMapping("/user")
    public Set<Role> getUserRole(){
        return userService.loadUserByUsername(userService.getCurrentUsername()).getRoles();
    }

    @PostMapping("/order")
    @PreAuthorize("hasRole('USER')")
    public void postOrder(@RequestParam("id") Long id,@RequestParam("date") Long dateId,@RequestParam("time") Long timeId){
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
    }
    @GetMapping("/results")
    public List<Appointment> results(){
        return userService.loadUserByUsername(userService.getCurrentUsername()).getCustomer().getAppointments().stream().filter(appointment -> appointment.getStatus()==Status.SUCCESSFUL).collect(Collectors.toList());
    }

    @DeleteMapping("/delapp/{id}")
    @PreAuthorize("hasRole('USER')")
    public void del(@PathVariable("id") long id) {
        System.out.println("ok");
        if (appointmentService.find(id).getCustomer() == userService.loadUserByUsername(userService.getCurrentUsername()).getCustomer() && appointmentService.find(id).getStatus() == Status.WAITING) {
            appointmentService.find(id).setStatus(Status.CANCEL);
            appointmentService.save(appointmentService.find(id));
            FreeTime time = new FreeTime().setTime(appointmentService.find(id).getTime());
            boolean b = false;
            for (FreeMeet meet : appointmentService.find(id).getDoctor().getFreeMeets()) {
                if (meet.getDate().isEqual(appointmentService.find(id).getDate())) {
                    for (int i = 0; i < meet.getFreeTimes().size(); i++) {
                        if (meet.getFreeTimes().get(i).getTime().isAfter(time.getTime())) {
                            meet.getFreeTimes().add(i, time);
                            time.setFreeMeet(meet);
                            break;
                        }
                        if (i == meet.getFreeTimes().size() - 1) {
                            meet.getFreeTimes().add(time);
                            time.setFreeMeet(meet);
                        }
                    }
                    for (FreeTime time1 : meet.getFreeTimes())
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
        }
    }
}
