package cl.utem.vote.feeling.api.vo;

import cl.utem.vote.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VoteVO extends Utem {

    private String sectionToken = null;
    private LocalDate attendance = null;
    private Integer choice = null;

    public String getSectionToken() {
        return sectionToken;
    }

    public void setSectionToken(String sectionToken) {
        this.sectionToken = sectionToken;
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
}
