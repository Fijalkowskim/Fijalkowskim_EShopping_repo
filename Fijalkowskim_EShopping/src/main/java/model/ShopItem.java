package model;
/**
 * Represent item that can be bought by user at the shop.
 * @author Fijalkowskim
 * @version 1.0
 */
public class ShopItem {
    int id;
    String name;
    float price;
    String description;
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
     * @return Description of an item.
     */
    public String getDescription() {return description;}

    /**
     * Initialises item.
     * @param id ID of an item.
     * @param name Item's name.
     * @param price Item's price.
     * @param description Item's description.
     */
    public ShopItem(int id, String name, float price, String description){
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
