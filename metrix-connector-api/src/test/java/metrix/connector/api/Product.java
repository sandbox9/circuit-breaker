package metrix.connector.api;

import java.io.Serializable;

/**
 * Created by chanwook on 2015. 2. 4..
 */
public class Product implements Serializable {
    private int productId;

    public Product(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
