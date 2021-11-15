package course.polyclinic.components;

import course.polyclinic.enums.Status;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Accessors(chain = true)
@Entity
@Data
@Table(name = "appointments")
public class Appointment {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="date")
    private LocalDate date;
    @Column(name="time")
    private LocalTime time;
    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;
    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable = false)
    private Doctor doctor;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="result_id",referencedColumnName = "id")
    private Result result;
}
