package at.jkopec.dezsys09.db;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Created by jakubkopec on 06.04.16.
 */
@Entity
public class User {
    @Id
    @Size(max = 50)
    @NotEmpty
    private String email;

    @Size(max = 50)
    @NotEmpty
    private String name;

    @NotEmpty
    @Size(min = 5)
    private String password;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}