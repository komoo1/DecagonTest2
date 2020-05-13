package util;

import com.decagon.stock.model.TransactionType;
import com.decagon.stock.repository.data.StockData;
import com.decagon.stock.repository.data.TransactionData;
import com.decagon.stock.repository.data.TransactionSearchData;
import com.decagon.stock.repository.data.UserData;
import org.assertj.core.internal.bytebuddy.utility.RandomString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author Victor.Komolafe
 */
public class TestDataUtil {

    public static TransactionSearchData createMockTransactionSearchData(UUID txUuid, String symbol) {
        return new TransactionSearchData() {
            @Override
            public TransactionType getType() {
                return TransactionType.BUY;
            }

            @Override
            public BigDecimal getAmount() {
                return BigDecimal.TEN;
            }

            @Override
            public Double getOpenPrice() {
                return new Random().nextDouble();
            }

            @Override
            public Double getClosePrice() {
                return null;
            }

            @Override
            public Date getCreatedDate() {
                return Date.from(Instant.now());
            }
            @Override
            public boolean isActive() {
                return true;
            }

            @Override
            public UUID getUuid() {
                return txUuid;
            }
            @Override
            public StockData getStock() {
                return (StockData) () -> symbol;
            }
        };
    }

    public static TransactionData createMockTransactionData(UUID txUuid, UUID userUuid, String symbol) {
        return new TransactionData() {
            @Override
            public TransactionType getType() {
                return TransactionType.BUY;
            }

            @Override
            public BigDecimal getAmount() {
                return BigDecimal.TEN;
            }

            @Override
            public Double getOpenPrice() {
                return new Random().nextDouble();
            }

            @Override
            public Double getClosePrice() {
                return null;
            }

            @Override
            public boolean isActive() {
                return true;
            }

            @Override
            public UUID getUuid() {
                return txUuid;
            }

            @Override
            public UserData getUser() {
                return new UserData() {
                    @Override
                    public String getUsername() {
                        return RandomString.make(10);
                    }

                    @Override
                    public UUID getUuid() {
                        return userUuid;
                    }
                };
            }

            @Override
            public StockData getStock() {
                return new StockData() {
                    @Override
                    public String getSymbol() {
                        return symbol;
                    }
                };
            }
        };
    }
}
