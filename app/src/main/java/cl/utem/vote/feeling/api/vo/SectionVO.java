package cl.utem.vote.feeling.api.vo;

import cl.utem.vote.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Section")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SectionVO extends Utem {

    private int selection = 0;
    private String choice = null;

    public SectionVO() {
    }

    public SectionVO(int selection, String choice) {
        this.selection = selection;
        this.choice = choice;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
