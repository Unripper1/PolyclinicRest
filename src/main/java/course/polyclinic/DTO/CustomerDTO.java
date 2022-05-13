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
    @Min(value = 1)
    @Max(value = 12)
    private Integer month;
    @Min(value = 1900)
    @Max(value = 2010)
    private Integer year;
    @Min(value = 1)
    @Max(value = 31)
    private Integer day;
    private String spec;
    @NotEmpty
    private String number;
}
