package cl.utem.feeling.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "voters")
public class Voter extends PkEntityBase {

    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "user_fk", referencedColumnName = "pk", nullable = false)
    @ManyToOne
    private User user = null;

    @JoinColumn(name = "section_fk", referencedColumnName = "pk", nullable = false)
    @ManyToOne
    private Section section = null;

    @Column(name = "attendance", nullable = false)
    private LocalDate attendance = LocalDate.now();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public LocalDate getAttendance() {
        return attendance;
    }

    public void setAttendance(LocalDate attendance) {
        this.attendance = attendance;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.user);
        hash = 73 * hash + Objects.hashCode(this.section);
        hash = 73 * hash + Objects.hashCode(this.attendance);
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
        final Voter other = (Voter) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.section, other.section)) {
            return false;
        }
        return Objects.equals(this.attendance, other.attendance) && super.equals(obj);
    }
}
