package Group.Artifact.util.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import Group.Artifact.domain.dto.response.RestResponse;

@ControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = {BadCredentialsException.class, IdInvalidException.class} ) 
    public ResponseEntity<RestResponse<Object>> handleException(RuntimeException runtimeException){
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(runtimeException.getMessage());
        res.setMessage("Exception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(res);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> handelValidationError(MethodArgumentNotValidException exception){     
        BindingResult bindingResult = exception.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors(); 

        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(exception.getBody().getDetail());
        
        List<String> errors = new ArrayList<>();
        fieldErrors.forEach(error -> errors.add("Error: " + error.getField() + "("+ error.getDefaultMessage()+")"));
        res.setMessage(errors.size()>1 ?  errors : errors.get(0));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(res);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException MethodArgumentTypeMismatchException){
                RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(MethodArgumentTypeMismatchException.getMessage());
        res.setMessage("Exception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(res);
    }
    
}
