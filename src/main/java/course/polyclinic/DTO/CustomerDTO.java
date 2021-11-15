package course.polyclinic.DTO;

import course.polyclinic.enums.Gender;
import lombok.Data;

@Data
public class CustomerDTO {
    private String password;
    private String login;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Integer month;
    private Integer year;
    private Integer day;
    private String insurance;
    private String number;
}
