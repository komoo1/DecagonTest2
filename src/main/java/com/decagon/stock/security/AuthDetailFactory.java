package com.decagon.stock.security;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

import java.io.Serializable;

/**
 * @author Victor.Komolafe
 */
@RequestScope
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
@Component
// TODO threadlocal proxied scoped and register RequestContextListener/Filter
public class AuthDetailFactory implements Serializable {

    private static AuthDetail authDetail;

    public static void setAuthDetail(AuthDetail authDetail) {
        AuthDetailFactory.authDetail = authDetail;
    }

    public static AuthDetail getAuthDetail() {
        return authDetail;
    }
}
