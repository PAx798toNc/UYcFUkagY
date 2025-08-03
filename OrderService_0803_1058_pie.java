// 代码生成时间: 2025-08-03 10:58:08
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.Optional;

// 订单服务组件
@Service
public class OrderService {

    // 自动注入订单仓库
    @Autowired
    private OrderRepository orderRepository;

    // 创建订单
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // 获取订单详情
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow("=> new OrderNotFoundException("Order not found with id: " + id));
    }

    // 更新订单状态
    public Order updateOrderStatus(Long id, OrderStatus newStatus) {
        Order order = getOrderById(id);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    // 订单不存在时抛出的异常
    public class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String message) {
            super(message);
        }
    }

    // 异常处理器
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

// 订单状态枚举
public enum OrderStatus {
    PENDING, PROCESSING, COMPLETED, CANCELLED
}

// 订单实体
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Order {
    @Id
    private Long id;
    private OrderStatus status;

    // 省略其他属性和getter/setter方法
}

// 订单仓库接口
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 省略其他方法
}