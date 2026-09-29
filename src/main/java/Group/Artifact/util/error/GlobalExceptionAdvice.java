package Group.Artifact.util.error;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import Group.Artifact.domain.dto.response.RestResponse;
import Group.Artifact.util.error.ExceptionCustom.AlreadyExistsException;
import Group.Artifact.util.error.ExceptionCustom.DateTimeInvalidException;
import Group.Artifact.util.error.ExceptionCustom.IdInvalidException;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<RestResponse<Object>> handleAllException(Exception exception){
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setError(exception.getMessage());
        res.setMessage("Sever has error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(res);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<RestResponse<Object>> handleDataIntegrityViolationException(DataIntegrityViolationException exception){
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Data Integrity Violation Exception");
        res.setMessage("Data Integrity Violation");
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(res);
    }

    @ExceptionHandler(value = BadCredentialsException.class ) 
    public ResponseEntity<RestResponse<Object>> handleBadCredentialsException(BadCredentialsException badCredentialsException){
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        res.setError(badCredentialsException.getMessage());
        res.setMessage("Username or password incorrect!!");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(res);
    }

    @ExceptionHandler(value = IdInvalidException.class)
    public ResponseEntity<RestResponse<Object>> handleIdInvalidException(IdInvalidException idInvalidException){
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(idInvalidException.getMessage());
        res.setMessage("Id invalid");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(res);
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<RestResponse<Object>> handleAlreadyExistsException(AlreadyExistsException alreadyExistsException){
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CONFLICT.value());
        res.setError(alreadyExistsException.getMessage());
        res.setMessage("Conflict");
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(res);
    }

    @ExceptionHandler(value = DateTimeInvalidException.class)
    public ResponseEntity<RestResponse<Object>> handleDateTimeInvalidException(DateTimeInvalidException dateTimeInvalidException){
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(dateTimeInvalidException.getMessage());
        res.setMessage("Date time invalid");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(res);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<RestResponse<Object>> handleDateTimeInvalidException(HttpMessageNotReadableException httpMessageNotReadableException){
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(httpMessageNotReadableException.getMessage());
        res.setMessage("Request body contains an invalid value");
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
    public ResponseEntity<RestResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException){
                RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(methodArgumentTypeMismatchException.getMessage());
        res.setMessage("Method Argument Type Mismatch Exception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(res);
    }
    
    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleTypeMismatch(NoResourceFoundException noResourceFoundException){
                RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(noResourceFoundException.getMessage());
        res.setMessage("No Resource Found Exception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(res);
    }
}
