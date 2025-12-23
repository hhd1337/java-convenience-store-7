package store;

import store.controller.ConvenienceStore;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        ConvenienceStore convenienceStore = new ConvenienceStore(inputView, outputView);
        convenienceStore.open();
    }
}
