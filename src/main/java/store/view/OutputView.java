package store.view;

import store.domain.Products;

public class OutputView {
    private static final String NEW_LINE = System.lineSeparator();

    public static void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    public static void printProducts(Products products) {
        System.out.println("현재 보유하고 있는 상품입니다.");
        printNewLine();
        products.getProducts().forEach(System.out::println);
    }

    public static void printErrorMessage(String message) {
        printNewLine();
        System.out.println(message);
    }

    private static void printNewLine() {
        System.out.printf(NEW_LINE);
    }
}
