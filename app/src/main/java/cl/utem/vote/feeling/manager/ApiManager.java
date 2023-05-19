package cl.utem.vote.feeling.manager;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cl.utem.vote.feeling.api.vo.TokenVO;
import cl.utem.vote.feeling.exception.AuthException;
import cl.utem.vote.feeling.exception.UtemException;
import cl.utem.vote.feeling.persistence.enums.Role;
import cl.utem.vote.feeling.persistence.model.ApiAuth;
import cl.utem.vote.feeling.persistence.model.Credential;
import cl.utem.vote.feeling.persistence.model.User;
import cl.utem.vote.feeling.persistence.repository.ApiAuthRepository;
import cl.utem.vote.feeling.persistence.repository.CredentialRepository;
import cl.utem.vote.feeling.persistence.repository.UserRepository;
import cl.utem.vote.feeling.utils.JwtUtils;
import cl.utem.vote.feeling.utils.UrlUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ApiManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.url}")
    private String redirectGoogleUrl;

    @Autowired
    private transient ApiAuthRepository apiAuthRepository;

    @Autowired
    private transient CredentialRepository credentialRepository;

    @Autowired
    private transient UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiManager.class);

    private String getUrl(final Credential credential) {
        String url = StringUtils.EMPTY;
        try {
            if (credential != null) {
                final String googleRedirect = String.format("https://accounts.google.com/o/oauth2/v2/auth?scope=%s&access_type=offline&include_granted_scopes=true&response_type=code&state=%s&redirect_uri=%s&client_id=%s",
                        URLEncoder.encode("https://www.googleapis.com/auth/userinfo.email", StandardCharsets.UTF_8),
                        credential.getState(),
                        URLEncoder.encode(redirectGoogleUrl, StandardCharsets.UTF_8),
                        StringUtils.trimToEmpty(clientId));

                URL urlCu = new URL(googleRedirect);
                url = StringUtils.trimToEmpty(urlCu.toString());
                LOGGER.debug("[{}] Url de redirección: {}", credential.getState(), url);
            }
        } catch (Exception e) {
            url = StringUtils.EMPTY;
            LOGGER.error("Error al obtener url: {}", e.toString());
            LOGGER.debug("Error al obtener url: {}", e.toString(), e);
        }
        return url;
    }

    public ApiAuth getApiAuth(final String token, final String key) {
        ApiAuth credential = null;
        if (!StringUtils.isAnyBlank(token, key)) {
            credential = apiAuthRepository.findByTokenAndApiKey(token, key);
        }
        return credential;
    }

    @Transactional
    public Credential create(ApiAuth auth, String token, String successUrl, String failedUrl) {
        Credential credential = new Credential();
        credential.setAuth(auth);
        credential.setState(token);
        credential.setSuccessUrl(successUrl);
        credential.setFailedUrl(failedUrl);
        return credentialRepository.saveAndFlush(credential);
    }

    public Credential getCredential(final String token) {
        Credential credential = null;
        if (StringUtils.isNotBlank(token)) {
            credential = credentialRepository.findByState(token);
        }
        return credential;
    }

    @Transactional
    public Credential save(Credential credential) {
        Credential saved = null;
        if (credential != null) {
            saved = credentialRepository.saveAndFlush(credential);
        }
        return saved;
    }

    public TokenVO getTokenVO(final Credential credential) {
        TokenVO vo = null;
        if (credential != null) {
            String redirectUrl = getUrl(credential);
            if (UrlUtils.isValid(redirectUrl)) {
                vo = new TokenVO();
                vo.setCreated(credential.getCreated());
                vo.setRedirectUrl(redirectUrl);
                vo.setToken(credential.getState());
            }
        }
        return vo;
    }

    public String getTokenUrl(final String code) {
        String url = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(code)) {
                if (StringUtils.isNotBlank(code)) {
                    url = String.format("https://www.googleapis.com/oauth2/v4/token?code=%s&client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code",
                            StringUtils.trimToEmpty(code),
                            StringUtils.trimToEmpty(clientId),
                            StringUtils.trimToEmpty(clientSecret),
                            URLEncoder.encode(redirectGoogleUrl, StandardCharsets.UTF_8));
                }
            }
        } catch (Exception e) {
            url = StringUtils.EMPTY;
            LOGGER.error("Error al obtener url: {}", e.toString());
            LOGGER.debug("Error al obtener url: {}", e.toString(), e);
        }
        return url;
    }

    public User authUser(final String authorization) {
        User user = null;
        try {
            if (StringUtils.isNotBlank(authorization)) {
                final String jwt = StringUtils.trimToEmpty(StringUtils.removeStartIgnoreCase(authorization, "bearer"));
                GoogleIdToken google = JwtUtils.getGoogleIdToken(clientId, jwt);
                if (google == null) {
                    throw new UtemException("NO se pudo decodificar los datos de google");
                }

                if (!google.verifyExpirationTime(System.currentTimeMillis(), 5)) {
                    throw new UtemException("Token expirado.");
                }

                final boolean emailVerified = google.getPayload().getEmailVerified();
                if (!emailVerified) {
                    String name = (String) google.getPayload().get("name");
                    throw new UtemException(String.format("El correo electrónico de %s no está validado", name));
                }

                final String email = StringUtils.lowerCase(StringUtils.trimToEmpty(google.getPayload().getEmail()));
                if (!EmailValidator.getInstance().isValid(email)) {
                    throw new UtemException(String.format("El correo electrónico %s no es válido", email));
                }

                if (!StringUtils.containsIgnoreCase(email, "@utem.cl")) {
                    throw new UtemException(String.format("El correo electrónico %s no pertenece al dominio de la utem", email));
                }

                user = userRepository.findByEmailIgnoreCase(email);
                if (user == null) {
                    User utem = new User();
                    utem.setEmail(email);
                    utem.setRole(Role.STUDENT);
                    user = userRepository.saveAndFlush(utem);
                }
            }
        } catch (Exception e) {
            user = null;
            LOGGER.error("Error al validar jwt y obtener email: {}", e.toString());
            LOGGER.debug("Error al validar jwt y obtener email: {}", e.toString(), e);
            throw new AuthException(e.getMessage());
        }
        return user;
    }
}
