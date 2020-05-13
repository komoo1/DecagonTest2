package com.decagon.stock.repository;

import com.decagon.stock.model.Stock;
import com.decagon.stock.model.Transaction;
import com.decagon.stock.model.TransactionType;
import com.decagon.stock.model.User;
import com.decagon.stock.repository.data.TransactionData;
import com.decagon.stock.repository.data.TransactionSearchData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Victor.Komolafe
 */
@RunWith(SpringRunner.class)
@DataJpaTest()
@TestPropertySource("classpath:application.properties")
public class TransactionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransactionRepository transactionRepository;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private UUID testUUid = UUID.randomUUID();

    @Before
    public void init(){

        User user = new User();
        user.setUsername("user");
        user.setPasswordHash(BCrypt.hashpw("pass", BCrypt.gensalt()));
        user.setUuid(testUUid);
        entityManager.persist(user);

        Stock stock = new Stock();
        stock.setSymbol("NFLX");
        entityManager.persist(stock);

        Transaction t = new Transaction();
        t.setType(TransactionType.BUY);
        t.setOpenPrice(100.0D);
        t.setAmount(BigDecimal.TEN);
        t.setUuid(testUUid);
        t.setActive(true);
        t.setUser(user);
        t.setStock(stock);

        Transaction t2 = new Transaction();
        t2.setType(TransactionType.SELL);
        t2.setOpenPrice(100.0D);
        t2.setClosePrice(101.03D);
        t2.setAmount(BigDecimal.ONE);
        t2.setUser(user);
        t2.setStock(stock);

        entityManager.persist(t);
        entityManager.persist(t2);
    }

    @Test
    public void testFindAllTransactions() throws Exception {
        Page<TransactionSearchData> allTransactions =
                transactionRepository.findAllTransactions(testUUid, dateFormat.parse("2020-05-12 22:00:00"),
                    Date.from(Instant.now()), Pageable.unpaged());

        assertThat(allTransactions.getContent()).hasSize(1);
    }

    @Test
    public void testFindFirstByUuidNotFound(){
        Optional<TransactionData> transaction = transactionRepository.findFirstByUuid(UUID.randomUUID(), UUID.randomUUID());
        assertFalse(transaction.isPresent());
    }
    @Test
    public void testFindFirstByUuidFound(){
        Optional<TransactionData> transaction = transactionRepository.findFirstByUuid(testUUid, testUUid);
        assertTrue(transaction.isPresent());
    }


    @Test
    public void testUpdateTransactionOne(){
        int i = transactionRepository.updateTransaction(BigDecimal.ONE, testUUid);
        assertEquals(i, 1);

        Optional<TransactionData> transaction = transactionRepository.findFirstByUuid(testUUid, testUUid);
        assertTrue(transaction.isPresent());
        assertThat(transaction.get()).extracting(TransactionData::getAmount).isEqualTo(BigDecimal.ONE);
    }

    @Test
    public void testUpdateTransactionTwo(){
        int i = transactionRepository.updateTransaction(BigDecimal.ONE, 500.1, false, testUUid);
        assertEquals(i, 1);

        Optional<TransactionData> transaction = transactionRepository.findFirstByUuid(testUUid, testUUid);
        assertTrue(transaction.isPresent());
        assertThat(transaction.get()).matches(t -> !t.isActive() && BigDecimal.ONE.equals(t.getAmount()));
    }

}
