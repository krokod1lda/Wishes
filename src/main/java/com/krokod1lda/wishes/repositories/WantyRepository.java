package com.krokod1lda.wishes.repositories;

import com.krokod1lda.wishes.models.Wanty;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.ArrayList;

public interface WantyRepository extends CrudRepository<Wanty, Long> {
    @Query(value = "select * from wanty where wanty_name like %:wantyName% order by date desc",
            nativeQuery = true)
    ArrayList<Wanty> findByWantyName(@Param("wantyName")String wantyName);

    @Query(value = "select * from wanty where buyer_id = :buyerId order by date desc",
            nativeQuery = true)
    ArrayList<Wanty> findByBuyerId(@Param("buyerId") long buyerId);
    @Query(value = "select * from wanty where seller_id = :sellerId order by date desc",
            nativeQuery = true)
    ArrayList<Wanty> findBySellerId(@Param("sellerId") long sellerId);
    @Query(value = "select * from wanty where client_id = :clientId order by date desc",
            nativeQuery = true)
    ArrayList<Wanty> findByClientId(@Param("clientId") long clientId);

    @Transactional
    @Modifying
    @Query(value = "delete from wanty where seller_id = :id or buyer_id = :id or client_id = :id",
            nativeQuery = true)
    void deleteAllTheWantiesByPersonId(@Param("id") long id);

    @Query(value = "select * from wanty where date between :date1 and :date2 order by date asc",
            nativeQuery = true)
    ArrayList<Wanty> getEntriesByDates(@Param("date1") Date date1, @Param("date2") Date date2);

    @Query(value = "select * from wanty order by date desc",
            nativeQuery = true)
    ArrayList<Wanty> findAllDesc();
}