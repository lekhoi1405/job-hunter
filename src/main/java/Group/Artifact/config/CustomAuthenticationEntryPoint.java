package Group.Artifact.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import Group.Artifact.domain.RestResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final AuthenticationEntryPoint authenticationEntryPoint = new BearerTokenAuthenticationEntryPoint();

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        this.authenticationEntryPoint.commence(request, response, authException);
        response.setContentType("application/json");
        RestResponse<Object> restResponse = new RestResponse<>();

        restResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        restResponse.setMessage("token invalid");

        String errorMessage = Optional.ofNullable(authException.getCause())
                    .map(Throwable::getMessage)
                    .orElse(authException.getMessage());
        restResponse.setError(errorMessage);
        // String errorMessage;

        // if (authException.getCause() != null) {
        //     errorMessage = authException.getCause().getMessage();
        // } else {
        //     errorMessage = authException.getMessage();
        // }
        this.objectMapper.writeValue(response.getWriter(), restResponse);
    }
    
}
