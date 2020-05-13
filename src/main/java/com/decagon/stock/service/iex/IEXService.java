package com.decagon.stock.service.iex;

import org.springframework.stereotype.Service;

/**
 * @author Victor.Komolafe
 */
@Service
public interface IEXService {

    Double getIEXStockPrice(String symbol);

}
