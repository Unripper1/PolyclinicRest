package course.polyclinic.DTO;

import course.polyclinic.enums.Gender;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class CustomerDTO {
    @NotEmpty
    private String password;
    @Email
    private String login;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private Gender gender;
    private Integer month;
    private Integer year;
    private Integer day;
    @NotEmpty
    private String insurance;
    @NotEmpty
    private String number;
}
