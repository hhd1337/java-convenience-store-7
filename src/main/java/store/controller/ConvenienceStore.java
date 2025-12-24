package store.controller;

import store.domain.promotion.PromotionCatalog;
import store.domain.stock.Stock;
import store.file.ProductFileReader;
import store.file.PromotionFileReader;
import store.view.InputView;
import store.view.OutputView;
import store.view.mapper.StockLineMapper;

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

        outputView.printStock(StockLineMapper.toLines(stock.getProducts()));
    }
}