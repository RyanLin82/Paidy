package com.paidy.questions;

import com.paidy.questions.exception.QuestionException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Change integer to ordinal numbers.
 *
 * @author <a href="mailto:ryan.lin8216@gmail.com">Ryan Lin</a>
 */
final class OrdinalNumbersFormatter {

    private static final String ZERO = "0";
    private static final String POSTFIX = "th";

    /**
     * Format the given input string to ordinal numbers.
     *
     * @param input given string
     * @return ordinal number
     * @throws NullPointerException if given input is null.
     * @throws QuestionException if given input is not numeric.
     */
    public String parse(@NonNull String input) {
        if (!StringUtils.isNumeric(input)) {
            throw new QuestionException(String.format("Input can't be parsed to integer. input: %s", input));
        }
        if (input.length() > 1) {
            input = StringUtils.stripStart(input, ZERO);
            if (input.length() == 0) {
                input = ZERO;
            }
        }
        var specialCase = SpecialCase.find(input);
        if (StringUtils.isNotEmpty(specialCase)) {
            return specialCase;
        }

        var lastCharacter = input.charAt(input.length() - 1);
        var postFixEnum = OrdinalIndicatorSuffix.find(lastCharacter);
        if (Objects.isNull(postFixEnum)) {
            postFixEnum = POSTFIX;
        }
        return input + postFixEnum;
    }

    @AllArgsConstructor
    @Getter
    enum SpecialCase {
        ELEVENTH("11", "11th"),
        TWELFTH("12", "12th"),
        THIRTEENTH("13", "13th");

        private final String number;
        private final String ordinalIndicator;

        private static final Map<String, String> TYPE_MAP = Collections.unmodifiableMap(EnumSet.allOf(SpecialCase.class)
                .stream().collect(Collectors.toMap(SpecialCase::getNumber, SpecialCase::getOrdinalIndicator)));

        public static String find(final String s) {
            return TYPE_MAP.get(s);
        }

    }

    @AllArgsConstructor
    @Getter
    enum OrdinalIndicatorSuffix {
        ONE('1', "st"),
        TWO('2', "nd"),
        THREE('3', "rd");

        private final char number;
        private final String suffix;

        private static final Map<Character, String> TYPE_MAP = Collections.unmodifiableMap(EnumSet.allOf(OrdinalIndicatorSuffix.class)
                .stream().collect(Collectors.toMap(OrdinalIndicatorSuffix::getNumber, OrdinalIndicatorSuffix::getSuffix)));

        public static String find(final char s) {
            return TYPE_MAP.get(s);
        }
    }
}
