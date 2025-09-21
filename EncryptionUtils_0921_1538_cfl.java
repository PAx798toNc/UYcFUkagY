// 代码生成时间: 2025-09-21 15:38:57
package com.example.demo.utils;

import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class EncryptionUtils {

    private static final String ALGORITHM = "AES"; // 使用AES加密算法
    private static final String CHARSET_NAME = "UTF-8"; // 使用UTF-8编码

    // 生成AES密钥
    public SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128); // 初始化密钥长度为128位
        return keyGenerator.generateKey();
    }

    // 加密
    public String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(CHARSET_NAME));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // 解密
    public String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        return new String(cipher.doFinal(encryptedBytes), CHARSET_NAME);
    }

    // 错误处理示例
    public String handleEncryptionError(String data) {
        try {
            SecretKey key = generateAESKey();
            return encrypt(data, key);
        } catch (Exception e) {
            System.err.println("Encryption error: " + e.getMessage());
            return null; // 或者返回默认值，或者抛出自定义异常
        }
    }

    // 添加必要的注释
    // 以下是加密解密工具类的主要方法，用于生成AES密钥，以及对字符串进行加密和解密。
    // 该类遵循Spring Boot的最佳实践，使用@Component注解标记为一个Spring组件。
    // generateAESKey方法生成一个新的AES密钥。
    // encrypt方法使用提供的密钥对数据进行加密。
    // decrypt方法使用提供的密钥对加密的数据进行解密。
    // handleEncryptionError方法是一个示例错误处理方法，它尝试加密数据，如果失败则返回null。
}
