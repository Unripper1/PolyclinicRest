package course.polyclinic.repo;

import course.polyclinic.components.FreeMeet;
import course.polyclinic.components.FreeTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepo extends JpaRepository<FreeTime,Long> {
    public FreeTime findFreeTimeById(Long id);
}
