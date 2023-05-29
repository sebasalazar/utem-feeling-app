package cl.utem.feeling.api.rest;

import cl.utem.feeling.api.vo.ErrorVO;
import cl.utem.feeling.exception.AuthException;
import cl.utem.feeling.exception.BadVoteException;
import cl.utem.feeling.exception.UtemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"cl.utem.feeling.api.rest"})
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    /**
     *
     * @param ex AuthException
     * @return Una entidad procesable por http con la descripción de un error
     * manejado
     */
    @ExceptionHandler({AuthException.class})
    public ResponseEntity<ErrorVO> handleException(AuthException ex) {
        LOGGER.error("Error en autenticación: {}", ex.getLocalizedMessage());
        LOGGER.debug("Error en autenticación: {}", ex.getMessage(), ex);

        final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        final ErrorVO error = new ErrorVO(ex.getMessage());

        return new ResponseEntity<>(error, httpStatus);
    }

    /**
     *
     * @param ex BadVoteException
     * @return Una entidad procesable por http con la descripción de un error
     * manejado
     */
    @ExceptionHandler({BadVoteException.class})
    public ResponseEntity<ErrorVO> handleException(BadVoteException ex) {
        LOGGER.error("Error en Votación: {}", ex.getLocalizedMessage());
        LOGGER.debug("Error en Votación: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorVO(ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     *
     * @param ex PagoWebException
     * @return Una entidad procesable por http con la descripción de un error
     * manejado
     */
    @ExceptionHandler({UtemException.class})
    public ResponseEntity<ErrorVO> handleException(UtemException ex) {
        LOGGER.error("Error manejado de UTEM: {}", ex.getLocalizedMessage());
        LOGGER.debug("Error manejado de UTEM: {}", ex.getMessage(), ex);

        final HttpStatus httpStatus = HttpStatus.valueOf(ex.getCode());
        final ErrorVO error = new ErrorVO(ex.getMessage());

        return new ResponseEntity<>(error, httpStatus);
    }

    /**
     *
     * @param ex Excepción inesperada
     * @return Un error 500 con la excepción generada
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorVO> handleException(Exception ex) {
        LOGGER.error("Error NO manejado: {}", ex.getLocalizedMessage());
        LOGGER.debug("Error NO manejado: {}", ex.getMessage(), ex);

        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorVO error = new ErrorVO(ex.getMessage());

        return new ResponseEntity<>(error, httpStatus);
    }
}
