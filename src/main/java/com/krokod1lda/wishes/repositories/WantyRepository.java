package com.krokod1lda.wishes.repositories;

import com.krokod1lda.wishes.models.Wanty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WantyRepository extends CrudRepository<Wanty, Long> {
    @Query(value = "select * from wanty where wanty_name like %:wantyName%",
            nativeQuery = true)
    List<Wanty> findByWantyName(@Param("wantyName")String wantyName);

    List<Wanty> findByBuyerId(long buyerId);
    List<Wanty> findBySellerId(long sellerId);
    List<Wanty> findByClientId(long clientId);
}
