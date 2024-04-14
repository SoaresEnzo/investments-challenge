package gg.soares.domain.taxes;

import java.math.BigDecimal;
import java.util.Collection;

public class TaxCalculator {
    public static BigDecimal calculate(final Taxable aTaxable) {
        return aTaxable.getTaxableAmount()
                .multiply(aTaxable.getTax());
    }

    public static BigDecimal calculate(final Collection<Taxable> taxables) {
        return taxables.stream()
                .map(TaxCalculator::calculate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
