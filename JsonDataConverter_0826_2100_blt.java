// 代码生成时间: 2025-08-26 21:00:24
package com.example.demo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JsonDataConverter implements Converter<String, Object> {
    private static final Logger logger = LoggerFactory.getLogger(JsonDataConverter.class);
    private final ObjectMapper objectMapper;

    public JsonDataConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object convert(String source) {
        try {
            return objectMapper.readValue(source, Object.class);
        } catch (JsonProcessingException e) {
            logger.error("Error converting JSON to object: " + source, e);
            // Add your error handling logic here.
            // For simplicity, we throw a RuntimeException, but in a real application,
            // you might want to throw a custom exception or return an error response.
            throw new RuntimeException("Failed to convert JSON to object");
        }
    }
}
