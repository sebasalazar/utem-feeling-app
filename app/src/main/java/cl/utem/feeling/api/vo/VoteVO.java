package cl.utem.feeling.api.vo;

import cl.utem.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Schema(name = "Result")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VoteVO extends Utem {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Token único de la sección / asignatura", requiredMode = Schema.RequiredMode.REQUIRED, example = "2a94bea0-2b58-49f0-a4cd-5f41d454406d")
    private String sectionToken = null;

    @Schema(description = "Fecha de la operación", requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-03-13")
    private LocalDate attendance = null;

    @Schema(description = "Votación", allowableValues = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
            example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
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
