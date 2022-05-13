package course.polyclinic.components;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import course.polyclinic.enums.Status;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
@Accessors(chain = true)
@Entity
@Data
@Table(name = "appointments")
@JsonIgnoreProperties
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
    @OneToOne(mappedBy = "appointment")
    private Result result;
}
