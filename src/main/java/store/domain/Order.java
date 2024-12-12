package store.domain;

import java.util.List;

public class Order {
    private List<Product> products;
    private int quantity;
    private boolean isPromotion;

    public Order(List<Product> products, int quantity) {
        this.products = products;
        this.quantity = quantity;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isPromotion() {
        return isPromotion;
    }
}
