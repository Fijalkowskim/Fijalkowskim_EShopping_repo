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
        assertFalse(userData.cash == -1);
    }
    @Test
    public void testSetCash()
    {
        userData.setCash(100);
        assertTrue(userData.cash == 100);
    }


}