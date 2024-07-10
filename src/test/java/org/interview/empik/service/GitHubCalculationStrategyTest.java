package org.interview.empik.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.*;

class GitHubCalculationStrategyTest {


    /**
     * formula
     * 6 / followers * (2 + publicRepos)
     */
    @ParameterizedTest
    @MethodSource("calculations")
    public void calculationsShouldBeCalculateBasedOnFormula(int followers, int publicRepos, BigDecimal expectedCalculation) {
        // given
        GitHubCalculationStrategy gitHubCalculationStrategy = new GitHubCalculationStrategy();

        // when
        var calculation = gitHubCalculationStrategy.calculate(followers, publicRepos);

        // then
        assertEquals(calculation, expectedCalculation);
    }


    static Stream<Arguments> calculations() {
        return Stream.of(
                Arguments.of(1, 1, valueOf(2)),
                Arguments.of(0, 1, valueOf(0)),
                Arguments.of(1, 0, valueOf(3)),
                Arguments.of(-1, 1, valueOf(-2)),
                Arguments.of(1, -1, valueOf(6)),
                Arguments.of(1000, 0, valueOf(0))
        );
    }
}