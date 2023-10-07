package cl.utem.feeling.api.vo;

import cl.utem.feeling.persistence.model.Section;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Schema(name = "Result")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResultVO extends CourseVO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Valoración promedio del curso", example = "3.7", requiredMode = Schema.RequiredMode.REQUIRED)
    private double avg = 0.0;

    @Schema(description = "Cantidad de votaciones", example = "17", requiredMode = Schema.RequiredMode.REQUIRED)
    private long total = 0l;

    public ResultVO() {
    }

    public ResultVO(Section section, double avg, long total) {
        super(section);
        this.avg = avg;
        this.total = total;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
