package fijalkowskim_eshopping.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;

/**
 * Represent item that can be bought by user at the shop.
 * @author Fijalkowskim
 * @version 2.0
 */
public class ShopItem {
    int id;
    String name;
    float price;
    String description;
    String imageUrl;

    /**
     * Initialises item.
     * @param name Item's name.
     * @param price Item's price.
     * @param description Item's description.
     */
    public ShopItem(int id,String name, float price, String description, String imageUrl){
        setID(id);
        setName(name);
        setPrice(price);
        setDescription(description);
        setImageUrl(imageUrl);
    }

    /**
     * Initialises default empty item
     */
    public ShopItem(){
        setName(null);
        setPrice(0);
        setDescription(null);
        setImageUrl("");
    }

    /**
     * Initialises item with no image
     * @param name Item's name.
     * @param price Item's price.
     * @param description Item's description.

     */
    public ShopItem(String name, float price, String description){
        this(0,name,price,description,"");
    }
    /**
     * @return "ID" of the item.
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
    public String getImageUrl() {
        return imageUrl;
    }
    /**
     * Sets id
     * @param id
     */

    public void setID(int id) {
        this.id = id < 0  ? 0 : id ;
    }
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
    /**
     * Sets image URL.
     *
     * @param imageUrl URL of the item's image.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl.isEmpty() ? "defaultUrl" : imageUrl;
    }
    /**
     * Converts the object to JSON format.
     *
     * @return JSON representation of the item.
     * @throws JsonProcessingException If an error occurs during JSON processing.
     */
    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }



}
