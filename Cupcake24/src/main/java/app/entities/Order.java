package app.entities;

public class Order {
    private int orderNumber;
    private String productName;
    private int price;
    private int productCode;
    private int quantity;

    public Order(int orderNumber, String productName, int price, int productCode, int quantity) {
        this.orderNumber = orderNumber;
        this.productName = productName;
        this.price = price;
        this.productCode = productCode;
        this.quantity = quantity;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getProductCode() {
        return productCode;
    }

    public int getQuantity() {
        return quantity;
    }
}
