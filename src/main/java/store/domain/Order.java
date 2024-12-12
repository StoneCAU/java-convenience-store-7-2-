package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.List;

public class Order {
    private List<Product> products;
    private int quantity;
    private boolean isPromotion;

    public Order(List<Product> products, int quantity) {
        this.products = products;
        this.quantity = quantity;
        checkPromotionDate();
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

    private void checkPromotionDate() {
        LocalDate now = DateTimes.now().toLocalDate();
        if (isPromotionDate(now)) {
            isPromotion = true;
            return;
        }
        isPromotion = false;
    }

    private boolean isPromotionDate(LocalDate now) {
        Promotion promotion = products.getFirst().getPromotion();
        return now.isBefore(promotion.getEndDate()) && now.isAfter(promotion.getStartDate());
    }
}
