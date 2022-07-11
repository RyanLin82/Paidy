package com.paidy.questions.mask;

import com.paidy.questions.exception.QuestionException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for {@link EmailMask}
 *
 * @author <a href="mailto:ryan.lin8216@gmail.com">Ryan Lin</a>
 */
final class EmailMaskTest {

    @ParameterizedTest
    @MethodSource("provideDefaultPositiveCases")
    void emailDefaultMaskPositive(String email, String expected) {
        var emailMask = new EmailMask();
        var result = emailMask.mask(email);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideCustomizePositiveCases")
    void emailCustomizeMaskPositive(String email, String expected) {
        var emailMask = new EmailMask('+');
        var result = emailMask.mask(email);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideExceptionCases")
    void emailMaskException(String email, Class<? extends RuntimeException> exception) {
        var emailMask = new EmailMask();
        assertThrows(exception, () -> emailMask.mask(email));
    }


    private static Stream<Arguments> provideDefaultPositiveCases() {
        return Stream.of(
                Arguments.of("local-part@domain-name.com  ", "l********t@domain-name.com"),
                Arguments.of("LOCAL-part@domain-name.com", "l********t@domain-name.com"),
                Arguments.of("ABC@domain-name.com  ", "a*c@domain-name.com"),
                Arguments.of("a@domain-name.com", "*@domain-name.com"),
                Arguments.of("ab@domain-name.com", "**@domain-name.com")


        );
    }

    private static Stream<Arguments> provideCustomizePositiveCases() {
        return Stream.of(
                Arguments.of("local-part@domain-name.com", "l++++++++t@domain-name.com"),
                Arguments.of("LOCAL-parT@domain-name.com  ", "l++++++++t@domain-name.com"),
                Arguments.of("ABC@domain-name.com", "a+c@domain-name.com"),
                Arguments.of("a@domain-name.com  ", "+@domain-name.com"),
                Arguments.of("ab@domain-name.com", "++@domain-name.com")
        );
    }

    private static Stream<Arguments> provideExceptionCases() {
        return Stream.of(
                Arguments.of("@domain-name.com", QuestionException.class),
                Arguments.of("local@-part@domain-name.com", QuestionException.class),
                Arguments.of("local-partdomain-name.com", QuestionException.class),
                Arguments.of(null, NullPointerException.class),
                Arguments.of("", QuestionException.class),
                Arguments.of("lo\"test\"cal-part@domain-name.com", QuestionException.class)
        );
    }
}
