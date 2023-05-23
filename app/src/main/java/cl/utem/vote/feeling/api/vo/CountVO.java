package cl.utem.vote.feeling.api.vo;

import cl.utem.vote.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Schema(name = "Count")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountVO extends Utem {

    @Schema(description = "Valoración promedio del curso", example = "3.7", requiredMode = Schema.RequiredMode.REQUIRED)
    private double avg = 0.0;

    @Schema(description = "Cantidad de votaciones", example = "17", requiredMode = Schema.RequiredMode.REQUIRED)
    private long total = 0l;

    public CountVO() {
        this.avg = 0.0;
        this.total = 0;
    }

    public CountVO(double avg, long total) {
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
