package model;
/**
 * Represents data of the user.
 * @author Fijalkowskim
 * @version 1.0
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
