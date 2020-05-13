package com.decagon.stock.repository.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Victor.Komolafe
 */
public interface UserData extends Serializable  {

    String getUsername();

    UUID getUuid();
}
