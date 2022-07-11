package com.paidy.questions;

import com.paidy.questions.exception.QuestionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link DayOfWeekCounter}
 *
 * @author <a href="mailto:ryan.lin8216@gmail.com">Ryan Lin</a>
 */
final class DayOfWeekCounterTest {

    @ParameterizedTest
    @MethodSource("provideCountDayOfSundayPositive")
    void countDayOfSundayInPeriod(String fromDate, String toDate, int expected) {
        var dateCounter = new DayOfWeekCounter();
        var result = dateCounter.count(fromDate, toDate);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideCountDayOfFridayPositive")
    void countDayOfFridayInPeriod(String fromDate, String toDate, int expected) {
        var dateCounter = new DayOfWeekCounter(5);
        var result = dateCounter.count(fromDate, toDate);
        assertEquals(expected, result);
    }

    @Test
    void countDayOfSettingError() {
        assertThrows(QuestionException.class, () -> new DayOfWeekCounter(9));

    }

    @ParameterizedTest
    @MethodSource("provideDateOfPeriodWithExceptionResult")
    void countDayOfWeekInPeriod(String fromDate, String toDate, Class<? extends RuntimeException> excpetion) {
        var dateCounter = new DayOfWeekCounter();
        assertThrows(excpetion, () -> dateCounter.count(fromDate, toDate));
    }

    private static Stream<Arguments> provideCountDayOfSundayPositive() {
        return Stream.of(
                Arguments.of("01-07-2022", "30-07-2022", 4),
                Arguments.of("04-07-2022", "08-07-2022", 0)
        );
    }

    private static Stream<Arguments> provideCountDayOfFridayPositive() {
        return Stream.of(
                Arguments.of("01-07-2022", "30-07-2022", 5),
                Arguments.of("04-07-2022", "08-07-2022", 0)
        );
    }

    private static Stream<Arguments> provideDateOfPeriodWithExceptionResult() {
        return Stream.of(
                Arguments.of("32-07-2022", "30-07-2022", DateTimeParseException.class),
                Arguments.of("04 -07-2022", "08-07-2022", DateTimeParseException.class),
                Arguments.of("04-07-2022", "01-07-2022", QuestionException.class),
                Arguments.of(null, "01-07-2022", NullPointerException.class),
                Arguments.of("04-07-2022", null, NullPointerException.class)
        );
    }
}
