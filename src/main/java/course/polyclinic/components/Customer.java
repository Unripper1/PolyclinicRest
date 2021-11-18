package course.polyclinic.components;

import course.polyclinic.enums.Gender;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="customers")
@Accessors(chain = true)
public class Customer {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name="birthDate")
    private LocalDate birthDate;
    @Column(name="insurance")
    private String insurance="null";
    @Column(name="phone_number")
    private String number;
    @OneToMany(mappedBy = "customer")
    private List<Appointment> appointments;
    @OneToOne(mappedBy = "customer")
    private User user;

}
