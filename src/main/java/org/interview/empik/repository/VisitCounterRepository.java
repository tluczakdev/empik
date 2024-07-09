package org.interview.empik.repository;

import org.interview.empik.entity.VisitCounter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitCounterRepository extends CrudRepository<VisitCounter, Long> {

    Optional<VisitCounter> findByLogin(String login);
}
