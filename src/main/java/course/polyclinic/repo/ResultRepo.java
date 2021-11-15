package course.polyclinic.repo;

import course.polyclinic.components.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepo extends JpaRepository<Result,Long> {
}
