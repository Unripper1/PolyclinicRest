package course.polyclinic.components;

import course.polyclinic.enums.Gender;
import course.polyclinic.enums.Specialization;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Accessors(chain = true)
@Entity
@RequiredArgsConstructor
@Data
@Table(name="doctors")
public class Doctor implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="cabinet")
    private Integer cabinet;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name="birthDate")
    private LocalDate birthDate;
    @Column(name="phone_namber")
    private String number;
    @Column(name="specialization")
    @Enumerated(EnumType.STRING)
    private Specialization spec;
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
    @OneToOne(mappedBy = "doctor")
    private User user;
    @OneToMany(mappedBy = "doctor")
    private List<FreeMeet> freeMeets ;
}
