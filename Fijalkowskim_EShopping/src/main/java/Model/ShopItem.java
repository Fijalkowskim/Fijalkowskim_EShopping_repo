package Model;

public class ShopItem {
    String name;
    float price;

    public String getName(){return name;}
    public float getPrice() {return price;}

    public ShopItem(String name, float price){this.name = name; this.price = price;}
}
