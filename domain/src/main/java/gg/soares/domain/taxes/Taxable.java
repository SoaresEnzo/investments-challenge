package gg.soares.domain.taxes;

import java.math.BigDecimal;

public interface Taxable {
    BigDecimal getTaxableAmount();
    BigDecimal getTax();
}
