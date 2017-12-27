package com.mindzen.drools.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends PagingAndSortingRepository<RuleTable, Integer>{

}