package cl.utem.vote.feeling.api.rest.v1;

import cl.utem.vote.feeling.api.vo.JwtVO;
import cl.utem.vote.feeling.api.vo.RequestCodeVO;
import cl.utem.vote.feeling.api.vo.ResponseVO;
import cl.utem.vote.feeling.api.vo.TokenVO;
import cl.utem.vote.feeling.exception.AuthException;
import cl.utem.vote.feeling.exception.BadVoteException;
import cl.utem.vote.feeling.exception.UtemException;
import cl.utem.vote.feeling.manager.ApiManager;
import cl.utem.vote.feeling.persistence.model.ApiAuth;
import cl.utem.vote.feeling.persistence.model.Credential;
import cl.utem.vote.feeling.utils.TokenUtils;
import cl.utem.vote.feeling.utils.UrlUtils;
import java.io.Serializable;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/v1/auth"}, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class AuthRest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient ApiManager apiManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRest.class);

    @GetMapping(value = "/login", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TokenVO> requestToken(@RequestHeader("X-API-TOKEN") String apiToken, @RequestHeader("X-API-KEY") String apiKey) {

        /**
         * Intentamos crear un token
         */
        final String token = TokenUtils.getToken();
        if (StringUtils.isBlank(token)) {
            LOGGER.error("No fue posible crear el token");
            throw new BadVoteException();
        }

        LOGGER.debug("[{}] Petición de token", token);

        final ApiAuth auth = apiManager.getApiAuth(apiToken, apiKey);
        if (auth == null || !auth.isActive()) {
            LOGGER.error("[{}] Credenciales inválidas", token);
            throw new AuthException();
        }

        final String successUrl = String.format("https://api.sebastian.cl/feeling/v1/auth/%s/result", token);
        final String failedUrl = String.format("https://api.sebastian.cl/feeling/v1/auth/%s/result", token);

        final Credential created = apiManager.create(auth, token, successUrl, failedUrl);
        if (created == null) {
            LOGGER.error("[{}] No fue posible crear la operación", token);
            throw new UtemException("No fue posible crear la operación");
        }

        final TokenVO tokenVO = apiManager.getTokenVO(created);
        if (tokenVO == null) {
            LOGGER.error("[{}] No fue posible crear la operación, error al generar url de redirección", token);
            throw new UtemException("No fue posible crear la operación");
        }

        return ResponseEntity.ok(tokenVO);
    }

    @PostMapping(value = "/request", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TokenVO> requestToken(@RequestHeader("X-API-TOKEN") String apiToken, @RequestHeader("X-API-KEY") String apiKey,
            @RequestBody RequestCodeVO requestCode) {

        /**
         * Intentamos crear un token
         */
        final String token = TokenUtils.getToken();
        if (StringUtils.isBlank(token)) {
            LOGGER.error("No fue posible crear el token");
            throw new BadVoteException();
        }

        LOGGER.debug("[{}] Petición de token", token);

        final ApiAuth auth = apiManager.getApiAuth(apiToken, apiKey);
        if (auth == null || !auth.isActive()) {
            LOGGER.error("[{}] Credenciales inválidas", token);
            throw new AuthException();
        }

        if (requestCode == null) {
            LOGGER.error("[{}] El cuerpo de la petición es incorrecto", token);
            throw new UtemException(400, "Datos inválidos");
        }

        final String successUrl = StringUtils.trimToEmpty(requestCode.getSuccessUrl());
        if (!UrlUtils.isValid(successUrl)) {
            LOGGER.error("[{}] La url de éxito tiene un formato incorrecto", token);
            throw new UtemException(String.format("La url de éxito (%s) es inválida", successUrl));
        }

        final String failedUrl = StringUtils.trimToEmpty(requestCode.getFailedUrl());
        if (!UrlUtils.isValid(failedUrl)) {
            LOGGER.error("[{}] La url de error tiene un formato incorrecto", token);
            throw new UtemException(String.format("La url de error (%s) es inválida", failedUrl));
        }

        final Credential created = apiManager.create(auth, token, successUrl, failedUrl);
        if (created == null) {
            LOGGER.error("[{}] No fue posible crear la operación", token);
            throw new UtemException("No fue posible crear la operación");
        }

        final TokenVO tokenVO = apiManager.getTokenVO(created);
        if (tokenVO == null) {
            LOGGER.error("[{}] No fue posible crear la operación, error al generar url de redirección", token);
            throw new UtemException("No fue posible crear la operación");
        }

        return ResponseEntity.ok(tokenVO);
    }

    @GetMapping(value = "/{token}/jwt", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<JwtVO> getJwt(@RequestHeader("X-API-TOKEN") String apiToken, @RequestHeader("X-API-KEY") String apiKey,
            @PathVariable("token") String token) {
        if (StringUtils.isBlank(token)) {
            LOGGER.error("No fue posible crear el token");
            throw new BadVoteException();
        }

        final ApiAuth auth = apiManager.getApiAuth(apiToken, apiKey);
        if (auth == null || !auth.isActive()) {
            LOGGER.error("[{}] Credenciales inválidas", token);
            throw new AuthException();
        }

        final Credential credential = apiManager.getCredential(token);
        if (credential == null) {
            LOGGER.error("[{}] Token no encontrado", token);
            throw new UtemException(404, String.format("Token %s no encontrado", token));
        }

        if (credential.isExpired()) {
            LOGGER.error("[{}] Expirado", token);
            throw new UtemException(410, String.format("Token %s expirado %s", token, credential.getUpdated()));
        }

        if (!Objects.equals(auth, credential.getAuth())) {
            LOGGER.error("[{}] Token no pertenece a quien consulta", token);
            throw new UtemException(403, String.format("No tiene permiso para consultar por este token", token));
        }

        final String jwt = credential.getJwt();
        if (StringUtils.isBlank(jwt)) {
            LOGGER.error("[{}] No fue posible crear JWT", token);
            throw new UtemException(String.format("No fue posible crear el jwt en base al token '%s'", token));
        }

        return ResponseEntity.ok(new JwtVO(jwt));
    }

    @GetMapping(value = "/{token}/result", consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseVO> getResult(@PathVariable("token") String token) {
        if (StringUtils.isBlank(token)) {
            LOGGER.error("No fue posible crear el token");
            throw new BadVoteException();
        }

        final Credential credential = apiManager.getCredential(token);
        if (credential == null) {
            LOGGER.error("[{}] Token no encontrado", token);
            throw new UtemException(404, String.format("Token %s no encontrado", token));
        }

        if (credential.isExpired()) {
            LOGGER.error("[{}] Expirado", token);
            throw new UtemException(410, String.format("Token %s expirado %s", token, credential.getUpdated()));
        }

        return ResponseEntity.ok(new ResponseVO(String.format("Proceso para el token %s con resultado exitoso", token)));
    }
}
