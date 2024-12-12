package store.util;

import java.util.Arrays;
import java.util.List;
import store.domain.Order;
import store.domain.Orders;
import store.domain.Product;
import store.domain.Products;
import store.exception.ErrorMessage;
import store.exception.StoreException;

public class InputValidator {
    public static Orders getOrders(String input, Products products) {
        List<Order> orderList = generateOrderList(input, products);

        return new Orders(orderList);
    }

    private static List<Order> generateOrderList(String input, Products products) {
        List<String> orderLine = parseOrderLine(input);

        return orderLine.stream()
                .map(line -> {
                    List<String> info = parseOrderInfo(line);
                    List<Product> productList = getProductList(info.getFirst(), products);
                    int quantity = parseNumber(info.get(1));
                    return new Order(productList, quantity);
                }).toList();
    }

    private static List<String> parseOrderInfo(String orderLine) {
        return Arrays.stream(orderLine.split("-")).toList();
    }

    private static List<String> parseOrderLine(String input) {
        List<String> parsed = Arrays.stream(input.split(",")).toList();
        return parsed.stream()
                .map(str -> str.replaceAll("[\\[\\]]", ""))
                .toList();
    }

    private static List<Product> getProductList(String name, Products products) {
        return products.getAllKindsOfProductsByName(name);
    }

    private static int parseNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new StoreException(ErrorMessage.INVALID_ORDER);
        }
    }

}
