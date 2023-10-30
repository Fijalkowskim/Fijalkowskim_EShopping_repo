package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDataTest {

    UserData userData;
    @BeforeEach
    void setUp()
    {
        userData = new UserData(0);
    }
    @Test
    public void testSetCashAsNegativeInt()
    {
        userData.setCash(-1);
        assertNotEquals(-1, userData.cash);
    }
    @Test
    public void testSetCash()
    {
        userData.setCash(100);
        assertEquals(100, userData.cash);
    }
    @Test
    public void testGetCash(){
        userData.setCash(100);
        assertEquals(100,userData.getCash());
    }


}