// 代码生成时间: 2025-08-23 08:27:21
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShoppingCartService {
    // In-memory storage for demonstration purposes.
    // In a real-world application, this would be replaced with a database or other persistent storage.
    private Map<Integer, ShoppingCart> shoppingCarts = new HashMap<>();
    private static int nextCartId = 1;

    // Represents a shopping cart with an ID and a list of items.
    public static class ShoppingCart {
        private int id;
        private Map<Integer, Integer> items = new HashMap<>();

        public ShoppingCart(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public Map<Integer, Integer> getItems() {
            return items;
        }

        public void addItem(int productId, int quantity) {
            items.put(productId, items.getOrDefault(productId, 0) + quantity);
        }

        public void removeItem(int productId) {
            items.remove(productId);
        }
    }

    // Creates a new shopping cart.
    public ShoppingCart createCart() {
        ShoppingCart cart = new ShoppingCart(nextCartId++);
        shoppingCarts.put(cart.getId(), cart);
        return cart;
    }

    // Retrieves a shopping cart by ID.
    public ShoppingCart getCart(int cartId) {
        if (!shoppingCarts.containsKey(cartId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shopping cart not found");
        }
        return shoppingCarts.get(cartId);
    }

    // Adds an item to the shopping cart.
    public void addItemToCart(int cartId, int productId, int quantity) {
        ShoppingCart cart = getCart(cartId);
        cart.addItem(productId, quantity);
    }

    // Removes an item from the shopping cart.
    public void removeItemFromCart(int cartId, int productId) {
        ShoppingCart cart = getCart(cartId);
        cart.removeItem(productId);
    }

    // Updates the quantity of an item in the shopping cart.
    public void updateItemQuantityInCart(int cartId, int productId, int quantity) {
        ShoppingCart cart = getCart(cartId);
        if (quantity <= 0) {
            cart.removeItem(productId);
        } else {
            cart.addItem(productId, quantity);
        }
    }

    // Empties the shopping cart.
    public void emptyCart(int cartId) {
        ShoppingCart cart = getCart(cartId);
        cart.getItems().clear();
    }

    // Lists all items in the shopping cart.
    public Map<Integer, Integer> listCartItems(int cartId) {
        ShoppingCart cart = getCart(cartId);
        return cart.getItems();
    }
}
