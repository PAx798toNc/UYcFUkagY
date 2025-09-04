// 代码生成时间: 2025-09-04 23:10:32
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ShoppingCartService {
    private Map<Integer, Integer> cart = new HashMap<>(); // Simulates a shopping cart with item ID and quantity

    /**
     * Adds an item to the cart or updates the quantity if it already exists.
     *
     * @param itemId The ID of the item to add.
     * @param quantity The quantity of the item to add.
     */
    public void addItemToCart(int itemId, int quantity) {
        if (quantity <= 0) {
# 优化算法效率
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than zero.");
        }
        cart.merge(itemId, quantity, Integer::sum);
    }

    /**
     * Removes an item from the cart.
     *
     * @param itemId The ID of the item to remove.
     */
    public void removeItemFromCart(int itemId) {
        if (!cart.containsKey(itemId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found in the cart.");
        }
        cart.remove(itemId);
    }

    /**
     * Updates the quantity of an existing item in the cart.
     *
     * @param itemId The ID of the item to update.
     * @param quantity The new quantity of the item.
     */
# NOTE: 重要实现细节
    public void updateItemQuantity(int itemId, int quantity) {
        if (!cart.containsKey(itemId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found in the cart.");
        }
        if (quantity <= 0) {
# FIXME: 处理边界情况
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than zero.");
        }
        cart.put(itemId, quantity);
# 添加错误处理
    }

    /**
     * Retrieves the current state of the cart.
     *
     * @return A Map representing the cart with item IDs and their quantities.
     */
    public Map<Integer, Integer> getCart() {
        return new HashMap<>(cart); // Returns a copy of the cart to prevent external modification
    }

    /**
# 添加错误处理
     * Clears the cart, removing all items.
     */
    public void clearCart() {
        cart.clear();
    }

    /**
     * Calculates the total quantity of items in the cart.
     *
# FIXME: 处理边界情况
     * @return The total quantity of items.
     */
    public int getTotalQuantity() {
# 改进用户体验
        return cart.values().stream().mapToInt(Integer::intValue).sum();
    }
# 添加错误处理

    /**
     * Retrieves the set of item IDs in the cart.
     *
     * @return A Set of item IDs.
     */
    public Set<Integer> getItemIds() {
        return cart.keySet();
    }
}
