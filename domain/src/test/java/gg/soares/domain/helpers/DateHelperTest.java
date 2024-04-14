package gg.soares.domain.helpers;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateHelperTest {

    @Test
    public void givenValidParams_whenCallAddMonthsToInstant_shouldReturnInstantWithAddedMonths() {
        final var instant = Instant.now();
        final var months = 10;

        final var actualInstant = DateHelper.addMonthsToInstant(instant, months);

        assertNotNull(actualInstant);
        assertEquals(
                instant.atZone(ZoneId.systemDefault()).toLocalDate().plusMonths(months),
                actualInstant.atZone(ZoneId.systemDefault()).toLocalDate()
        );
    }

    @Test
    public void givenValidParams_whenCallSubtractMonthsToInstant_shouldReturnInstantWithSubtractedMonths() {
        final var instant = Instant.now();
        final var months = 10;

        final var actualInstant = DateHelper.subtractMonthsToInstant(instant, months);

        assertNotNull(actualInstant);
        assertEquals(
                instant.atZone(ZoneId.systemDefault()).toLocalDate().minusMonths(months),
                actualInstant.atZone(ZoneId.systemDefault()).toLocalDate()
        );
    }

    @Test
    public void givenValidParams_whenCallGetMonthsBetweenInstants_shouldReturnDifferenceOfTimeInMonths() {
        final var expectedMonths = 10;
        final var start = Instant.now();
        final var end = DateHelper.addMonthsToInstant(start, expectedMonths);

        final var actualMonths = DateHelper.getMonthsBetweenInstants(start, end);

        assertNotNull(actualMonths);
        assertEquals(expectedMonths, actualMonths);
    }


}