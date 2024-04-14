package gg.soares.domain.investment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class InvestmentTest {

    @Test
    public void givenValidParams_whenCallNewInvestment_shouldInstantiateAnInvestment() {
        final var expectedValue = new BigDecimal("1000");
        final var expectedCreatedDate = Instant.now();
        final var expectedOwnerId = "123";

        final var actualInvestment = Investment.newInvestment(expectedCreatedDate, expectedValue, expectedOwnerId);

        Assertions.assertNotNull(actualInvestment);
        Assertions.assertEquals(expectedValue, actualInvestment.getInvestedAmount());
        Assertions.assertEquals(expectedCreatedDate, actualInvestment.getInvestmentDate());
        Assertions.assertEquals(expectedOwnerId, actualInvestment.getOwnerId());

    }
}