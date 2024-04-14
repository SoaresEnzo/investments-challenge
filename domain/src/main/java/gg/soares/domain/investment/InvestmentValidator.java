package gg.soares.domain.investment;

import gg.soares.domain.validation.Error;
import gg.soares.domain.validation.ValidationHandler;
import gg.soares.domain.validation.Validator;

import java.math.BigDecimal;
import java.time.Instant;

public class InvestmentValidator extends Validator {

    private final Investment investment;

    public InvestmentValidator(final Investment anInvestment, final ValidationHandler aHandler) {
        super(aHandler);
        this.investment = anInvestment;
    }

    @Override
    public void validate() {
        checkInvestmentValueConstraints();
        checkInvestmentCreationDateConstraints();
    }

    private void checkInvestmentValueConstraints() {
        final var value = this.investment.getInvestedAmount();

        if (value == null) {
            this.validationHandler().append(new Error("Investment 'value' should not be null"));
            return;
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            this.validationHandler().append(new Error("Investment 'value' cannot be lower than zero"));
        }
    }

    private void checkInvestmentCreationDateConstraints() {
        final var creationDate = this.investment.getInvestmentDate();

        if (creationDate == null) {
            this.validationHandler().append(new Error("Investment 'creationDate' should not be null"));
            return;
        }

        if (creationDate.isAfter(Instant.now())) {
            this.validationHandler().append(new Error("Investment 'creationDate' cannot be in the future"));
        }
    }
}