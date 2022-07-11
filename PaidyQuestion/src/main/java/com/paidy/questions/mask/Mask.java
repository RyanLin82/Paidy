package com.paidy.questions.mask;

/**
 * interface of mask
 *
 * @author <a href="mailto:ryan.lin8216@gmail.com">Ryan Lin</a>
 */
public interface Mask {
    /**
     * Mask the input.
     *
     * @param input data to be masked
     * @return masked input.
     */
    String mask(String input);
}
