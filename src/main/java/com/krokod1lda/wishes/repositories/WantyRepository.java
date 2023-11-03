package com.krokod1lda.wishes.repositories;

import com.krokod1lda.wishes.models.Wanty;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    @Query(value = "select * from wanty where date > :date1 and date < :date2 order by date asc",
    nativeQuery = true)
    ArrayList<Wanty> getEntriesByDates(@Param("date1") Date date1, @Param("date2") Date date2);

    /*
    Для подсчета количества записей для каждого значения id_seller,
    где поле date больше значения date1 и меньше значения date2,
    можно использовать следующий SQL-запрос:

    SELECT id_seller, COUNT(*) as count_records
    FROM wanty
    WHERE date > date1 AND date < date2
    GROUP BY id_seller;
    */


}