package course.polyclinic.components;

import lombok.Data;
import lombok.experimental.Accessors;


import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "results")
@Accessors(chain = true)
public class Result {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="diagnosis")
    private String diagnosis;
    @Column(name="therapy")
    private String therapy;
    @Column(name="description")
    private String description;
    @OneToOne(mappedBy = "result")
    private Appointment appointment;
    @OneToMany(mappedBy = "result")
    private List<Direction> directions;
}
