package cl.utem.vote.feeling.api.vo;

import cl.utem.vote.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountVO extends Utem {

    private double avg = 0.0;
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
