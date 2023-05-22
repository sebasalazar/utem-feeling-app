package cl.utem.vote.feeling.api.vo;

import cl.utem.vote.feeling.persistence.model.Subject;
import cl.utem.vote.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResultVO extends Utem {

    private String name = null;
    private List<CountVO> results = null;

    public ResultVO() {
    }

    public ResultVO(Subject poll, List<CountVO> results) {
        this.name = poll.getName();
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CountVO> getResults() {
        return results;
    }

    public void setResults(List<CountVO> results) {
        this.results = results;
    }
}
