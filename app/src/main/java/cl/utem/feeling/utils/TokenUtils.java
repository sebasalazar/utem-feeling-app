package cl.utem.feeling.utils;

import java.io.Serializable;
import java.security.SecureRandom;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina.
 */
public class TokenUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer TOKEN_SIZE = 37;
    private static final String PRINTABLE_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenUtils.class);

    /**
     * Constructor no instanciable
     */
    private TokenUtils() {
        throw new IllegalStateException("No instanciable");
    }

    /**
     *
     * @param size Tamaño del texto
     * @return Un string aleatoreo
     */
    public static String randomAlphanumeric(final int size) {
        String random = StringUtils.EMPTY;
        try {
            if (size > 0) {
                random = SecureRandom.getInstanceStrong()
                        .ints(size, 0, StringUtils.length(PRINTABLE_CHARS))
                        .mapToObj(PRINTABLE_CHARS::charAt)
                        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                        .toString();
                LOGGER.trace("{}", random);
            }
        } catch (Exception e) {
            random = StringUtils.EMPTY;
            LOGGER.error("Error al obtener datos aleatoreos: {}", e.getLocalizedMessage());
            LOGGER.debug("Error al obtener datos aleatoreos: {}", e.getMessage(), e);
        }
        return random;
    }

    /**
     *
     * @return Un string de largo 37
     */
    public static String getToken() {
        return randomAlphanumeric(TOKEN_SIZE);
    }
}
