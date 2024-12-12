package store.domain;

import java.util.List;
import store.exception.ErrorMessage;
import store.exception.StoreException;

public class Promotions {
    private List<Promotion> promotions;

    public Promotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public Promotion findPromotionByName(String name) {
        if (name.equals("null")) return null;

        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new StoreException(ErrorMessage.INVALID_FILE_CONTENT));
    }
}
