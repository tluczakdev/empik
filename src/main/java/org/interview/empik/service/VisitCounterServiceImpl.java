package org.interview.empik.service;

import org.interview.empik.entity.VisitCounter;
import org.interview.empik.repository.VisitCounterRepository;
import org.springframework.stereotype.Service;

@Service
public class VisitCounterServiceImpl implements VisitCounterService {

    private final VisitCounterRepository visitCounterRepository;

    public VisitCounterServiceImpl(VisitCounterRepository visitCounterRepository) {
        this.visitCounterRepository = visitCounterRepository;
    }

    @Override
    public void visitedBy(String login) {

        VisitCounter visitCounter = visitCounterRepository.findByLogin(login)
                .map(vc -> {
                    vc.setRequestCounter(vc.getRequestCounter() + 1);
                    return vc;
                })
                .orElse(new VisitCounter(login, 1));

        visitCounterRepository.save(visitCounter);
    }
}
