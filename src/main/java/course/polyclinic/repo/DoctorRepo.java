package course.polyclinic.repo;

import course.polyclinic.components.Doctor;
import course.polyclinic.components.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepo extends JpaRepository<Doctor,Long> {
//    public List<Doctor> findAllBySpec();
    public Doctor findDoctorById(Long id);
}
