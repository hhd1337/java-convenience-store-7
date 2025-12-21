package store.domain;

import java.util.List;

public class Stock {
    private List<Product> products;

    public Stock(List<Product> products) {
        this.products = products;
    }

    public boolean existsByName(String name) {
        return products.stream().anyMatch(
                product -> product.getName().equals(name)
        );
    }
}
