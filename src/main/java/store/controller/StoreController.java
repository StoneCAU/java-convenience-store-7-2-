package store.controller;

import store.domain.Orders;
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
}
