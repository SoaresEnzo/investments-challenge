package gg.soares.domain.investment;

import gg.soares.domain.exception.DomainException;
import gg.soares.domain.helpers.DateHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


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

    @Test
    public void givenANegativeValue_whenCallNewInvestment_shouldThrowError() {
        final var expectedValue = new BigDecimal("-1000");
        final var expectedCreatedDate = Instant.now();
        final var expectedOwnerId = "123";
        final var expectedMessage = "Investment 'value' cannot be lower than zero";

        final var anException = Assertions.assertThrows(DomainException.class, () -> Investment.newInvestment(expectedCreatedDate, expectedValue, expectedOwnerId));
        Assertions.assertEquals(expectedMessage, anException.getMessage());
    }

    @Test
    public void givenANullValue_whenCallNewInvestment_shouldThrowError() {
        final BigDecimal expectedValue = null;
        final var expectedCreatedDate = Instant.now();
        final var expectedOwnerId = "123";
        final var expectedMessage = "Investment 'value' should not be null";

        final var anException = Assertions.assertThrows(DomainException.class, () -> Investment.newInvestment(expectedCreatedDate, expectedValue, expectedOwnerId));
        Assertions.assertEquals(expectedMessage, anException.getMessage());
    }

    @Test
    public void givenAFutureDate_whenCallNewInvestment_shouldThrowError() {
        final var expectedValue = new BigDecimal("1000");
        final var expectedCreatedDate = Instant.now().plus(10, ChronoUnit.DAYS);
        final var expectedOwnerId = "123";
        final var expectedMessage = "Investment 'creationDate' cannot be in the future";

        final var anException = Assertions.assertThrows(DomainException.class, () -> Investment.newInvestment(expectedCreatedDate, expectedValue, expectedOwnerId));
        Assertions.assertEquals(expectedMessage, anException.getMessage());
    }

    @Test
    public void givenANullDate_whenCallNewInvestment_shouldThrowError() {
        final var expectedValue = new BigDecimal("1000");
        final Instant expectedCreatedDate = null;
        final var expectedMessage = "Investment 'creationDate' should not be null";
        final var expectedOwnerId = "123";

        final var anException = Assertions.assertThrows(DomainException.class, () -> Investment.newInvestment(expectedCreatedDate, expectedValue, expectedOwnerId));
        Assertions.assertEquals(expectedMessage, anException.getMessage());
    }

    @Test
    public void givenAValidInvestment_whenWithdrawingMoreThanExisting_shouldThrowError() {
        final var aValue = new BigDecimal("1000");
        final var aCreatedDate = Instant.now().minus(720, ChronoUnit.DAYS);
        final var anOwnerId = "123";
        final var anInvestment = Investment.newInvestment(aCreatedDate, aValue, anOwnerId);
        final var expectedMessage = "Insufficient funds";

        final var anException = Assertions.assertThrows(DomainException.class, () -> anInvestment.withdraw(new BigDecimal("2000")));
        Assertions.assertEquals(expectedMessage, anException.getMessage());
    }

    @Test
    public void givenAValidInvestment_whenGettingValueWithInterest_shouldReturnTheOriginalValuePlusInterest() {
        final var aValue = new BigDecimal("1000");
        final var aCreatedDate = DateHelper.subtractMonthsToInstant(Instant.now(), 10);
        final var anOwnerId = "123";
        final var anInvestment = Investment.newInvestment(aCreatedDate, aValue, anOwnerId);
        final var expectedValue = new BigDecimal("1053.23");

      Assertions.assertEquals(expectedValue, anInvestment.getInvestedValueWithInterest());
  }

    @Test
    public void givenAValidInvestment_whenWithdrawingManyTimes_shouldReturnTheInvestedValueWithInterestMinusWithdraws() {
        final var aValue = new BigDecimal("1000");
        final var aCreatedDate = DateHelper.subtractMonthsToInstant(Instant.now(), 10);
        final var anOwnerId = "123";
        final var anInvestment = Investment.newInvestment(aCreatedDate, aValue, anOwnerId);
        final var numberOfWithdraws = 100;
        final var numberToWithdraw = new BigDecimal("1");
        final var expectedValue = anInvestment.getInvestedValueWithInterest().subtract(numberToWithdraw.multiply(new BigDecimal(numberOfWithdraws)));

        for (int i=0; i< numberOfWithdraws; i++) {
            anInvestment.withdraw(numberToWithdraw);
        }

        Assertions.assertEquals(expectedValue, anInvestment.getInvestedValueWithInterest());
    }

    @Test
    public void givenAValidInvestment_whenWithdrawing_shouldUpdateInvestment() {
        final var aValue = new BigDecimal("1000.00");
        final var aCreatedDate = Instant.now().minus(720, ChronoUnit.DAYS);
        final var anOwnerId = "123";
        final var anInvestment = Investment.newInvestment(aCreatedDate, aValue, anOwnerId);

        anInvestment.withdraw(anInvestment.getInterestValue());
        Assertions.assertEquals(aValue, anInvestment.getInvestedAmount());
    }

}