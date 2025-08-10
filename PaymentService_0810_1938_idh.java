// 代码生成时间: 2025-08-10 19:38:29
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import javax.transaction.Transactional;

// PaymentService组件，用于处理支付流程
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository; // 注入支付信息存储库

    public Payment processPayment(PaymentRequest paymentRequest) {
        // 验证支付请求
        if (paymentRequest == null || paymentRequest.getAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid payment request");
        }

        // 创建支付实体
        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setStatus(PaymentStatus.PENDING); // 设置支付状态为待处理

        // 保存支付信息到数据库
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment completePayment(Long paymentId) {
        // 根据ID查找支付信息
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));

        // 验证支付状态是否可以完成
        if (!PaymentStatus.PENDING.equals(payment.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment cannot be completed");
        }

        // 更新支付状态为完成
        payment.setStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);
        return payment;
    }
}

// 支付请求实体类
class PaymentRequest {
    private double amount; // 支付金额
    // getter和setter方法
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}

// 支付实体类
class Payment {
    private Long id;
    private double amount;
    private PaymentStatus status; // 支付状态
    // getter和setter方法
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
}

// 支付状态枚举
enum PaymentStatus {
    PENDING, COMPLETED
}

// 支付信息存储库接口
interface PaymentRepository extends JpaRepository<Payment, Long> {
}