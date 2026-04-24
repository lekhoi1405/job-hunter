package Group.Artifact.util;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import Group.Artifact.domain.RestResponse;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object>{

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //khi nao can ghi de
        //true la luon luon format
        return true;
    }
    
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
                HttpServletResponse httpServletResponse = ((ServletServerHttpResponse)response).getServletResponse();
                int status = httpServletResponse.getStatus();
                RestResponse<Object> res = new RestResponse<>();
                res.setStatusCode(status);
                if(body instanceof String){
                    return body;
                }
                if(status >= 400){
                    return body;
                }else{
                    res.setData(body);
                    res.setMessage("Call Api Success");
                }
                return res;
    }
}

