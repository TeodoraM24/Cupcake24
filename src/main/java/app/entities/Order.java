package app.entities;

public class Order {

    private String productName;
    private int price;
    private int orderid;

    public Order(String productName, int price, int totalPrice, int orderid, int quantity) {
        this.productName = productName;
        this.price = price;
        this.orderid = orderid;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getOrderid() {
        return orderid;
    }
}