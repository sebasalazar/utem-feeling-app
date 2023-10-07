package cl.utem.feeling.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.io.Serializable;
import java.util.Collections;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final ObjectMapper MAPPER;

    /**
     * Datos para transformar datos json
     */
    static {
        MAPPER = new ObjectMapper();
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    public static GoogleIdToken getGoogleIdToken(final String clientId, final String idTokenJwt) {
        GoogleIdToken decoded = null;
        try {
            if (StringUtils.isNotBlank(idTokenJwt)) {
                GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
                        new GsonFactory()).setAudience(Collections.singletonList(clientId))
                        .build();

                decoded = verifier.verify(idTokenJwt);
            }
        } catch (Exception e) {
            decoded = null;
            LOGGER.error("Error al decodificar jwt: {}", e.getMessage());
            LOGGER.trace("Error al decodificar jwt: {}", e.getMessage(), e);
        }
        return decoded;
    }
}
