package gg.soares.domain.investment;

import gg.soares.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class InvestmentID extends Identifier {
    private final String value;

    private InvestmentID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static InvestmentID unique() {
        return InvestmentID.from(UUID.randomUUID());
    }

    public static InvestmentID from(final String anId) {
        return new InvestmentID(anId);
    }

    public static InvestmentID from(final UUID anId) {
        return new InvestmentID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvestmentID that = (InvestmentID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
