package store.domain.payment;

import store.domain.order.Order;
import store.domain.order.OrderItem;
import store.domain.stock.Stock;

public class Payment {
    private Order order;
    private final Stock stock;

    public Payment(Order order, Stock stock) {
        this.order = order;
        this.stock = stock;
    }

    public int calculateTotalPurchaseAmount() {
        // 재고 검증, 예외로직
        order.getOrderItems()
                .forEach(item -> stock.hasEnoughStock(item.getName(), item.getQuantity()));

        return order.getOrderItems().stream()
                .mapToInt(this::calculateItemPurchaseAmount)
                .sum();
    }

    private int calculateItemPurchaseAmount(OrderItem item) {
        int unitPrice = stock.findPriceByName(item.getName());
        return item.calculateItemTotalPrice(unitPrice);
    }
}
