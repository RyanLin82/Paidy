package com.paidy.questions.mask;

import com.paidy.questions.exception.QuestionException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link PhoneMask}
 *
 * @author <a href="mailto:ryan.lin8216@gmail.com">Ryan Lin</a>
 */
final class PhoneMaskTest {

    @ParameterizedTest
    @MethodSource("provideDefaultPositiveCases")
    void phoneNumberDefaultMaskPositive(String phoneNumber, String expected) {
        var phoneMask = new PhoneMask();
        var result = phoneMask.mask(phoneNumber);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideCustomizePositiveCases")
    void phoneNumberCustomizeMaskPositive(String phoneNumber, String expected) {
        var phoneMask = new PhoneMask(6, '@', '-');
        var result = phoneMask.mask(phoneNumber);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideExceptionCases")
    void phoneNumberMaskException(String phoneNumber, Class<? extends RuntimeException> exception) {
        var phoneMask = new PhoneMask();
        assertThrows(exception, () -> phoneMask.mask(phoneNumber));
    }

    private static Stream<Arguments> provideDefaultPositiveCases() {
        return Stream.of(
                Arguments.of("+44 123 456 789", "+**-***-**6-789"),
                Arguments.of("04 07 20 1234 333", "**-**-**-***4-333")
        );
    }

    private static Stream<Arguments> provideCustomizePositiveCases() {
        return Stream.of(
                Arguments.of("+44 123 456 789", "+@@-@@@-456-789"),
                Arguments.of("04 07 20 1234 333", "@@-@@-@@-@234-333")
        );
    }

    private static Stream<Arguments> provideExceptionCases() {
        return Stream.of(
                Arguments.of("-44 123 456 789", QuestionException.class),
                Arguments.of("04  07 20 1234 333", QuestionException.class),
                Arguments.of("456 789", QuestionException.class),
                Arguments.of(null, NullPointerException.class),
                Arguments.of("", QuestionException.class),
                Arguments.of("04     333", QuestionException.class),
                Arguments.of("04 07+20 1234 333", QuestionException.class)
        );
    }
}
