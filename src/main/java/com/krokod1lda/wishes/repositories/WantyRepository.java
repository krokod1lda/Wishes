package com.krokod1lda.wishes.repositories;

import com.krokod1lda.wishes.models.Wanty;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface WantyRepository extends CrudRepository<Wanty, Long> {
    @Query(value = "select * from wanty where wanty_name like %:wantyName%",
            nativeQuery = true)
    ArrayList<Wanty> findByWantyName(@Param("wantyName")String wantyName);
    ArrayList<Wanty> findByBuyerId(long buyerId);
    ArrayList<Wanty> findBySellerId(long sellerId);
    ArrayList<Wanty> findByClientId(long clientId);
    boolean existsBySellerIdOrBuyerIdOrClientId(long sellerId, long buyerId, long clientId);
    @Transactional
    @Modifying
    @Query(value = "delete from wanty where seller_id = :id or buyer_id = :id or client_id = :id",
            nativeQuery = true)
    void deleteAllTheWantiesByPersonId(@Param("id") long id);
}