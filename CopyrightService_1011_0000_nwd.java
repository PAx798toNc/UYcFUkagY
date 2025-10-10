// 代码生成时间: 2025-10-11 00:00:22
package com.example.copyright;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Copyright detection service for identifying potential copyright issues in text.
 */
@Service
public class CopyrightService {

    private static final String COPYRIGHT_PATTERN = "\b(copyright|Copyright|COPYRIGHT)\b";
    private static final Pattern PATTERN = Pattern.compile(COPYRIGHT_PATTERN, Pattern.CASE_INSENSITIVE);

    /**
     * Checks a given text for copyright notices.
     * 
     * @param text The text to check for copyright notices.
     * @return boolean indicating whether the text contains a copyright notice.
     * @throws ResponseStatusException if an error occurs during the check.
     */
    public boolean checkForCopyrightNotices(String text) {
        if (text == null || text.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Text cannot be null or empty");
        }

        Matcher matcher = PATTERN.matcher(text);
        return matcher.find();
    }

    /**
     * Validates the text for any copyright infringements.
     * 
     * @param text The text to validate.
     * @throws ResponseStatusException if the text contains a copyright notice.
     */
    public void validateCopyright(String text) {
        try {
            if (checkForCopyrightNotices(text)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Copyright notice detected in the text");
            }
        } catch (ResponseStatusException e) {
            throw e; // Rethrow to preserve the original exception details.
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while validating copyright", e);
        }
    }
}