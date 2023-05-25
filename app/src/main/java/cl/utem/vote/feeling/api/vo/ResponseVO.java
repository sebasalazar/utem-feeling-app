package cl.utem.vote.feeling.api.vo;

import cl.utem.vote.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.apache.commons.lang3.StringUtils;

@Schema(name = "Response")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseVO extends Utem {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Flag que indica el estado de éxito", example = "true",
            allowableValues = {"true", "false"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean ok = false;

    @Schema(description = "Mensaje de respuesta", requiredMode = Schema.RequiredMode.REQUIRED, example = "Procesado Exitosamente")
    private String message = null;

    @Schema(description = "Fecha de creación de la respuesta", requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime created = OffsetDateTime.now(ZoneOffset.UTC);

    public ResponseVO() {
        this.ok = true;
        this.message = "Proceso Exitoso";
        this.created = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public ResponseVO(String message) {
        this.ok = true;
        this.message = StringUtils.trimToEmpty(message);
        this.created = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public ResponseVO(boolean ok, String message) {
        this.ok = ok;
        this.message = StringUtils.trimToEmpty(message);
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
