package cl.utem.vote.feeling.api.vo;

import cl.utem.vote.feeling.persistence.model.Subject;
import cl.utem.vote.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 *
 * @author Sebastián Salazar Molina.
 */
@Schema(name = "Result")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResultVO extends Utem {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Nombre de la asignatura", requiredMode = Schema.RequiredMode.REQUIRED, example = "COMPUTACIÓN PARALELA Y DISTRIBUIDA")
    private String name = null;

    @ArraySchema(schema = @Schema(description = "Resultados", implementation = CountVO.class))
    private List<CountVO> results = null;

    public ResultVO() {
    }

    public ResultVO(Subject subject, List<CountVO> results) {
        this.name = subject.getName();
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
