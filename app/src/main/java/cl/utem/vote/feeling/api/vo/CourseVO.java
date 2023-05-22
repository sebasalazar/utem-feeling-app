package cl.utem.vote.feeling.api.vo;

import cl.utem.vote.feeling.persistence.model.Section;
import cl.utem.vote.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Schema(name = "Course")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CourseVO extends Utem {

    private String token = null;
    private String code = null;
    private String name = null;
    private Integer semester = null;
    private Integer year = null;
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
