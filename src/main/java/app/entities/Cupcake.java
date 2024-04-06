package app.entities;

public class Cupcake {
    private String description;
    private String cupcakepart;
    private int price;
    private int productcode;

    public Cupcake(String description, String cupcakepart, int price, int productcode) {
        this.description = description;
        this.cupcakepart = cupcakepart;
        this.price = price;
        this.productcode = productcode;
    }

    public String getDescription() {
        return description;
    }

    public String getCupcakepart() {
        return cupcakepart;
    }

    public int getPrice() {
        return price;
    }

    public int getProductcode() {
        return productcode;
    }

    @Override
    public String toString() {
        return "Cupcake{" +
                "description='" + description + '\'' +
                ", cupcakepart='" + cupcakepart + '\'' +
                ", price=" + price +
                ", productcode=" + productcode +
                '}';
    }
}