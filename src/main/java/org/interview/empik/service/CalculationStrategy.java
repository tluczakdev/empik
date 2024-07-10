package org.interview.empik.service;

public interface CalculationStrategy<T, U, R> {

    T calculate(U followers, R publicRepos);
}
