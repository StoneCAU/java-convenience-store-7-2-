package store.controller;

import store.domain.Products;
import store.util.FileLoader;
import store.view.OutputView;

public class StoreController {
    public void run() {
        OutputView.printWelcomeMessage();
        Products products = FileLoader.getProducts();
        OutputView.printProducts(products);
    }
}
