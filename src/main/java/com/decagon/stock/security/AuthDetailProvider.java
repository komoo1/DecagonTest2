package com.decagon.stock.security;


import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Victor Komolafe
 */
@Service
public class  AuthDetailProvider {
    // TODO use security datastore and tokenization

    public final Map<String, AuthDetail> tokenStoreMap = new HashMap<>();

    public AuthDetail getAuthDetail(String token){
        return tokenStoreMap.get(token);
    }

    public void setAuthDetail(String token, AuthDetail authDetail) {
        tokenStoreMap.put(token, authDetail);
    }
}
