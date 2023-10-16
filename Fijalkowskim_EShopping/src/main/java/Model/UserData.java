package Model;

public class UserData {
    public void setCash(float cash) {
        this.cash = cash;
    }

    float cash;

    public UserData(float cash) {
        this.cash = cash;
    }

    public float getCash(){return cash;}
}
