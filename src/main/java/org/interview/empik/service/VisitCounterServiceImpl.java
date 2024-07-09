package org.interview.empik.service;

import org.interview.empik.entity.VisitCounter;
import org.interview.empik.repository.VisitCounterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VisitCounterServiceImpl implements VisitCounterService {

    private final VisitCounterRepository visitCounterRepository;

    public VisitCounterServiceImpl(VisitCounterRepository visitCounterRepository) {
        this.visitCounterRepository = visitCounterRepository;
    }

    @Override
    public void visitedBy(String login) {

        if (login == null || login.isBlank()) return;

        Optional<VisitCounter> oVisitCounter = visitCounterRepository.findByLogin(login);

        if (oVisitCounter.isPresent()) {
            var visitCounter = oVisitCounter.get();
            visitCounter.setRequestCounter(visitCounter.getRequestCounter() + 1);
            visitCounterRepository.save(visitCounter);
        } else {
            visitCounterRepository.save(new VisitCounter(login, 1));
        }
    }
}
