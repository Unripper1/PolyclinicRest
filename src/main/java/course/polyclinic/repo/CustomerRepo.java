package course.polyclinic.repo;

import course.polyclinic.components.Customer;
import course.polyclinic.components.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
}
