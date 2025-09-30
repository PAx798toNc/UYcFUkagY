// 代码生成时间: 2025-10-01 03:21:22
package com.example.machinetranslation;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class MachineTranslationService {

    private final RestTemplate restTemplate;

    public MachineTranslationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Translates the given text using an external machine translation API.
     *
     * @param text The text to be translated.
     * @param sourceLang The source language.
     * @param targetLang The target language.
     * @return The translated text.
     * @throws IOException If there is an error connecting to the translation API.
     */
    public String translateText(String text, String sourceLang, String targetLang) throws IOException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String requestBody = String.format({"q": "%s", "source": "%s", "target": "%s", "format": "text"}, text, sourceLang, targetLang);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                new URI("https://api.example.com/translate"),
                HttpMethod.POST,
                entity,
                String.class
            );
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new IOException("Translation API returned an error status: " + response.getStatusCode());
            }
        } catch (URISyntaxException e) {
            throw new IOException("Invalid URI for translation API", e);
        }
    }

    /**
     * Handles exceptions that may occur during the translation process.
     *
     * @param throwable The exception that was thrown.
     * @return A user-friendly error message.
     */
    public String handleError(Throwable throwable) {
        // Log the exception details here (e.g., using SLF4J)
        return "Error translating text: " + throwable.getMessage();
    }
}
