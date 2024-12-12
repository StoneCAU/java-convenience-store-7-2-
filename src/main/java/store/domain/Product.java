package store.domain;

import java.util.List;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private Promotion promotion;

    public Product(List<String> productInfo, Promotions promotions) {
        this.name = productInfo.get(0);
        this.price = Integer.parseInt(productInfo.get(1));
        this.quantity = Integer.parseInt(productInfo.get(2));
        this.promotion = promotions.findPromotionByName(productInfo.get(3));
    }

    public Product(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = 0;
        this.promotion = null;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "- " + name + " " + priceFormatting() + " " + quantityFormatting() + " " + promotionFormatting();
    }

    private String priceFormatting() {
        return String.format("%,d원", price);
    }

    private String quantityFormatting() {
        if (quantity == 0) return "재고 없음";
        return quantity + "개";
    }

    private String promotionFormatting() {
        if (promotion == null) return "";
        return getPromotion().getName();
    }
}
