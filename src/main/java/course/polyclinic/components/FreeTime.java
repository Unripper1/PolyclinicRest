package course.polyclinic.components;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalTime;
@Transactional
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
    @JsonIgnore
    private FreeMeet freeMeet;
}
