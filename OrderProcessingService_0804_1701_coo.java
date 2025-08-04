// 代码生成时间: 2025-08-04 17:01:41
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.dao.DataAccessException;

// Service类，用于处理订单
@Service
public class OrderProcessingService {

    // 自动注入订单仓库接口
    @Autowired
    private OrderRepository orderRepository;

    // 创建订单
    public ResponseEntity<?> createOrder(Order order) {
        try {
            // 保存订单到数据库
            orderRepository.save(order);
            return ResponseEntity.ok("Order created successfully");
        } catch (DataAccessException e) {
            // 数据库异常处理
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while creating order");
        }
    }

    // 更新订单状态
    public ResponseEntity<?> updateOrderStatus(Long orderId, String newStatus) {
        try {
            // 检查订单是否存在
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("Our order was not found"));
            // 更新订单状态
            order.setStatus(newStatus);
            orderRepository.save(order);
            return ResponseEntity.ok("Order status updated successfully");
        } catch (OrderNotFoundException e) {
            // 订单未找到异常处理
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataAccessException e) {
            // 数据库异常处理
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while updating order status");
        }
    }

    // 订单不存在异常
    static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String message) {
            super(message);
        }
    }

    // 全局异常处理
    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error while processing order: " + ex.getMessage());
    }
}
