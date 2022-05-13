package course.polyclinic.DTO;

import course.polyclinic.enums.Gender;
import course.polyclinic.enums.Specialization;
import lombok.Data;

@Data
public class DoctorDTO {
    private String password;
    private String login;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Integer month;
    private Integer year;
    private Integer day;
    private String number;
    private String spec;
    private Integer cabinet;
    public Specialization decodeSpec(){
        switch (spec){
            case "SURGEON":return Specialization.SURGEON;
            case "NEUROLOGIST":return Specialization.NEUROLOGIST;
            case "UROLOGIST":return Specialization.UROLOGIST;
            case "OPHTHALMOLOGIST": return Specialization.OPHTHALMOLOGIST;
            case "OTOLARYNGOLOGIST": return Specialization.OTOLARYNGOLOGIST;
        }
        return null;
    }
}