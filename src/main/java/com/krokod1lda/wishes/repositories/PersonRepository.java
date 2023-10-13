package com.krokod1lda.wishes.repositories;

import com.krokod1lda.wishes.models.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findByType(String type);
    @Query(value = "select * from person where type = :type and is_archived = :archived",
            nativeQuery = true)
    List<Person> findByTypeAndArchived(@Param("type") String type, @Param("archived") boolean archived);
}