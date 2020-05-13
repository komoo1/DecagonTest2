package com.decagon.stock.service.iex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.zankowski.iextrading4j.client.IEXCloudClient;
import pl.zankowski.iextrading4j.client.IEXCloudTokenBuilder;
import pl.zankowski.iextrading4j.client.IEXTradingApiVersion;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.PriceRequestBuilder;

import java.math.BigDecimal;

/**
 * @author Victor.Komolafe
 */
@Service
public class IEXServiceImpl implements IEXService {

    @Value("${iex.key.public}")
    private String iexPublicKey;

    @Value("${iex.key.private}")
    private String iexPrivateKey;

    public Double getIEXStockPrice(String symbol){
        final IEXCloudClient cloudClient = IEXTradingClient.create(IEXTradingApiVersion.IEX_CLOUD_V1_SANDBOX,
                new IEXCloudTokenBuilder()
                        .withPublishableToken(iexPublicKey)
                        .withSecretToken(iexPrivateKey)
                        .build());

        final BigDecimal price = cloudClient.executeRequest(new PriceRequestBuilder()
                .withSymbol(symbol)
                .build());

        return price.doubleValue();
    }

}
