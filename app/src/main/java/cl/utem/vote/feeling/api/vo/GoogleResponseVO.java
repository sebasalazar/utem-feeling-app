package cl.utem.vote.feeling.api.vo;

import cl.utem.vote.feeling.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author ExperTI (contacto@experti.cl) https://experti.cl
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoogleResponseVO extends Utem {

    @JsonProperty("access_token")
    private String accessToken = null;

    @JsonProperty("expires_in")
    private Long expiresIn = null;

    @JsonProperty("scope")
    private String scope = null;

    @JsonProperty("token_type")
    private String tokenType = null;

    @JsonProperty("id_token")
    private String idToken = null;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
