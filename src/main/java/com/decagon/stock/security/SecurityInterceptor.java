package com.decagon.stock.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 *
 * @author Victor.Komolafe
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private final AuthDetailProvider authDetailProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        SecureAccess annotation = getMethodAnnotation(SecureAccess.class, handlerMethod);
        if (annotation == null) {
            return true;
        }

        String authToken = getHeaderParam(request, "authToken");

        if(StringUtils.isEmpty(authToken)){
            throw new IllegalAccessException("Invalid authentication token");
        }

        AuthDetail authDetail = authDetailProvider.getAuthDetail(authToken);
        if(authDetail==null){
            throw new IllegalAccessException("Invalid authentication token");
        }

        return super.preHandle(request, response, handler);
    }

    private  <A extends Annotation> A getMethodAnnotation(Class<A> annotationClass, HandlerMethod handlerMethod){
        A annotation = handlerMethod.getMethodAnnotation(annotationClass);

        if (annotation == null) {
            annotation = AnnotationUtils.findAnnotation(handlerMethod.getMethod().getDeclaringClass(), annotationClass);
        }

        return annotation;
    }

    private  String getHeaderParam(HttpServletRequest request, String headerParamName){
        String headerValue = request.getHeader(headerParamName);

        if(StringUtils.isEmpty(headerValue)) {
            Map<String, String[]> parameterMap = request.getParameterMap();

            if(parameterMap.containsKey(headerParamName)) {
                String[] values = parameterMap.get(headerParamName);

                if(values != null && values.length > 0) {
                    headerValue = values[0];
                }
            }
        }

        return headerValue;
    }

}
