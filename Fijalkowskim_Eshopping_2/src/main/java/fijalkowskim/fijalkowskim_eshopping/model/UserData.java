package fijalkowskim.fijalkowskim_eshopping.model;
/**
 * Represents data of the user.
 * @author Fijalkowskim
 * @version 1.1
 */
public class UserData {
    float cash;
    /**
     * Initialises cash.
     * @param cash User's cash.
     */
    public UserData(float cash) {
        setCash(cash);
    }

    /**
     * Initialises user data with no cash.
     */
    public UserData(){this(0);}
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
