package store.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.exception.ErrorMessage;
import store.exception.StoreException;

public class FileLoader {
    private final static String PRODUCTS_FILE_NAME = "products.md";
    private final static String PROMOTIONS_FILE_NAME = "promotions.md";

    public static Products getProducts() {
        Promotions promotions = getPromotions();

        return new Products(generateProducts(promotions), promotions);
    }

    private static Promotions getPromotions() {
        return new Promotions(generatePromotions());
    }

    private static List<Promotion> generatePromotions() {
        List<String> promotionLines = loadMarkdownFile(PROMOTIONS_FILE_NAME);

        return promotionLines.stream()
                .skip(1)
                .map(line -> new Promotion(parseLine(line)))
                .collect(Collectors.toList());
    }

    private static List<Product> generateProducts(Promotions promotions) {
        List<String> productLines = loadMarkdownFile(PRODUCTS_FILE_NAME);

        return productLines.stream()
                .skip(1)
                .map(line -> new Product(parseLine(line), promotions))
                .collect(Collectors.toList());
    }

    private static List<String> parseLine(String str) {
        return Arrays.stream(str.split(",")).toList();
    }

    private static List<String> loadMarkdownFile(String fileName) {
        try (InputStream inputStream = FileLoader.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            return reader.lines().collect(Collectors.toList());

        } catch (IOException e) {
            throw new StoreException(ErrorMessage.INVALID_FILE_CONTENT);
        }
    }
}
