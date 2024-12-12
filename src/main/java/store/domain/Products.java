package store.domain;

import java.util.List;
import store.exception.ErrorMessage;
import store.exception.StoreException;

public class Products {
    private List<Product> products;
    private Promotions promotions;

    public Products(List<Product> products, Promotions promotions) {
        this.products = products;
        this.promotions = promotions;
        addOutOfStockProducts();
    }

    public List<Product> getProducts() {
        return products;
    }

    public Promotions getPromotions() {
        return promotions;
    }

    public List<Product> getAllKindsOfProductsByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .toList();
    }

    private Product getPromotionProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .filter(product -> product.getPromotion() != null)
                .findFirst()
                .orElseThrow(() -> new StoreException(ErrorMessage.INVALID_FILE_CONTENT));
    }

    private void addOutOfStockProducts() {
        List<Product> productList = getNeedToAddProduct();

        productList.forEach(product -> {
                    Product promotionProduct = getPromotionProductByName(product.getName());
                    int index = products.indexOf(promotionProduct);
                    products.add(index + 1, new Product(promotionProduct));
                });
    }

    private List<Product> getNeedToAddProduct() {
        return products.stream()
                .filter(product -> getAllKindsOfProductsByName(product.getName()).size() != 2)
                .filter(product -> hasOnlyPromotionProduct(product.getName()))
                .map(Product::new)
                .toList();
    }

    private boolean hasOnlyPromotionProduct(String name) {
        List<Product> productList = getAllKindsOfProductsByName(name);
        return productList.get(0).getPromotion() != null;
    }
}
