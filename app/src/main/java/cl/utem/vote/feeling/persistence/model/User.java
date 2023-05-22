package cl.utem.vote.feeling.persistence.model;

import cl.utem.vote.feeling.persistence.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends PkEntityBase {

    private static final long serialVersionUID = 1L;

    @Column(name = "email", nullable = false, unique = true)
    private String email = null;

    @Column(name = "role", nullable = false)
    private Role role = Role.STUDENT;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.email, other.email) && super.equals(obj);
    }
}
