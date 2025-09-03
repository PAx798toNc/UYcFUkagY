// 代码生成时间: 2025-09-03 11:54:24
package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RandomNumberGeneratorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomNumberGeneratorService.class);

    /**
     * 生成一个随机数
     *
     * @param min 最小值（包含）
     * @param max 最大值（不包含）
     * @return 生成的随机数
     */
    public int generateRandomNumber(int min, int max) {
        if (min >= max) {
            LOGGER.error("Invalid range for random number generation: min must be less than max");
            throw new IllegalArgumentException("Invalid range for random number generation");
        }

        Random random = ThreadLocalRandom.current();
        return random.nextInt(min, max);
    }
}
