package model;
/**
 * Represent item that can be bought by user at the shop.
 * @author Fijalkowskim
 * @version %I%, %G%
 */
public class ShopItem {
    int id;
    String name;
    float price;
    /**
     * @return ID of the item.
     */
    public int getID(){return id;}

    /**
     * @return Name of the item.
     */
    public String getName(){return name;}

    /**
     * @return Price of the item.
     */
    public float getPrice() {return price;}

    /**
     * Initialises item.
     * @param name Item's name.
     * @param price Item's price.
     */
    public ShopItem(int id, String name, float price){this.id = id; this.name = name; this.price = price;}
}
