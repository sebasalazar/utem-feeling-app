package cl.utem.feeling.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "sections")
public class Section extends PkEntityBase {

    private static final long serialVersionUID = 1L;

    @Column(name = "token", nullable = false)
    private String token = null;

    @JoinColumn(name = "subject_fk", referencedColumnName = "pk", nullable = false)
    @ManyToOne
    private Subject subject = null;

    @Column(name = "semester", nullable = false)
    private Integer semester = null;

    @Column(name = "year", nullable = false)
    private Integer year = null;

    @Column(name = "active", nullable = false)
    private boolean active = false;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.token);
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
        final Section other = (Section) obj;
        return Objects.equals(this.token, other.token) && super.equals(obj);
    }
}
