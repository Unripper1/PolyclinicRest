package course.polyclinic.repo;

import course.polyclinic.components.FreeMeet;
import course.polyclinic.components.FreeTime;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface TimeRepo extends JpaRepository<FreeTime,Long> {
    public FreeTime findFreeTimeById(Long id);
    public void deleteById(Long id);
}
