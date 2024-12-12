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
        this.addedQuantity = calculateAddedQuantity();
        validate();
        checkPromotionDate();
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void update() {
        if (products.size() == 2) {
            updatePromotionAndNormalProduct();
            return;
        }
        updateOnlyNormalProduct();
    }

    private void updateOnlyNormalProduct() {
        Product normalProduct = products.get(0);
        normalProduct.setQuantity(normalProduct.getQuantity() - quantity);
    }

    private void updatePromotionAndNormalProduct() {
        Product promotionProduct = products.get(0);
        Product normalProduct = products.get(1);

        if (promotionProduct.getQuantity() < quantity) {
            normalProduct.setQuantity(normalProduct.getQuantity() - (quantity - promotionProduct.getQuantity()));
            promotionProduct.setQuantity(0);
            return;
        }
        promotionProduct.setQuantity(promotionProduct.getQuantity() - quantity);
    }

    public int getAddedQuantity() {
        return addedQuantity;
    }

    public boolean hasAddedQuantity() {
        return addedQuantity > 0;
    }

    public void addQuantity() {
        quantity++;
        addedQuantity++;
    }

    public void subtractQuantity() {
        quantity -= getNotApplicableQuantity();
    }

    public int getTotalAmount() {
        return quantity * products.getFirst().getPrice();
    }

    public int getPromotionAmount() {
        return getAddedQuantity() * products.getFirst().getPrice();
    }

    public boolean isAddable() {
        Promotion promotion = products.getFirst().getPromotion();
        if (promotion == null) return false;
        return (quantity + 1) % (promotion.getGet() + promotion.getBuy()) == 0
                && isPromotion
                && quantity <= products.getFirst().getQuantity();
    }

    public boolean hasNotApplicable() {
        int promotionQuantity = products.getFirst().getQuantity();
        Promotion promotion = products.getFirst().getPromotion();
        if (promotion == null) return false;
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
        if (products.getFirst().getPromotion() != null && isPromotionDate(now)) {
            isPromotion = true;
            return;
        }
        isPromotion = false;
    }

    private boolean isPromotionDate(LocalDate now) {
        Promotion promotion = products.getFirst().getPromotion();
        return now.isBefore(promotion.getEndDate()) && now.isAfter(promotion.getStartDate());
    }

    private int calculateAddedQuantity() {
        Promotion promotion = products.getFirst().getPromotion();
        if (promotion != null) {
            return getShareQuantity() / (promotion.getBuy() + promotion.getGet());
        }
        return 0;
    }

    private int getShareQuantity() {
        if (products.getFirst().getQuantity() <= quantity) return  products.getFirst().getQuantity();
        return quantity;
    }

    @Override
    public String toString() {
        return products.getFirst().getName() + "\t\t\t" + quantity + "\t\t" + String.format("%,d", products.getFirst().getPrice() * quantity);
    }
}
