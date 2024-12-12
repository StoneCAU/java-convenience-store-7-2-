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
        selectBuyOrNot(orders);
        selectMembershipOrNot(orders);
        getResults(orders);
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

    private void selectBuyOrNot(Orders orders) {
        orders.getOrders().stream()
                .filter(Order::hasNotApplicable)
                .forEach(order -> {
                    String reply = buyOrNot(order);
                    if (reply.equals("N")) order.subtractQuantity();
                });
    }

    private String buyOrNot(Order order) {
        while (true) {
            try {
                String input = InputView.inputBuyOrNot(order);
                return InputValidator.getReply(input);
            } catch (StoreException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void selectMembershipOrNot(Orders orders) {
        String reply = membershipOrNot();
        if (reply.equals("Y")) orders.applyMembership();
    }

    private String membershipOrNot() {
        while (true) {
            try {
                String input = InputView.inputMembership();
                return InputValidator.getReply(input);
            } catch (StoreException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void getResults(Orders orders) {
        OutputView.printResults(orders);
    }
}
