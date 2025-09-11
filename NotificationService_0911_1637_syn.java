// 代码生成时间: 2025-09-11 16:37:48
package com.yourcompany.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class NotificationService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    /**
     * Sends a notification to a specific endpoint
     * 
     * @param url The endpoint to send the notification to
     * @param message The message to be sent
     * @return ResponseEntity<String> with the response from the endpoint
     */
    public ResponseEntity<String> sendNotification(String url, String message) {
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, message, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Notification sent successfully.");
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Failed to send notification.");
            }
        } catch (HttpClientErrorException ex) {
            // Handle specific HTTP client errors
            return ResponseEntity.status(ex.getStatusCode()).body("Error sending notification: " + ex.getMessage());
        } catch (Exception ex) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending notification: " + ex.getMessage());
        }
    }
}
