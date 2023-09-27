package com.krokod1lda.wishes.repo;

import com.krokod1lda.wishes.models.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findByType(String type);
}