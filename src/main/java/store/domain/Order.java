package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.List;
import store.exception.ErrorMessage;
import store.exception.StoreException;

public class Order {
    private List<Product> products;
    private int quantity;
    private int addedQuantity;
    private boolean isPromotion;

    public Order(List<Product> products, int quantity) {
        this.products = products;
        this.quantity = quantity;
        this.addedQuantity = 0;
        validate();
        checkPromotionDate();
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAddedQuantity() {
        return addedQuantity;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public void addQuantity() {
        quantity++;
        addedQuantity++;
    }

    public void subtractQuantity() {
        quantity -= getNotApplicableQuantity();
    }

    public boolean isAddable() {
        Promotion promotion = products.getFirst().getPromotion();
        return (quantity + 1) % (promotion.getGet() + promotion.getBuy()) == 0
                && isPromotion
                && quantity <= products.getFirst().getQuantity();
    }

    public boolean hasNotApplicable() {
        int promotionQuantity = products.getFirst().getQuantity();
        Promotion promotion = products.getFirst().getPromotion();
        return promotionQuantity - (promotionQuantity % (promotion.getBuy() + promotion.getGet())) < quantity
                && isPromotion;
    }

    public int getNotApplicableQuantity() {
        int promotionQuantity = products.getFirst().getQuantity();
        Promotion promotion = products.getFirst().getPromotion();
        return quantity - (promotionQuantity - (promotionQuantity % (promotion.getBuy() + promotion.getGet())));
    }

    private void validate() {
        if (quantity > getTotalQuantity()) {
            throw new StoreException(ErrorMessage.INVALID_QUANTITY);
        }
    }

    private int getTotalQuantity() {
        return products.stream()
                .mapToInt(Product::getQuantity)
                .sum();
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
