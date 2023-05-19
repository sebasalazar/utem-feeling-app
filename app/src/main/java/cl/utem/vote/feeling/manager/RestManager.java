package cl.utem.vote.feeling.manager;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import okhttp3.CacheControl;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author https://experti.cl
 */
@Service("restManager")
public class RestManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${rest.connection.timeout}")
    private Integer connectionTimeout;

    @Value("${rest.request.timeout}")
    private Integer requestTimeout;

    /**
     * Cliente rest independiente del framework.
     */
    private OkHttpClient client = null;

    private static final String USER_AGENT = "Sebastian.cl/1.0.0";
    private static final String JSON_MIME = "application/json";
    private static final Integer MAX_REQUESTS = 2048;
    private static final Integer MAX_REQUESTS_PER_HOST = 1024;
    private static final Logger LOGGER = LoggerFactory.getLogger(RestManager.class);

    /**
     * Inicio de servicio, se setean los valores de timeout para la conexión.
     */
    @PostConstruct
    public void initService() {
        try {
            /**
             * Construimos el cliente que hará la conexión Sin framework
             */

            final ConnectionPool connectionPool = new ConnectionPool(MAX_REQUESTS_PER_HOST, (requestTimeout * 2), TimeUnit.SECONDS);
            final Dispatcher dispacher = new Dispatcher(Executors.newFixedThreadPool(MAX_REQUESTS_PER_HOST));
            dispacher.setMaxRequests(MAX_REQUESTS);
            dispacher.setMaxRequestsPerHost(MAX_REQUESTS_PER_HOST);

            /**
             * Construimos el cliente POST que hará la conexión hacia VTEX
             */
            this.client = new OkHttpClient.Builder()
                    .connectionPool(connectionPool)
                    .dispatcher(dispacher)
                    .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
                    .readTimeout(requestTimeout, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            LOGGER.info("[REST] Timeout de Conexión           : {} segundos", connectionTimeout);
            LOGGER.info("[REST] Timeout de Petición           : {} segundos", requestTimeout);
            LOGGER.info("[REST] Límite de conexiones          : {}", MAX_REQUESTS);
            LOGGER.info("[REST] Límite de conexiones por Host : {}", MAX_REQUESTS_PER_HOST);
            LOGGER.info("[REST] Límite de conexiones          : {}", client.dispatcher().getMaxRequests());
            LOGGER.info("[REST] Límite de conexiones por Host : {}", client.dispatcher().getMaxRequestsPerHost());
        } catch (Exception e) {
            LOGGER.error("Error al iniciar Servicio: {}", e.getMessage());
            LOGGER.debug("Error al iniciar Servicio: {}", e.getMessage(), e);
        }
    }

    @PreDestroy
    public void destroyService() {
        LOGGER.info("=== [START] Liberando TokenizarManager ===");
        try {
            if (client != null) {
                client.connectionPool().evictAll();
                client.dispatcher().cancelAll();
                client.dispatcher().executorService().shutdown();
            }
        } catch (Exception e) {
            LOGGER.error("Error al terminar Servicio: {}", e.getMessage());
            LOGGER.debug("Error al terminar Servicio: {}", e.getMessage(), e);
        }
        LOGGER.info("=== [END] Liberando TokenizarManager ===");
    }

    public String get(final String token, final String url) {
        String responseJson = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(url)) {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("User-Agent", USER_AGENT)
                        .addHeader("Accept", JSON_MIME)
                        .addHeader("Content-Type", JSON_MIME)
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .get()
                        .build();
                try ( Response response = client.newCall(request).execute()) {
                    if (response != null) {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            if (response.isSuccessful()) {
                                responseJson = StringUtils.trimToEmpty(responseBody.string());
                            } else {
                                LOGGER.error("[{}] [GET] [PETICION]  Intento de post a       :  '{}'", token, url);
                                LOGGER.error("[{}] [GET] [RESPUESTA] Código de Respuesta     :  '{}'", token, response.code());
                                LOGGER.error("[{}] [GET] [RESPUESTA] Cuerpo de Respuesta     :  '{}'", token, StringUtils.trimToEmpty(responseBody.string()));
                            }
                            responseBody.close();
                        }
                        response.close();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al hacer GET: {}", e.getMessage());
        }
        return responseJson;
    }

    /**
     *
     * @param token Token de procesamiento, útil para loguear
     * @param url URL para realizar el POST
     * @return Un Json con el resultado del posteo
     */
    public String delete(final String token, final String url) {
        String responseJson = StringUtils.EMPTY;
        try {
            LOGGER.debug("[{}] === Intento de eliminación de tarjeta, intento de DELETE a '{}'", token, url);
            if (StringUtils.isNotBlank(url)) {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("User-Agent", USER_AGENT)
                        .addHeader("Accept", JSON_MIME)
                        .addHeader("Content-Type", JSON_MIME)
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .delete()
                        .build();
                try ( Response response = client.newCall(request).execute()) {
                    if (response != null) {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            if (response.isSuccessful()) {
                                responseJson = StringUtils.trimToEmpty(responseBody.string());
                            } else {
                                LOGGER.error("[{}] [GET] [PETICION]  Intento de post a       :  '{}'", token, url);
                                LOGGER.error("[{}] [GET] [RESPUESTA] Código de Respuesta     :  '{}'", token, response.code());
                                LOGGER.error("[{}] [GET] [RESPUESTA] Cuerpo de Respuesta     :  '{}'", token, StringUtils.trimToEmpty(responseBody.string()));
                            }
                            responseBody.close();
                        }
                        response.close();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al hacer DELETE: {}", e.getMessage());
        }
        return responseJson;
    }

    /**
     *
     * @param token Token de procesamiento, útil para loguear
     * @param url URL para realizar el POST
     * @param json Json a postear
     * @return Un Json con el resultado del posteo
     */
    public String post(final String token, final String url, final String json) {
        String responseJson = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(url)) {
                RequestBody requestBody = RequestBody.create(MediaType.get(JSON_MIME), json);
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("User-Agent", USER_AGENT)
                        .addHeader("Accept", JSON_MIME)
                        .addHeader("Content-Type", JSON_MIME)
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .post(requestBody)
                        .build();
                try ( Response response = client.newCall(request).execute()) {
                    if (response != null) {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            if (response.isSuccessful()) {
                                responseJson = StringUtils.trimToEmpty(responseBody.string());
                            } else {
                                LOGGER.error("[{}] [GET] [PETICION]  Intento de post a       :  '{}'", token, url);
                                LOGGER.error("[{}] [GET] [RESPUESTA] Código de Respuesta     :  '{}'", token, response.code());
                                LOGGER.error("[{}] [GET] [RESPUESTA] Cuerpo de Respuesta     :  '{}'", token, StringUtils.trimToEmpty(responseBody.string()));
                            }
                            responseBody.close();
                        }
                        response.close();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al hacer POST: {}", e.getMessage());
        }
        return responseJson;
    }
}
