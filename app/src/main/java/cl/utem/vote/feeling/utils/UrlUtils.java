package cl.utem.vote.feeling.utils;

import java.io.Serializable;
import java.net.URI;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase permite obtener información en función de las URL.
 *
 * @author Sebastián Salazar Molina.
 */
public class UrlUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final UrlValidator URL_VALIDATOR = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS + UrlValidator.ALLOW_ALL_SCHEMES + UrlValidator.ALLOW_2_SLASHES);
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

    private UrlUtils() {
        throw new IllegalStateException("Clase no instanciable");
    }

    public static boolean isValid(final String url) {
        boolean ok = false;
        if (StringUtils.isNotBlank(url)) {
            ok = URL_VALIDATOR.isValid(url);
            if (!ok) {
                LOGGER.error("Url '{}' inválida", url);
            }
        }
        return ok;
    }

    public static URI getUri(String url) {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (Exception e) {
            uri = null;
            LOGGER.error("Error al construir URI: {}", e.getMessage());
            LOGGER.debug("Error al construir URI: {}", e.getMessage(), e);
        }
        return uri;
    }

    public static URI appendUri(String baseUrl, String appendQuery) {
        URI uri = null;
        try {
            URI oldUri = new URI(baseUrl);
            String newQuery = oldUri.getQuery();
            if (StringUtils.isBlank(newQuery)) {
                newQuery = appendQuery;
            } else {
                newQuery += "&" + appendQuery;
            }
            uri = new URI(oldUri.getScheme(), oldUri.getAuthority(),
                    oldUri.getPath(), newQuery, oldUri.getFragment());
        } catch (Exception e) {
            uri = null;
            LOGGER.error("Error al construir URI: {}", e.getMessage());
            LOGGER.debug("Error al construir URI: {}", e.getMessage(), e);
        }
        return uri;
    }
}
