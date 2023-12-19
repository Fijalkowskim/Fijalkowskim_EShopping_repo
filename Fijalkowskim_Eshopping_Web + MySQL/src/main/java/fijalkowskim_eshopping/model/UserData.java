package fijalkowskim_eshopping.model;
/**
 * Represents data of the user.
 * @author Fijalkowskim
 * @version 2.0
 */
public class UserData {
    int id;
    float cash;
    /**
     * Initialises cash.
     * @param cash User's cash.
     */
    public UserData(int id,float cash) {
        setCash(cash);
    }

    /**
     * Initialises user data with no cash.
     */
    public UserData(){this(0,0);}
    /**
     * @return User's id.
     */
    public int getId(){return id;}

    /**
     * Sets user's id.
     * @param id User's id.
     */
    public void setID(int id) {
        this.id = Math.max(id, 0);
    }
    /**
     * @return User's cash.
     */
    public float getCash(){return cash;}

    /**
     * Sets user's cash.
     * @param cash User's cash.
     */
    public void setCash(float cash) {
        this.cash = Math.max(cash,0);
    }
}
