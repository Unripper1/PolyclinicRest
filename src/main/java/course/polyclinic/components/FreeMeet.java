package course.polyclinic.components;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
@Transactional
@Accessors(chain = true)
@Entity
@RequiredArgsConstructor
@Data
@Table(name="free_meets")
public class FreeMeet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Long id;
    @OneToMany(mappedBy = "freeMeet", fetch=FetchType.EAGER)
    private List<FreeTime> freeTimes;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable = false)
    @JsonIgnore
    private Doctor doctor;
}
