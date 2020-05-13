package com.decagon.stock.service.iex;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Victor.Komolafe
 */
@Profile("test")
@Service
public class IEXServiceImplTest implements IEXService {

    public Double getIEXStockPrice(String symbol){
        BigDecimal price = new BigDecimal(1011.01);
        return price.doubleValue();
    }
}
