package it.cle.webproject.validation;

import it.cle.webproject.dto.ResultDTO;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Maps;


// TODO: Auto-generated Javadoc
/**
 * The Class DefaultExceptionHandler.
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    /**
     * Handle request exception.
     *
     * @param ex the ex
     * @return the result dto
     */
    @RequestMapping(produces = {"application/json"})
    @ExceptionHandler({MissingServletRequestParameterException.class,
            UnsatisfiedServletRequestParameterException.class,
            HttpRequestMethodNotSupportedException.class,
            ServletRequestBindingException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ResultDTO handleRequestException(Exception ex) {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Request Error");
        map.put("cause", ex.getMessage());
        ResultDTO result = new ResultDTO();
		result.setMap(map);
        return result;
    }

    /**
     * Handle validation exception.
     *
     * @param ex the ex
     * @return the result dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @RequestMapping(produces = {"application/json"})
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ResultDTO handleValidationException(ConstraintViolationException ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Validation Failure");
        map.put("violations", convertConstraintViolation(ex.getConstraintViolations()));
        ResultDTO result = new ResultDTO();
		result.setMap(map);
        return result;
    }

    /**
     * Handle validation exception.
     *
     * @param ex the ex
     * @return the result dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @RequestMapping(produces = {"application/json"})
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public  ResultDTO handleValidationException(MethodArgumentNotValidException ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Validation Failure");
//        map.put("error", this.messages.getMessage("field.required", new Object [] {"UserName"}, "Required", null));
        map.put("violations", convertConstraintViolation(ex));
        ResultDTO result = new ResultDTO();
		result.setMap(map);
        return result;
    }

    /**
     * Handle validation exception.
     *
     * @param ex the ex
     * @return the result dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @RequestMapping(produces = {"application/json"})
    @ExceptionHandler(ObjectRetrievalFailureException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ResultDTO handleValidationException(ObjectRetrievalFailureException ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Entity Not Found");
        map.put("cause", ex.getMessage());
        ResultDTO result = new ResultDTO();
		result.setMap(map);
        return result;
    }

    /**
     * Handle data integrity violation exception.
     *
     * @param ex the ex
     * @return the result dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @RequestMapping(produces = {"application/json"})
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody ResultDTO handleDataIntegrityViolationException(DataIntegrityViolationException ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Data Integrity Error");
        map.put("cause", ex.getCause().getCause().getLocalizedMessage());
        ResultDTO result = new ResultDTO();
		result.setMap(map);
        return result;
    }

    /**
     * Handle data access exception.
     *
     * @param ex the ex
     * @return the result dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @RequestMapping(produces = {"application/json"})
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ResultDTO handleDataAccessException(DataAccessException ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Data Error");
        map.put("cause", ex.getCause().getMessage());
        ResultDTO result = new ResultDTO();
		result.setMap(map);
        return result;
    }

    /**
     * Handle unsupported media type exception.
     *
     * @param ex the ex
     * @return the result dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @RequestMapping(produces = {"application/json"})
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public @ResponseBody ResultDTO handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Unsupported Media Type");
        map.put("cause", ex.getLocalizedMessage());
        map.put("supported", ex.getSupportedMediaTypes());
        ResultDTO result = new ResultDTO();
		result.setMap(map);
        return result;
    }

    /**
     * Handle uncaught exception.
     *
     * @param ex the ex
     * @return the result dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @RequestMapping(produces = {"application/json"})
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ResultDTO handleUncaughtException(Exception ex) throws IOException {
        Map<String, Object>  map = Maps.newHashMap();
        map.put("error", "Unknown Error");
        if (ex.getCause() != null) {
            map.put("cause", ex.getCause().getMessage());
        } else {
            map.put("cause", ex.getMessage());
        }
        ResultDTO result = new ResultDTO();
		result.setMap(map);
        return result;
    }

    /**
     * Convert constraint violation.
     *
     * @param constraintViolations the constraint violations
     * @return the map
     */
    private Map<String, Map<String, Object> > convertConstraintViolation(Set<ConstraintViolation<?>> constraintViolations) {
        Map<String, Map<String, Object> > result = Maps.newHashMap();
        for (ConstraintViolation constraintViolation : constraintViolations) {
            Map<String, Object>  violationMap = Maps.newHashMap();
            violationMap.put("value", constraintViolation.getInvalidValue());
            violationMap.put("type", constraintViolation.getRootBeanClass());
            violationMap.put("message", constraintViolation.getMessage());
            result.put(constraintViolation.getPropertyPath().toString(), violationMap);
        }
        return result;
    }

    /**
     * Convert constraint violation.
     *
     * @param ex the ex
     * @return the map
     */
    private Map<String, Map<String, Object> > convertConstraintViolation(MethodArgumentNotValidException ex) {
        Map<String, Map<String, Object> > result = Maps.newHashMap();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            Map<String, Object>  violationMap = Maps.newHashMap();
            violationMap.put("target", ex.getBindingResult().getTarget());
            violationMap.put("type", ex.getBindingResult().getTarget().getClass());
            violationMap.put("field", error.toString());
            violationMap.put("message", error.getDefaultMessage());
            result.put(error.getObjectName(), violationMap);
        }
        return result;
    }

}