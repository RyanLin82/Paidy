package com.paidy.questions;

import com.paidy.questions.exception.QuestionException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link OrdinalNumbersFormatter}
 *
 * @author <a href="mailto:ryan.lin8216@gmail.com">Ryan Lin</a>
 */
final class OrdinalNumbersFormatterTest {

    private final OrdinalNumbersFormatter ordinalNumbersFormatter = new OrdinalNumbersFormatter();

    @ParameterizedTest
    @MethodSource("providePositiveCases")
    void ordinalNumberPositive(String input, String expected) {
        var result = ordinalNumbersFormatter.parse(input);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideExceptionCases")
    void ordinalNumberException(String input, Class<? extends RuntimeException> exception) {
        assertThrows(exception, () -> ordinalNumbersFormatter.parse(input));
    }

    private static Stream<Arguments> provideExceptionCases() {
        return Stream.of(
                Arguments.of("-1", QuestionException.class),
                Arguments.of(null, NullPointerException.class),
                Arguments.of("abc01", QuestionException.class)
        );
    }

    private static Stream<Arguments> providePositiveCases() {
        return Stream.of(
                Arguments.of("0", "0th"),
                Arguments.of("00", "0th"),
                Arguments.of("01", "1st"),
                Arguments.of("002", "2nd"),
                Arguments.of("3", "3rd"),
                Arguments.of("10", "10th"),
                Arguments.of("0011", "11th"),
                Arguments.of("12", "12th"),
                Arguments.of("13", "13th"),
                Arguments.of("14", "14th"),
                Arguments.of("20", "20th"),
                Arguments.of("21", "21st"),
                Arguments.of("22", "22nd"),
                Arguments.of("23", "23rd"),
                Arguments.of("101", "101st"),
                Arguments.of("102", "102nd")
        );
    }
}
