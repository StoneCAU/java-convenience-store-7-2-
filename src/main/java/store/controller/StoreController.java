package store.controller;

import store.domain.Order;
import store.domain.Orders;
import store.domain.Product;
import store.domain.Products;
import store.exception.StoreException;
import store.util.FileLoader;
import store.util.InputValidator;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    public void run() {
        OutputView.printWelcomeMessage();
        Products products = FileLoader.getProducts();
        OutputView.printProducts(products);
        buy(products);
    }

    private void buy(Products products) {
        Orders orders = makeOrder(products);
        selectAddOrNot(orders);
    }

    private Orders makeOrder(Products products) {
        while (true) {
            try {
                String input = InputView.inputOrders();
                return InputValidator.getOrders(input, products);
            } catch (StoreException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void selectAddOrNot(Orders orders) {
        orders.getOrders().stream()
                .filter(Order::isAddable)
                .forEach(order -> {
                    String reply = addOrNot(order.getProducts().getFirst());
                    if (reply.equals("Y")) order.addQuantity();
                });
    }

    private String addOrNot(Product product) {
        while (true) {
            try {
                String input = InputView.inputAddOrNot(product);
                return InputValidator.getReply(input);
            } catch (StoreException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
