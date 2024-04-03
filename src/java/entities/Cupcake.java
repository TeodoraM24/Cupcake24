package entities;

public class Cupcake {
    private String description;
    private String cupcakepart;
    private int price;
    private int productcode;

    public Cupcake(String description, String cupcakepart, int price, int productcode) {
        this.description = description;
        this.cupcakepart = cupcakepart;
        this.price = price;
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
}
