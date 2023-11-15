package model;
/**
 * Represent item that can be bought by user at the shop.
 * @author Fijalkowskim
 * @version 1.1
 */
public class ShopItem {
    String name;
    float price;
    String description;

    /**
     * Initialises item.
     * @param name Item's name.
     * @param price Item's price.
     * @param description Item's description.
     */
    public ShopItem(String name, float price, String description){
        setName(name);
        setPrice(price);
        setDescription(description);
    }

    /**
     * Initialises default empty item
     */
    public ShopItem(){
        setName(null);
        setPrice(0);
        setDescription(null);
    }
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
     * Sets name
     * @param name
     */
    public void setName(String name) {
        this.name = name == null || name.isEmpty() ? "Unnamed item" : name ;
    }
    /**
     * Sets price
     * @param price
     */
    public void setPrice(float price) {

        this.price = Math.max(price, 0);
    }
    /**
     * Sets description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null || description.isEmpty()  ? "No description" : description;
    }

}
