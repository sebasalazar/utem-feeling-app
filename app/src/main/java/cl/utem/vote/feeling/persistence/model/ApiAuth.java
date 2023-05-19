package cl.utem.vote.feeling.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "api_auths")
public class ApiAuth extends PkEntityBase {

    private static final long serialVersionUID = 1L;

    @Column(name = "name", nullable = false)
    private String name = null;

    @Column(name = "token", nullable = false, unique = true)
    private String token = null;

    @Column(name = "api_key", nullable = false)
    private String apiKey = null;

    @Column(name = "active", nullable = false)
    private boolean active = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.token);
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
        final ApiAuth other = (ApiAuth) obj;
        return Objects.equals(this.token, other.token) && super.equals(obj);
    }
}
