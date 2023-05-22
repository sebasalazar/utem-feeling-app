package cl.utem.vote.feeling.manager;

import cl.utem.vote.feeling.exception.UtemException;
import cl.utem.vote.feeling.persistence.enums.Role;
import cl.utem.vote.feeling.persistence.model.User;
import cl.utem.vote.feeling.persistence.repository.UserRepository;
import cl.utem.vote.feeling.utils.JwtUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${google.client.id}")
    private String clientId;

    @Autowired
    private transient UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiManager.class);

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
            LOGGER.error("Error al validar jwt y obtener email: {}", e.getLocalizedMessage());
            LOGGER.debug("Error al validar jwt y obtener email: {}", e.getMessage(), e);
        }
        return user;
    }
}
