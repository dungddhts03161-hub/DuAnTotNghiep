package model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Product product;
    private final String size;
    private final String color;
    private int quantity;

    public CartItem(Product product, String size, String color, int quantity) {
        this.product = product;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(1, quantity);
    }

    public String getKey() {
        return product.getMaSP() + "_" + size + "_" + color;
    }

    public BigDecimal getThanhTien() {
        return product.getDonGia().multiply(BigDecimal.valueOf(quantity));
    }
}
