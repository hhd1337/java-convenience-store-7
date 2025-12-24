package store.view;

import java.util.List;

public class OutputView {
    public void printStock(List<String> lines) {
        System.out.println("안녕하세요. w편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");
        for (String line : lines) {
            System.out.println("- " + line);
        }
        System.out.println("\n구매하실 상품명과 수량을 입력해주세요. (예: [사이다-2],[감자칩-1])");
    }
}