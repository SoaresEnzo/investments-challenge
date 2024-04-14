package gg.soares.domain.investment;

import gg.soares.domain.AggregateRoot;
import gg.soares.domain.validation.Error;
import gg.soares.domain.validation.ValidationHandler;
import gg.soares.domain.validation.handler.ThrowsValidationHandler;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class Investment extends AggregateRoot<InvestmentID> {
    private final Instant creationDate;
    private BigDecimal value;
    private final String ownerId;
    private static final BigDecimal TAX = new BigDecimal("0.0052");

    private Investment(
            final InvestmentID id,
            final Instant creationDate,
            final BigDecimal value,
            final String ownerId
    ) {
        super(id);
        this.creationDate = creationDate;
        this.value = value;
        this.ownerId = ownerId;
    }

    public static Investment newInvestment(
            final Instant creationDate,
            final BigDecimal value,
            final String ownerId
    ) {
        return new Investment(InvestmentID.unique(), creationDate, value, ownerId);
    }

    public static Investment with(
            final InvestmentID id,
            final Instant creationDate,
            final BigDecimal value,
            final String ownerId
    ) {
        return new Investment(id, creationDate, value, ownerId);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new InvestmentValidator(this, handler).validate();
    }

    public Instant getInvestmentDate() {
        return creationDate;
    }

    public Long getInvestedMonths() {
        YearMonth monthCreation = YearMonth.from(this.getInvestmentDate());
        YearMonth monthNow = YearMonth.from(Instant.now());

        return ChronoUnit.MONTHS.between(monthNow, monthCreation);
    }

    public BigDecimal getInvestedAmount() {
        return value;
    }

    public BigDecimal getInvestedValueWithInterest() {
        BigDecimal accumulatedTax = new BigDecimal("1").add(TAX);
        for (int i = 0; i < this.getInvestedMonths(); i++) {
            accumulatedTax = accumulatedTax.multiply(new BigDecimal("1").add(TAX));
        }
        return this.value.multiply(accumulatedTax);
    }

    public BigDecimal getInterestValue() {
        return this.getInvestedValueWithInterest().subtract(this.value);
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void withdraw(BigDecimal value) {
        ValidationHandler thrower = new ThrowsValidationHandler();

        if (value.compareTo(this.value) > 0) {
            Error error = new Error("Withdraw value cannot be higher than the investment value");
            thrower.append(error);
        }
        this.value = this.value.subtract(value);
    }
}
