package com.decagon.stock.repository;

import com.decagon.stock.model.Transaction;
import com.decagon.stock.repository.data.TransactionData;
import com.decagon.stock.repository.data.TransactionSearchData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Victor.Komolafe
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t where t.uuid = :uuid and t.user.uuid = :userUuid")
    Optional<TransactionData> findFirstByUuid(@Param("uuid") UUID uuid,
                                              @Param("userUuid") UUID userUuid);

    @Query("select t from Transaction t where t.user.uuid = :uuid and t.createdDate between :startdate and :enddate")
    Page<TransactionSearchData> findAllTransactions(@Param("uuid") UUID userUuid,
                                                    @Param("startdate") Date startdate,
                                                    @Param("enddate") Date enddate,
                                                    Pageable pageable);

    @Modifying
    @Query("update Transaction t set t.amount = :amount, t.closePrice = :price, t.active = :active where t.uuid = :uuid")
    int updateTransaction(@Param("amount")BigDecimal amount,
                          @Param("price") Double closePrice,
                          @Param("active") Boolean active,
                          @Param("uuid") UUID uuid);

    @Modifying
    @Query("update Transaction t set t.amount = :amount where t.uuid = :uuid")
    int updateTransaction(@Param("amount")BigDecimal amount, @Param("uuid") UUID uuid);
}
