package cl.utem.vote.feeling.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * Objeto BaseBean para las entidades del sistema
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Utem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @return Una versión que genera una string en formato json.
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
