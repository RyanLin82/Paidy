package com.paidy.questions.mask;

import com.paidy.questions.exception.QuestionException;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * This class provides the method to mask the email by special character.
 *
 * @author <a href="mailto:ryan.lin8216@gmail.com">Ryan Lin</a>
 */
public class EmailMask implements Mask {

    /**
     * if email local part length is less than this number, all local part will be masked.
     */
    private static final int SPECIAL_CASE = 2;
    private static final char CHAR_AT = '@';

    private final EmailValidator emailValidator = EmailValidator.getInstance();

    private char maskCharacter = '*';

    public EmailMask() {
    }

    public EmailMask(char maskCharacter) {
        this.maskCharacter = maskCharacter;
    }

    /**
     * The email will be trimmed and transformed to lowercase.
     * If the email is valid, then convert the mail to lowercase and all characters in the local-part between the first and last should be replaced by mask character(default *).
     * <p>
     * For example:
     * case1:
     * email: Local-part@domain-name.com
     * result: l********t@domain-name.com
     * <p>
     * case2:
     * email: A@domain-name.com
     * result: *@domain-name.com
     * <p>
     * case3:
     * email: AB@domain-name.com
     * result: **@domain-name.com
     *
     * @param email input email
     * @return masked email
     * @throws NullPointerException if the input email is null.
     * @throws QuestionException    if the phone number format is wrong.
     */
    public String mask(@NonNull String email) {
        email = StringUtils.trim(email).toLowerCase();
        if (!this.emailValidator.isValid(email)) {
            throw new QuestionException(String.format("email input format error, email: %s", email));
        }

        var indexOfAt = email.indexOf(CHAR_AT);
        var sb = new StringBuilder(email);
        if (indexOfAt <= SPECIAL_CASE) {
            for (var i = 0; i < indexOfAt; i++) {
                sb.setCharAt(i, maskCharacter);
            }
        } else {
            for (var i = 1; i < indexOfAt - 1; i++) {
                sb.setCharAt(i, maskCharacter);
            }
        }
        return sb.toString();
    }
}
