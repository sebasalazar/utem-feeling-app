package cl.utem.vote.feeling.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "votes")
public class Vote extends PkEntityBase {

    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "section_fk", referencedColumnName = "pk", nullable = false)
    @ManyToOne
    private Section section = null;

    @Column(name = "attendance", nullable = false)
    private LocalDate attendance = LocalDate.now();

    @Column(name = "choice", nullable = false)
    private Integer choice = null;

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

    public Integer getChoice() {
        return choice;
    }

    public void setChoice(Integer choice) {
        this.choice = choice;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.section);
        hash = 59 * hash + Objects.hashCode(this.attendance);
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
        final Vote other = (Vote) obj;
        if (!Objects.equals(this.section, other.section)) {
            return false;
        }
        return Objects.equals(this.attendance, other.attendance) && super.equals(obj);
    }
}
