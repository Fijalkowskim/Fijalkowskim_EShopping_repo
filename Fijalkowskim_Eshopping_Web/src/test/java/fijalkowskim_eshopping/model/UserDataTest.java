package fijalkowskim_eshopping.model;

import fijalkowskim_eshopping.model.UserData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the UserData class.
 * @author Fijalkowskim
 * @version 2.0
 */
public class UserDataTest {
    /**
     * Parameterized test for the UserData class constructor.
     * @param defaultConstructor Indicates whether to use the default constructor.
     * @param cash The initial cash value to set.
     * @param expectedCash The expected cash value after construction.
     */
    @ParameterizedTest
    @CsvSource({
            "false,0,0",
            "false,10,10",
            "false,-9,0",
            "true,0,0",
            "true,-6,0",
            "true,5,0"
    })
    public void testUserDataConstructor(boolean defaultConstructor,float cash, float expectedCash){
        UserData userData = defaultConstructor ? new UserData() : new UserData(cash);
        assertEquals(userData.cash, expectedCash);
    }


}