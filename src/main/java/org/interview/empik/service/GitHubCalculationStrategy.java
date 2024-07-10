package org.interview.empik.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.valueOf;

@Component
public class GitHubCalculationStrategy implements CalculationStrategy<BigDecimal, Integer, Integer> {

    /**
     * calculate 'calculations' based on the formula
     * 6 / followers * (2 + publicRepos)
     *
     * @param followers
     * @param publicRepos
     * @return calculations
     */
    @Override
    public BigDecimal calculate(Integer followers, Integer publicRepos) {
        BigDecimal numerator = valueOf(6);
        BigDecimal denominator = valueOf(followers).multiply(valueOf(2).add(valueOf(publicRepos)));
        return denominator.equals(BigDecimal.ZERO)
                ? BigDecimal.ZERO
                : numerator
                .divide(denominator, RoundingMode.HALF_UP);
    }
}
