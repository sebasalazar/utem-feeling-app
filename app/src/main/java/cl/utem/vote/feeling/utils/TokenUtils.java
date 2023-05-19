package cl.utem.vote.feeling.utils;

import java.io.Serializable;
import java.security.SecureRandom;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author https://experti.cl
 */
public class TokenUtils implements Serializable {

    private static final long serialVersionUID = 6735697959381170176L;

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
    public static String randomAlphanumeric(final Integer size) {
        String random = StringUtils.EMPTY;
        try {
            if (size != null && size > 0) {
                random = SecureRandom.getInstanceStrong()
                        .ints(size, 0, PRINTABLE_CHARS.length())
                        .mapToObj(i -> PRINTABLE_CHARS.charAt(i))
                        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                        .toString();
            }
        } catch (Exception e) {
            if (size != null && size > 0) {
                random = RandomStringUtils.randomAlphanumeric(size);
            }
            LOGGER.error("Error al obtener alphanumerico aleatoreo: {}", e.getMessage());
            LOGGER.trace("Error al obtener alphanumerico aleatoreo: {}", e.getMessage(), e);
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
