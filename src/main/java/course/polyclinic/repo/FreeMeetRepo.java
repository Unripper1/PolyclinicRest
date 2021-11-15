package course.polyclinic.repo;

import course.polyclinic.components.FreeMeet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FreeMeetRepo extends JpaRepository<FreeMeet,Long> {
    public FreeMeet findFreeMeetById(Long id);
    public List<FreeMeet> findAllByDateEquals(LocalDate date);
}
