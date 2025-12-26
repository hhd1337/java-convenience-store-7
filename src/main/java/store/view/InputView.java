package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import store.domain.order.OrderItem;
import store.util.ErrorMessage;

public class InputView {

    public List<OrderItem> readProductNameAndQuantity() {
        String input = Console.readLine();
        return parseOrderItems(input);
    }

    public boolean readYesNo() {
        String input = Console.readLine().trim().toUpperCase();
        if (input.equals("Y")) {
            return true;
        }
        if (input.equals("N")) {
            return false;
        }
        throw new IllegalArgumentException(ErrorMessage.PREFIX + " 잘못된 입력입니다. 다시 입력해주세요.");
    }

    private List<OrderItem> parseOrderItems(String input) {
        Set<String> seenNames = new HashSet<>();

        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(s -> s.replace("[", "").replace("]", "")) // 사이다-2
                .map(token -> token.split("-"))
                .map(parts -> {
                    String name = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());

                    if (!seenNames.add(name)) {
                        throw new IllegalArgumentException(
                                ErrorMessage.PREFIX + "같은 상품은 합쳐서 한번만 입력해주세요: " + name
                        );
                    }

                    OrderItem orderItem = new OrderItem(name, quantity);
                    orderItem.validatePositiveCount(quantity);
                    return orderItem;
                })
                .toList();
    }
}