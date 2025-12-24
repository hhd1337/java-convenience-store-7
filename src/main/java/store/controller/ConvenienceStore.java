package store.controller;

import java.util.List;
import store.domain.promotion.PromotionCatalog;
import store.domain.stock.Product;
import store.domain.stock.Stock;
import store.file.ProductFileReader;
import store.file.PromotionFileReader;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStore {
    private final InputView inputView;
    private final OutputView outputView;

    public ConvenienceStore(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void open() {
        Stock stock = new Stock(ProductFileReader.read());
        PromotionCatalog promotionCatalog = new PromotionCatalog(PromotionFileReader.read());

        outputView.printStock(toStockLines(stock.getProducts()));
    }

    private List<String> toStockLines(List<Product> products) {
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