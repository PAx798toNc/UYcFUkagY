// 代码生成时间: 2025-09-17 10:06:38
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Component
public class FormValidator implements Validator {

    private static final String USERNAME_NOT_EMPTY = "username.not.empty";
    private static final String USERNAME_SIZE = "username.size";
    private static final String PASSWORD_NOT_EMPTY = "password.not.empty";
    private static final String PASSWORD_SIZE = "password.size";

    @Override
    public boolean supports(Class<?> clazz) {
        // 这里指定Validator支持的类
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", USERNAME_NOT_EMPTY, "Username cannot be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", PASSWORD_NOT_EMPTY, "Password cannot be empty");

        // 检查用户名长度
        if (user.getUsername() != null && user.getUsername().length() < 5) {
            errors.rejectValue("username", USERNAME_SIZE, "Username must be at least 5 characters");
        }

        // 检查密码长度
        if (user.getPassword() != null && user.getPassword().length() < 8) {
            errors.rejectValue("password", PASSWORD_SIZE, "Password must be at least 8 characters");
        }
    }
}


// 假设的用户类，包含用户名和密码
class User {

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 5, message = "Username must be at least 5 characters")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    // getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
