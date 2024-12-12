package store.domain;

import java.util.List;

public class Orders {
    private List<Order> orders;
    private boolean isMembership;

    public Orders(List<Order> orders) {
        this.orders = orders;
        this.isMembership = false;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void applyMembership() {
        isMembership = true;
    }

    public boolean isMembership() {
        return isMembership;
    }

    public int getTotalQuantity() {
        return orders.stream()
                .mapToInt(Order::getQuantity)
                .sum();
    }

    public int getTotalPrice() {
        return orders.stream()
                .mapToInt(Order::getTotalAmount)
                .sum();
    }

    public int getPromotionDiscount() {
        return orders.stream()
                .mapToInt(Order::getPromotionAmount)
                .sum();
    }

    public int getMembershipDiscount() {
        if ((int) ((getTotalPrice() - getPromotionDiscount()) * 0.3) >= 8000) return 8000;
        return (int) ((getTotalPrice() - getPromotionDiscount()) * 0.3);
    }

    public int getTotalPayment() {
        return getTotalPrice() - getPromotionDiscount() - getMembershipDiscount();
    }
}
