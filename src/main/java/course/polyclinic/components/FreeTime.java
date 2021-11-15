package course.polyclinic.components;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Accessors(chain = true)
@Entity
@RequiredArgsConstructor
@Data
@Table(name="free_times")
public class FreeTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    Long id;
    LocalTime time;
    @ManyToOne
    @JoinColumn(name="freeMeet_id", nullable = false)
    private FreeMeet freeMeet;
}
