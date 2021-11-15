package course.polyclinic.repo;

import course.polyclinic.components.FreeMeet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeMeetRepo extends JpaRepository<FreeMeet,Long> {
    public FreeMeet findFreeMeetById(Long id);
}
