package cl.utem.vote.feeling.api.vo;

import cl.utem.vote.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JwtVO extends Utem {

    @Schema(description = "JWT con la información del usuario autenticado", requiredMode = Schema.RequiredMode.REQUIRED)
    private String jwt = null;

    @Schema(description = "Fecha de creación de la respuesta", requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime created = OffsetDateTime.now(ZoneOffset.UTC);

    public JwtVO() {
        this.jwt = null;
        this.created = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public JwtVO(String jwt) {
        this.jwt = jwt;
        this.created = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }
}
