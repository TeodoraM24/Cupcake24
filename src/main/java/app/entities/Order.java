package app.entities;

public class Order {

    private String productDescription;
    private int orderid; //id'en

    public Order(String productDescription, int orderid) {
        this.productDescription = productDescription;
        this.orderid = orderid;
    }

    public String getProductDescription() { return productDescription; }

    public int getOrderid() {
        return orderid;
    }
}