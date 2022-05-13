package course.polyclinic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class RunAfterStart {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @EventListener(ApplicationReadyEvent.class)
    private void createAdmin() throws SQLException {
        String query = "SELECT count(*) FROM users";
        PreparedStatement statement = getNewConnection().prepareStatement(query);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        System.out.println(resultSet.getInt(1));
        if(resultSet.getInt(1)==0) {
            String roles =
                    "INSERT INTO roles values (1, 'ROLE_USER'), (2, 'ROLE_DOCTOR'), (3, 'ROLE_ADMIN');";
            String admin = "INSERT INTO users values (0, 'admin@admin.com', '" + bCryptPasswordEncoder.encode("0000") + "');";
            String role_user = "INSERT INTO users_roles values (0, 3);";
            executeUpdate(roles);
            executeUpdate(admin);
            executeUpdate(role_user);
        }
    }
    private Connection getNewConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/polyclinicDB_test";
        String user = "postgres";
        String passwd = "12345";
        return DriverManager.getConnection(url, user, passwd);
    }
    private int executeUpdate(String query) throws SQLException {
        Statement statement = getNewConnection().createStatement();
        int result = statement.executeUpdate(query);
        return result;
    }

}
