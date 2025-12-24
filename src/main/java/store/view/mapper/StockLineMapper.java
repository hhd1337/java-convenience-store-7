package store.view.mapper;

import java.util.List;
import store.domain.stock.Product;

public class StockLineMapper {
    private StockLineMapper() {
    }

    public static List<String> toLines(List<Product> products) {
        return products.stream()
                .map(p -> {
                    String promotion = p.getPromotion();
                    if (promotion == null) {
                        promotion = "재고 없음";
                    }
                    return p.getName() + " " + p.getPrice() + "원 " + p.getQuantity() + "개 " + promotion;
                }).toList();
    }
}
