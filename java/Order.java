public class Order {

    private String productName; //mangler lige et godt navn. Det er til navnet på det man bestiller
    private int price; //pris på ordren
    private int orderid; //id'en

    public Order(String productName, int price, int orderid) {
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
