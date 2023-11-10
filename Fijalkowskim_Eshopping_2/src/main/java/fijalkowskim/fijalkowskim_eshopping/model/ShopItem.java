package fijalkowskim.fijalkowskim_eshopping.model;

import fijalkowskim.fijalkowskim_eshopping.view.GuiManager;
import javafx.scene.image.Image;

import java.net.URL;

/**
 * Represent item that can be bought by user at the shop.
 * @author Fijalkowskim
 * @version 1.1
 */
public class ShopItem {
    String name;
    float price;
    String description;
    Image image;

    /**
     * Initialises item.
     * @param name Item's name.
     * @param price Item's price.
     * @param description Item's description.
     */
    public ShopItem(String name, float price, String description, String imageFileName){
        setName(name);
        setPrice(price);
        setDescription(description);
        setImageByFileName(imageFileName);
    }

    /**
     * Initialises default empty item
     */
    public ShopItem(){
        setName(null);
        setPrice(0);
        setDescription(null);
        setImageByFileName("");
    }

    /**
     * Initialises item with no image
     * @param name Item's name.
     * @param price Item's price.
     * @param description Item's description.

     */
    public ShopItem(String name, float price, String description){
        this(name,price,description,"");
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
     * @return Image of an item.
     */
    public Image getImage() {return image;}
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
     * Sets image by name
     * @param imageFileName Name of an image
     */
    public void setImageByFileName(String imageFileName) {
        URL url = ShopItem.class.getResource(imageFileName);
        Image newImage = null;
        if(url != null)
        {
            try{
                 newImage = new Image(url.toString());
            }catch (Exception e) {
            }
        }
        this.image = url == null  || imageFileName.isEmpty()?
                null:
                newImage;
    }

    /**
     * Sets image of an item
     * @param image Image
     */
    public void setImage(Image image){
        this.image = image;
    }

}
