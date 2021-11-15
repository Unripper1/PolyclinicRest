package course.polyclinic.components;

import course.polyclinic.enums.DirectionName;
import course.polyclinic.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name="directions")
@Data
public class Direction {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="direction_name")
    @Enumerated(EnumType.STRING)
    private DirectionName name;
    @Column(name="start_date")
    private LocalDate startDate;
    @Column(name="final_date")
    private LocalDate finalDate;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name="result_id", nullable = false)
    private Result result;
    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;

}
