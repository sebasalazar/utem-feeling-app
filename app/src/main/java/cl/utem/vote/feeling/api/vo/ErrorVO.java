package cl.utem.vote.feeling.api.vo;

import cl.utem.vote.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorVO extends Utem {

    private static final long serialVersionUID = 1L;

    private boolean ok = false;
    private String message = null;
    private OffsetDateTime created = OffsetDateTime.now(ZoneOffset.UTC);

    public ErrorVO() {
        this.ok = false;
        this.message = "Error desconocido";
        this.created = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public ErrorVO(String message) {
        this.ok = false;
        this.message = message;
        this.created = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }
}
