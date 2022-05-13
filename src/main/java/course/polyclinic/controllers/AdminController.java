package course.polyclinic.controllers;

import course.polyclinic.DTO.DoctorDTO;
import course.polyclinic.components.Doctor;
import course.polyclinic.components.FreeMeet;
import course.polyclinic.components.FreeTime;
import course.polyclinic.components.User;
import course.polyclinic.services.DoctorService;
import course.polyclinic.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final DoctorService doctorService;
    @PostMapping("/regdoc")
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean registration(@RequestBody DoctorDTO doctorDTO) {
        try {
            userService.loadUserByUsername(doctorDTO.getLogin());
        }
        catch (UsernameNotFoundException e) {
            User user = new User().setLogin(doctorDTO.getLogin()).setPassword(doctorDTO.getPassword());
            LocalDate date = LocalDate.of(doctorDTO.getYear(), doctorDTO.getMonth(), doctorDTO.getDay());
            Doctor doctor = new Doctor().setGender(doctorDTO.getGender())
                    .setFirstName(doctorDTO.getFirstName()).setLastName(doctorDTO.getLastName())
                    .setNumber(doctorDTO.getNumber())
                    .setCabinet(doctorDTO.getCabinet())
                    .setBirthDate(date).setSpec(doctorDTO.decodeSpec()).setUser(user);
            userService.saveUser(user, "ROLE_DOCTOR");
            doctorService.add(doctor);
            List<FreeMeet> freeMeetList = new ArrayList<>();
            for (int i = 0; i <= 10; i++) {
                FreeMeet freeMeet = new FreeMeet().setDate(LocalDate.now().plusDays(i + 1));
                List<FreeTime> freeTimeList = new ArrayList<>();
                freeMeet.setDoctor(doctor);
                doctorService.addMeets(freeMeet);
                for (int j = 0; j <= 10; j++) {
                    FreeTime freeTime = new FreeTime();
                    freeTime.setFreeMeet(freeMeet);
                    freeTime.setTime(LocalTime.of(10 + j, 0));
                    freeTimeList.add(freeTime);
                    doctorService.addMeetsTime(freeTime);
                }
                freeMeet.setFreeTimes(freeTimeList);
                doctorService.addMeets(freeMeet);
            }
            return true;
        }
        return false;

    }
}
