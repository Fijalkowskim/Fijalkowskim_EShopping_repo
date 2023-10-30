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
     * Initialises item.
     * @param id ID of an item.
     * @param name Item's name.
     * @param price Item's price.
     * @param description Item's description.
     */
    public ShopItem(int id, String name, float price, String description){
        setId(id);
        setName(name);
        setPrice(price);
        setDescription(description);
    }
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
     * Sets id
     * @param id
     */
    public void setId(int id) {
        this.id = Math.max(id, 0);
    }
    /**
     * Sets name
     * @param name
     */
    public void setName(String name) {
        this.name = name != "" ? name : "Unnamed item";
    }
    /**
     * Sets price
     * @param price
     */
    public void setPrice(float price) {
        this.price = price >= 0 ? price : 0;
    }
    /**
     * Sets description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
