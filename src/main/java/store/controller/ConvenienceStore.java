package store.controller;

import java.util.List;
import store.domain.order.OrderItem;
import store.domain.promotion.Promotion;
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

        // 1. 인사, 재고 출력
        outputView.printStock(StockLineMapper.toLines(stock.getProducts()));
        // 2. 구매할 상품명과 수량 입력받아 저장
        List<OrderItem> orderItems = inputView.readProductNameAndQuantity();

        // 3. Stock과 비교해서 프로모션 적용해서 무료로 더 받을 수 있는지 확인, 사용자에게 묻고 OrderItem에 반영하기
        for (OrderItem item : orderItems) {
            int addCount = getAdditionalFreeCount(item, stock, promotionCatalog);
            if (addCount > 0) {
                outputView.printGetMoreItemDuePromotion(item.getName(), addCount);
                if (inputView.readYesNo()) {
                    item.increaseQuantity(addCount);
                }
            }
        }

    }

    private int getAdditionalFreeCount(OrderItem item, Stock stock, PromotionCatalog pc) {
        int orderQuantity = item.getQuantity();

        String promotionName = stock.findPromotionByProductName(item.getName());
        Promotion promotion = pc.findPromotionByName(promotionName);

        int buy = promotion.getBuy();
        int get = promotion.getGet();

        int cycle = buy + get;
        int remain = orderQuantity % cycle;

        if (orderQuantity < buy || remain == 0 || remain < buy) {
            return 0;
        }
        return cycle - remain;
    }
}