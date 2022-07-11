package com.paidy.questions.mask;

import com.paidy.questions.exception.QuestionException;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

/**
 * Mask phone number.
 *
 * @author <a href="mailto:ryan.lin8216@gmail.com">Ryan Lin</a>
 */
public class PhoneMask implements Mask {

    private static final String ERROR_MESSAGE = "phone number format error, phone number: %s";
    private static final char PHONE_PREFIX = '+';
    private char maskDigitsWithCharacter = '*';
    private char maskSpaceWithCharacter = '-';
    private int numberOfUnchanged = 4;

    public PhoneMask() {
    }

    public PhoneMask(int numberOfUnchanged, char maskDigitsWithCharacter, char maskSpaceWithCharacter) {
        this.numberOfUnchanged = numberOfUnchanged;
        this.maskDigitsWithCharacter = maskDigitsWithCharacter;
        this.maskSpaceWithCharacter = maskSpaceWithCharacter;
    }

    /**
     * Mask the input phone numbers.
     * The phone number consists of 9 digits(0-9) and may contain two characters(' ', '+').
     * Result will keep the last four digits, other digits will be converted to mask character (default *).
     * The space in the phone numbers will be converted to '-'. Between the digits can't contains more than one space.
     * The last four digits will keep origin (default keep 4 digits).
     * <p>
     * For example:
     * phoneNumber: +44 123 456 789
     * result: +**-***-**6-789
     *
     * @param phoneNumber input phone number
     * @return masked phone number
     * @throws NullPointerException if the input phone number is null.
     * @throws QuestionException    if the phone number format is wrong.
     */
    public String mask(@NonNull String phoneNumber) {
        phoneNumber = StringUtils.trim(phoneNumber);
        if (phoneNumber.length() == 0) {
            throw new QuestionException(String.format(ERROR_MESSAGE, phoneNumber));
        }
        var counter = 0;
        var digitsCounter = 0;
        var result = new StringBuilder();
        for (var i = phoneNumber.length() - 1; i >= 0; i--) {
            var tempChar = phoneNumber.charAt(i);
            if (Character.isDigit(tempChar) && counter != this.numberOfUnchanged) {
                result.append(tempChar);
                counter++;
                digitsCounter++;
            } else if (Character.isDigit(tempChar)) {
                result.append(this.maskDigitsWithCharacter);
                digitsCounter++;
            } else if (tempChar == ' ' && i + 1 < phoneNumber.length() && Character.isDigit(phoneNumber.charAt(i + 1))) {
                result.append(this.maskSpaceWithCharacter);
            } else if (tempChar == PHONE_PREFIX && i==0) {
                result.append(PHONE_PREFIX);
            } else {
                throw new QuestionException(String.format(ERROR_MESSAGE, phoneNumber));
            }
        }

        if (digitsCounter < 9) {
            throw new QuestionException(String.format(ERROR_MESSAGE, phoneNumber));
        }
        return result.reverse().toString();
    }
}
