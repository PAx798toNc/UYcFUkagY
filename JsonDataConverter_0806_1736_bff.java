// 代码生成时间: 2025-08-06 17:36:41
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Map;

@Component
public class JsonDataConverter implements ConverterFactory<String, Map<String, Object>> {

    private final ObjectMapper objectMapper;

    public JsonDataConverter(Jackson2ObjectMapperBuilder objectMapperBuilder) {
        this.objectMapper = objectMapperBuilder.build();
    }

    @Override
    public <T> Converter<String, T> getConverter(Class<T> targetType) {
        return source -> {
            try {
                return objectMapper.readValue(source, new TypeReference<T>() {});
            } catch (IOException e) {
                throw new IllegalArgumentException("Invalid JSON data format", e);
            }
        };
    }

    // 用于特定类型的转换器
    @Qualifier("jsonDataConverter")
    public static class SpecificJsonDataConverter implements Converter<String, Map<String, Object>> {
        private final ObjectMapper objectMapper;

        public SpecificJsonDataConverter(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Map<String, Object> convert(String source) {
            try {
                return objectMapper.readValue(source, new TypeReference<Map<String, Object>>() {});
            } catch (IOException e) {
                throw new IllegalArgumentException("Invalid JSON data format", e);
            }
        }
    }
}
