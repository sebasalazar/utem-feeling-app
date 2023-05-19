package cl.utem.vote.feeling.api.vo;

import cl.utem.vote.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RequestCode")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RequestCodeVO extends Utem {

    private static final long serialVersionUID = 1L;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Url de redirección en caso de una autenticación exitosa a esta url se le agregará un parámetro jwt con la información de la autenticación")
    private String successUrl = null;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Url de redirección en caso de una autenticación fallida")
    private String failedUrl = null;

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailedUrl() {
        return failedUrl;
    }

    public void setFailedUrl(String failedUrl) {
        this.failedUrl = failedUrl;
    }
}
