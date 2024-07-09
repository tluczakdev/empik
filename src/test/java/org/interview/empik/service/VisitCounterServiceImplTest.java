package org.interview.empik.service;

import org.interview.empik.entity.VisitCounter;
import org.interview.empik.repository.VisitCounterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class VisitCounterServiceImplTest {

    @Autowired
    private VisitCounterRepository visitCounterRepository;

    private VisitCounterService visitCounterService;

    @BeforeEach
    void init(){
        this.visitCounterService = new VisitCounterServiceImpl(visitCounterRepository);
    }

    @AfterEach
    void clean(){
        visitCounterRepository.deleteAll();
    }

    @Test
    void createVisitCounterForNonExistsLoginAndSetRequestCounterOnOne() {
        // given
        String nonExistLogin = "nonExistLogin";

        // when
        visitCounterService.visitedBy(nonExistLogin);

        // then
        Optional<VisitCounter> oCreatedVisitCounter = visitCounterRepository.findByLogin(nonExistLogin);
        assertTrue(oCreatedVisitCounter.isPresent());

        VisitCounter createdVisitCounter = oCreatedVisitCounter.get();
        assertEquals(createdVisitCounter.getLogin(), nonExistLogin);
        assertEquals(createdVisitCounter.getRequestCounter(), 1);
    }

    @Test
    void incrementRequestCounterByOneForExistingLogin() {
        // given
        VisitCounter visitCounter = new VisitCounter(1L, "LOGIN", 100);
        visitCounterRepository.save(visitCounter);

        // when
        visitCounterService.visitedBy(visitCounter.getLogin());

        // then
        Optional<VisitCounter> oUpdatedVisitCounter = visitCounterRepository.findByLogin(visitCounter.getLogin());
        assertTrue(oUpdatedVisitCounter.isPresent());

        VisitCounter updatedVisitCounter = oUpdatedVisitCounter.get();
        assertEquals(updatedVisitCounter.getLogin(), visitCounter.getLogin());
        assertEquals(updatedVisitCounter.getRequestCounter(), visitCounter.getRequestCounter() + 1);
    }
}