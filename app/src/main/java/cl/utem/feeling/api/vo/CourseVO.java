package cl.utem.feeling.api.vo;

import cl.utem.feeling.persistence.model.Section;
import cl.utem.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Schema(name = "Course")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CourseVO extends Utem {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Token identificador de la sección", example = "2a94bea0-2b58-49f0-a4cd-5f41d454406d", requiredMode = Schema.RequiredMode.REQUIRED)
    private String token = null;

    @Schema(description = "Código del curso", example = "INFB8090", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code = null;

    @Schema(description = "Nombre del curso", example = "COMPUTACIÓN PARALELA Y DISTRIBUIDA", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name = null;

    @Schema(description = "Semestre en que se impartió el curso", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer semester = null;

    @Schema(description = "Año en que se impartió el curso", example = "2023", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer year = null;

    @Schema(description = "Flag que indica si el curso está activo en este momento", example = "true", allowableValues = {"true", "false"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean active = false;

    public CourseVO() {
    }

    public CourseVO(Section section) {
        this.token = section.getToken();
        this.code = section.getSubject().getCode();
        this.name = section.getSubject().getName();
        this.semester = section.getSemester();
        this.year = section.getYear();
        this.active = section.isActive();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
