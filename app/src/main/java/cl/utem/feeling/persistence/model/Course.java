package cl.utem.feeling.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Entity
@Table(name = "courses")
public class Course extends PkEntityBase {

    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "section_fk", referencedColumnName = "pk", nullable = false)
    @ManyToOne
    private Section section = null;

    @JoinColumn(name = "user_fk", referencedColumnName = "pk", nullable = false)
    @ManyToOne
    private User user = null;

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.section);
        hash = 89 * hash + Objects.hashCode(this.user);
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
        final Course other = (Course) obj;
        if (!Objects.equals(this.section, other.section)) {
            return false;
        }
        return Objects.equals(this.user, other.user) && super.equals(obj);
    }
}
