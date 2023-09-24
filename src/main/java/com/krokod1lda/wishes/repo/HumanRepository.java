package com.krokod1lda.wishes.repo;

import com.krokod1lda.wishes.models.Human;
import org.springframework.data.repository.CrudRepository;

public interface HumanRepository extends CrudRepository<Human, Long> {
}