package store;

import model.CartItem;
import model.Product;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public final class CartStore {
    private static final String CART_SESSION_KEY = "cartItems";

    private CartStore() {
    }

    @SuppressWarnings("unchecked")
    public static Map<String, CartItem> getCart(HttpSession session) {
        Object value = session.getAttribute(CART_SESSION_KEY);
        if (value instanceof Map<?, ?>) {
            return (Map<String, CartItem>) value;
        }
        Map<String, CartItem> cart = new LinkedHashMap<>();
        session.setAttribute(CART_SESSION_KEY, cart);
        return cart;
    }

    public static Collection<CartItem> getItems(HttpSession session) {
        return getCart(session).values();
    }

    public static void addItem(HttpSession session, Product product, String size, String color, int quantity) {
        int safeQuantity = Math.max(1, quantity);
        CartItem newItem = new CartItem(product, size, color, safeQuantity);
        Map<String, CartItem> cart = getCart(session);
        CartItem existing = cart.get(newItem.getKey());
        if (existing == null) {
            cart.put(newItem.getKey(), newItem);
        } else {
            existing.setQuantity(existing.getQuantity() + safeQuantity);
        }
    }

    public static void updateItem(HttpSession session, String key, int quantity) {
        Map<String, CartItem> cart = getCart(session);
        if (cart.containsKey(key)) {
            cart.get(key).setQuantity(quantity);
        }
    }

    public static void removeItem(HttpSession session, String key) {
        getCart(session).remove(key);
    }

    public static void clear(HttpSession session) {
        getCart(session).clear();
    }

    public static int getTotalQuantity(HttpSession session) {
        return getItems(session).stream().mapToInt(CartItem::getQuantity).sum();
    }

    public static BigDecimal getSubtotal(HttpSession session) {
        return getItems(session).stream()
                .map(CartItem::getThanhTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static boolean isEmpty(HttpSession session) {
        return getCart(session).isEmpty();
    }
}
