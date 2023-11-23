package com.krokod1lda.wishes.repositories;

import com.krokod1lda.wishes.models.PhoneNumber;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Long> {

    ArrayList<PhoneNumber> findByProjectId(long projectId);
    @Transactional
    @Modifying
    void deletePhoneNumbersByProjectId(long projectId);
}
