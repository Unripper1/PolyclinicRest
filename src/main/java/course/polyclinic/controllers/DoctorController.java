package course.polyclinic.controllers;

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

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctor")
//@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
public class DoctorController {
    private final DoctorService doctorService;
    private final CustomerService customerService;
    private final AppointmentService appointmentService;
    private final UserService userService;
    @GetMapping("/lk")
//    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public String lk(Model model){
        model.addAttribute("appointments",userService.loadUserByUsername(userService.getCurrentUsername()).getDoctor().getAppointments());
        return "lk_doc";
    }
}
