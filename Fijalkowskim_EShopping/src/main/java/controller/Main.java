package controller;

import java.text.ParseException;

/**
 * Main class of the application.
 * @author Fijalkowskim
 * @version %I%, %G%
 */
public class Main {
    /**
     * Main method interacting only with Controller class.
     */
    public static void main(String[] args)
    {
        Controller controller = new Controller();

        if(args != null) {
            try{
            float startCash = Float.parseFloat(args[0]);
            controller.dataManager.getUserData().setCash(startCash);
            }
            catch(Exception ex){System.out.println(ex.getMessage());}
        }

        controller.MainLoop();

    }

}