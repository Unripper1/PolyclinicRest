package course.polyclinic.controllers;

import course.polyclinic.DTO.ResultDTO;
import course.polyclinic.components.Appointment;
import course.polyclinic.components.Result;
import course.polyclinic.enums.Status;
import course.polyclinic.repo.ResultRepo;
import course.polyclinic.services.AppointmentService;
import course.polyclinic.services.CustomerService;
import course.polyclinic.services.DoctorService;
import course.polyclinic.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctor")
//@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
public class DoctorController {
    private final DoctorService doctorService;
    private final CustomerService customerService;
    private final AppointmentService appointmentService;
    private final UserService userService;
    private final ResultRepo resultRepo;
    @GetMapping("/appointments")
    @PreAuthorize("hasAuthority('ROLE_DOCTOR')")

    public List<Appointment> getAppointments(){
        return userService.loadUserByUsername(userService.getCurrentUsername()).getDoctor().getAppointments().stream().filter(appointment -> appointment.getStatus()==Status.WAITING).collect(Collectors.toList());
    }

    @GetMapping("/appointment/{id}")
    @PreAuthorize("hasAuthority('ROLE_DOCTOR')")

    public List<Appointment> app(@PathVariable("id") long id){
        return appointmentService.find(id).getCustomer().getAppointments().stream().filter(appointment -> appointment.getStatus()==Status.SUCCESSFUL).collect(Collectors.toList());
    }
@PostMapping("/app/{id}")
@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
public void appPost(@PathVariable("id") long id, @RequestBody ResultDTO resultDTO){
        System.out.println(resultDTO.getDescription());
        System.out.println(resultDTO.getDiagnosis());
        System.out.println(resultDTO.getTherapy());

    Result result=new Result().setDiagnosis(resultDTO.getDiagnosis()).setTherapy(resultDTO.getTherapy()).setDescription(resultDTO.getDescription());
    resultRepo.save(result.setAppointment(appointmentService.find(id)));
    appointmentService.save(appointmentService.find(id).setStatus(Status.SUCCESSFUL));
}

}
