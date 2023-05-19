package cl.utem.vote.feeling.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "credentials")
public class Credential extends PkEntityBase {

    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "auth_fk", referencedColumnName = "pk", nullable = false)
    @ManyToOne
    private ApiAuth auth = null;

    @Column(name = "state", nullable = false, unique = true)
    private String state = null;

    @Column(name = "code")
    private String code = null;

    @Column(name = "jwt")
    private String jwt = null;

    @Column(name = "success_url", nullable = false)
    private String successUrl = null;

    @Column(name = "failed_url", nullable = false)
    private String failedUrl = null;

    public boolean isExpired() {
        return OffsetDateTime.now().isAfter(updated);
    }

    public ApiAuth getAuth() {
        return auth;
    }

    public void setAuth(ApiAuth auth) {
        this.auth = auth;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.state);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Credential other = (Credential) obj;
        return Objects.equals(this.state, other.state) && super.equals(obj);
    }
}
