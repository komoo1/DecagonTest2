package com.decagon.stock.security;


import org.springframework.stereotype.Component;

/**
 * @author Victor.Komolafe
 */
@Component
public class AuthDetailFactoryHelper {

    private AuthDetail authDetail;
    public AuthDetailFactoryHelper(AuthDetailFactory factory){
        this.authDetail = factory.getAuthDetail();
    }
    public AuthDetail getAuthDetail() {
        return authDetail;
    }
}
