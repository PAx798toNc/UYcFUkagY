// 代码生成时间: 2025-09-21 21:48:21
import org.springframework.stereotype.Component;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;

/**
 * JSON数据格式转换器组件，用于将JSON字符串转换为Java对象或将Java对象转换为JSON字符串
 */
@Component
public class JsonDataTransformer {

    private final MappingJackson2HttpMessageConverter jsonConverter;
    private final ObjectMapper objectMapper;

    /**
     * 构造函数
     * @param jsonConverter JSON消息转换器
     * @param objectMapper 对象映射器
     */
    public JsonDataTransformer(MappingJackson2HttpMessageConverter jsonConverter, ObjectMapper objectMapper) {
        this.jsonConverter = jsonConverter;
        this.objectMapper = objectMapper;
    }

    /**
     * 将JSON字符串转换为Java对象
     * @param json JSON字符串
     * @param clazz Java对象的类类型
     * @param <T> Java对象的类型
     * @return Java对象
     * @throws HttpMessageNotReadableException 当JSON解析失败时抛出异常
     */
    public <T> T fromJson(String json, Class<T> clazz) throws HttpMessageNotReadableException {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new HttpMessageNotReadableException("Failed to parse JSON: " + json, e);
        }
    }

    /**
     * 将Java对象转换为JSON字符串
     * @param object Java对象
     * @return JSON字符串
     * @throws JsonProcessingException 当JSON处理失败时抛出异常
     */
    public String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
