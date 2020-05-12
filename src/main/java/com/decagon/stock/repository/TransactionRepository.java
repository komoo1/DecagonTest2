package com.decagon.stock.repository;

import com.decagon.stock.model.Transaction;
import com.decagon.stock.model.User;
import com.decagon.stock.repository.data.TransactionData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Victor.Komolafe
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionCustomRepository {

    Optional<TransactionData> findFirstByUuid(UUID uuid);

    List<TransactionData> findByUserAndCreatedDateBetween(User user, Date start, Date end, Pageable pageable);

    @Modifying
    @Query("update Transaction t set t.closePrice = :price, t.active = :active where t.uuid = :uuid")
    int updateTransaction(@Param("price") Double closePrice,
                          @Param("active") Boolean active,
                          @Param("uuid") UUID uuid);

    @Modifying
    @Query("update Transaction t set t.active = :active where t.uuid = :uuid")
    int updateTransaction(@Param("active") Boolean active,
                          @Param("uuid") UUID uuid);
}
