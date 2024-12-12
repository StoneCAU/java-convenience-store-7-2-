package store.view;

import store.domain.Order;
import store.domain.Orders;
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

    public static void printResults(Orders orders) {
        printNewLine();
        System.out.println("===========W 편의점=============");
        System.out.println("상품명\t\t수량\t금액");
        orders.getOrders().forEach(System.out::println);
        printAddedProducts(orders);
        printPayment(orders);
    }

    private static void printAddedProducts(Orders orders) {
        System.out.println("===========증\t정=============");
        orders.getOrders().stream()
                .filter(Order::hasAddedQuantity)
                .forEach(order -> System.out.println(order.getProducts().getFirst().getName() + "\t\t\t" + order.getAddedQuantity()));
    }

    private static void printPayment(Orders orders) {
        System.out.println("==============================");
        System.out.println("총구매액\t\t\t" + orders.getTotalQuantity() + "\t\t" + moneyFormatting(orders.getTotalPrice()));
        System.out.println("행사할인\t\t\t\t\t-" + moneyFormatting(orders.getPromotionDiscount()));
        System.out.println("멤버십할인\t\t\t\t\t-" + moneyFormatting(orders.getMembershipDiscount()));
        System.out.println("내실돈\t\t\t\t\t" + moneyFormatting(orders.getTotalPayment()));
    }

    public static void printErrorMessage(String message) {
        printNewLine();
        System.out.println(message);
    }

    private static void printNewLine() {
        System.out.printf(NEW_LINE);
    }

    private static String moneyFormatting(int price) {
        return String.format("%,d", price);
    }
}
