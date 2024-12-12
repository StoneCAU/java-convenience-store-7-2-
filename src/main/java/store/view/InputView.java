package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Order;
import store.domain.Product;

public class InputView {
    private final static String NEW_LINE = System.lineSeparator();

    public static String inputOrders() {
        printNewLine();
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return inputString();
    }

    public static String inputAddOrNot(Product product) {
        printNewLine();
        System.out.printf("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n", product.getName());
        return inputString();
    }

    public static String inputBuyOrNot(Order order) {
        printNewLine();
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n", order.getProducts().getFirst().getName(), order.getNotApplicableQuantity());
        return inputString();
    }

    private static void printNewLine() {
        System.out.printf(NEW_LINE);
    }

    private static String inputString() {
        return Console.readLine();
    }
}
