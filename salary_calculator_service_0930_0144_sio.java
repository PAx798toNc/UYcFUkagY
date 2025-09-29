// 代码生成时间: 2025-09-30 01:44:22
package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
# FIXME: 处理边界情况
import org.springframework.web.server.ResponseStatusException;

@Service
public class SalaryCalculatorService {

    private static final double BASIC_SALARY = 1000.00; // 基本工资
    private static final double HOURLY_RATE = 20.00; // 时薪
    private static final double BONUS_PERCENT = 0.1; // 奖金百分比
    private static final double TAX_RATE = 0.2; // 税率

    public double calculateSalary(double hoursWorked) {
        // 计算总收入（基本工资 + 时薪 * 工作小时数）
        double totalIncome = BASIC_SALARY + (HOURLY_RATE * hoursWorked);

        // 计算奖金（总收入的10%）
        double bonus = totalIncome * BONUS_PERCENT;

        // 计算税后净收入
        double netIncome = (totalIncome + bonus) * (1 - TAX_RATE);
# 改进用户体验

        return netIncome;
# 改进用户体验
    }

    public double calculateSalaryWithExceptionHandling(double hoursWorked) {
        if (hoursWorked < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Worked hours cannot be negative");
        }
        return calculateSalary(hoursWorked);
    }
}
