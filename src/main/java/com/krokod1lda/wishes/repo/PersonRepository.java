package com.krokod1lda.wishes.repo;

import com.krokod1lda.wishes.models.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}