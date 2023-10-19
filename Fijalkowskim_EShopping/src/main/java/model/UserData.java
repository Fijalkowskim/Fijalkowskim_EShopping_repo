package model;
/**
 * Represents data of the user.
 * @author Fijalkowskim
 * @version %I%, %G%
 */
public class UserData {
    float cash;

    /**
     * Initialises cash.
     * @param cash User's cash.
     */
    public UserData(float cash) {
        this.cash = cash;
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
        this.cash = cash;
    }
}
