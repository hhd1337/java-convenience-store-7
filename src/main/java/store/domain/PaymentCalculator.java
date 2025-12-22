package store.domain;

public class PaymentCalculator {
    public int calculateTotalPurchaseAmount(Order order, Stock stock) {
        // 재고 검증, 예외로직
        order.getOrderItems()
                .forEach(item -> stock.hasEnoughStock(item.getName(), item.getQuantity()));

        return order.getOrderItems().stream()
                .mapToInt(item -> calculateItemPurchaseAmount(item, stock))
                .sum();
    }

    private int calculateItemPurchaseAmount(OrderItem item, Stock stock) {
        int unitPrice = stock.findPriceByName(item.getName());
        return item.calculateItemTotalPrice(unitPrice);
    }
}
