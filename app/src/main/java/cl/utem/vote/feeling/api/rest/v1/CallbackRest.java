package cl.utem.vote.feeling.api.rest.v1;

import cl.utem.vote.feeling.api.vo.GoogleResponseVO;
import cl.utem.vote.feeling.exception.UtemException;
import cl.utem.vote.feeling.manager.ApiManager;
import cl.utem.vote.feeling.manager.RestManager;
import cl.utem.vote.feeling.persistence.model.Credential;
import cl.utem.vote.feeling.utils.JwtUtils;
import cl.utem.vote.feeling.utils.UrlUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.io.Serializable;
import java.net.URI;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/v1/callback"},
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class CallbackRest implements Serializable {

    private static final long serialVersionUID = 2L;

    @Value("${google.client.id}")
    private String clientId;

    @Autowired
    private transient ApiManager apiManager;

    @Autowired
    private transient RestManager restManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(CallbackRest.class);

    @GetMapping(value = "/verify", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity verifyToken(@RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "state", required = false) String state,
            @RequestParam(name = "error", required = false) String error) {
        // Varialbe de redirección.
        URI uri = null;

        // Credenciales usadas
        Credential credential = apiManager.getCredential(state);
        if (credential == null) {
            LOGGER.error("[{}] Error consumiendo Token", state);
            throw new UtemException(404, String.format("Token %s no encontrado", state));
        }

        // Urls a redirigir
        final String successUrl = StringUtils.trimToEmpty(credential.getSuccessUrl());
        final String failedUrl = StringUtils.trimToEmpty(credential.getFailedUrl());

        // Validaciones
        try {
            if (StringUtils.isNotBlank(error)) {
                throw new UtemException(StringUtils.trimToEmpty(error));
            }

            if (StringUtils.isBlank(code) || StringUtils.isBlank(state)) {
                LOGGER.error("[{}] Valores incorrectos", code);
                throw new UtemException(String.format("Valores incorrectos para code: '%s' o state: '%s'", code, state));
            }

            /**
             * Código usado para iniciar la negociación
             */
            final String tokenUrl = apiManager.getTokenUrl(code);
            final String jsonResponse = restManager.post(code, tokenUrl, StringUtils.EMPTY);
            final GoogleResponseVO googleResponse = JwtUtils.getGoogleResponse(jsonResponse);
            if (googleResponse == null) {
                LOGGER.error("[{}] NO fue posible obtener los datos desde google", code);
                throw new UtemException("NO fue posible obtener los datos desde google");
            }

            // Procesamos datos
            final Long expiresIn = googleResponse.getExpiresIn();
            final String jwt = googleResponse.getIdToken();
            LOGGER.info("TOKEN DE GOOGLE: '{}'", jwt);

            final GoogleIdToken google = JwtUtils.getGoogleIdToken(clientId, jwt);
            if (google == null) {
                LOGGER.error("[{}] NO se pudo decodificar los datos de google", code);
                throw new UtemException("NO se pudo decodificar los datos de google");
            }

            final boolean emailVerified = google.getPayload().getEmailVerified();
            if (!emailVerified) {
                String name = (String) google.getPayload().get("name");
                LOGGER.error("[{}] El correo electrónico de '{}' no está validado", code, name);
                throw new UtemException(String.format("El correo electrónico de %s no está validado", name));
            }

            final String email = StringUtils.lowerCase(StringUtils.trimToEmpty(google.getPayload().getEmail()));
            if (!EmailValidator.getInstance().isValid(email)) {
                LOGGER.error("[{}] El correo electrónico '{}' no es válido", code, email);
                throw new UtemException(String.format("El correo electrónico %s no es válido", email));
            }

            // Consultar el correo electrónico.
            credential.setJwt(jwt);
            credential.setCode(code);
            credential.setUpdated(credential.getCreated().plusSeconds(expiresIn));
            Credential savedCredential = apiManager.save(credential);
            if (savedCredential == null) {
                LOGGER.error("[{}] No fue posible actualizar el token", state);
                throw new UtemException(404, String.format("No fue posible actualizar el token %s", state));
            }

            uri = UrlUtils.appendUri(successUrl, String.format("jwt=%s", jwt, savedCredential.getState()));
            if (uri == null) {
                LOGGER.error("[{}] Error al redirigir a {}", state, successUrl);
                uri = UrlUtils.getUri(failedUrl);
            }
        } catch (Exception e) {
            uri = UrlUtils.getUri(failedUrl);
            LOGGER.error("Error al validar credenciales de google: {}", e.getMessage());
            LOGGER.debug("Error al validar credenciales de google: {}", e.getMessage(), e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
