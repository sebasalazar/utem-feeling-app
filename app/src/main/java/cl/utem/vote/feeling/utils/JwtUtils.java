package cl.utem.vote.feeling.utils;

import cl.utem.vote.feeling.api.vo.GoogleResponseVO;
import cl.utem.vote.feeling.persistence.model.Credential;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.util.Collections;

public class JwtUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    /// Datos para realizar firmas
    private static final String AUD = "UTEM";

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

    public static GoogleResponseVO getGoogleResponse(final String json) {
        GoogleResponseVO vo = null;
        try {
            if (StringUtils.isNotBlank(json)) {
                vo = JwtUtils.MAPPER.readValue(json, GoogleResponseVO.class);
            }
        } catch (Exception e) {
            vo = null;
            LOGGER.error("Error al procesar respuesta: {}", e.getMessage());
            LOGGER.trace("Error al procesar respuesta: {}", e.getMessage(), e);
        }
        return vo;
    }

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