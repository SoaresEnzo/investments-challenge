package gg.soares.domain.investment;

import gg.soares.domain.taxes.Taxable;

import java.math.BigDecimal;
import java.util.Objects;

public class InvestmentTax implements Taxable {
    private final Investment investment;

    private InvestmentTax(final Investment anInvestment) {
        this.investment = Objects.requireNonNull(anInvestment);
    }

    public static InvestmentTax of(final Investment anInvestment) {
        return new InvestmentTax(anInvestment);
    }

    @Override
    public BigDecimal getTaxableAmount() {
        return this.investment.getInterestValue();
    }

    @Override
    public BigDecimal getTax() {
        final Long investedMonths = this.investment.getInvestedMonths();
        if (investedMonths < 12L) {
            return new BigDecimal("0.225");
        }
        if (investedMonths < 24L) {
            return new BigDecimal("0.185");
        }
        return new BigDecimal("0.15");
    }
}
