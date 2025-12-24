package store.view.mapper;

import java.util.ArrayList;
import java.util.List;
import store.domain.stock.Product;
import store.view.formatter.MoneyFormatter;

public class StockLineMapper {
    private StockLineMapper() {
    }

    public static List<String> toLines(List<Product> products) {

        List<Product> productsForDisplay = new ArrayList<>();

        for (int index = 0; index < products.size(); index++) {
            Product currentProduct = products.get(index);
            productsForDisplay.add(currentProduct);

            // 프로모션 상품이면서 다음 줄에 같은 이름의 일반 상품이 없다면
            // 출력형식을 맞추기 위해 재고 0인 일반상품 라인 추가
            if (isShouldInsertZeroStockNormalProduct(products, index, currentProduct)) {
                productsForDisplay.add(new Product(
                        currentProduct.getName(),
                        currentProduct.getPrice(),
                        0,
                        null
                ));
            }
        }

        return productsForDisplay.stream()
                .map(p -> {
                    String formattedPrice = MoneyFormatter.format(p.getPrice());
                    String quantity = "";
                    if (p.getQuantity() == 0) {
                        quantity = "재고 없음";
                    }
                    if (p.getQuantity() != 0) {
                        quantity = p.getQuantity() + "개 ";
                    }
                    String promotion = p.getPromotion();
                    if (promotion == null) { // 포로모션 없음 -> 그냥 일반상품만 출력
                        promotion = "";
                    }
                    return p.getName() + " " + formattedPrice + "원 " + quantity + promotion;
                }).toList();
    }

    private static boolean isShouldInsertZeroStockNormalProduct(List<Product> products, int index,
                                                                Product currentProduct) {
        boolean hasNext = (index + 1) < products.size();
        Product nextProduct = hasNext ? products.get(index + 1) : null;

        boolean isPromotionProduct = (currentProduct.getPromotion() != null);
        boolean nextHasSameName = (nextProduct != null)
                && nextProduct.getName().equals(currentProduct.getName());
        boolean nextIsNormalProduct = (nextProduct != null)
                && (nextProduct.getPromotion() == null);

        return isPromotionProduct && !(nextHasSameName && nextIsNormalProduct);
    }
}