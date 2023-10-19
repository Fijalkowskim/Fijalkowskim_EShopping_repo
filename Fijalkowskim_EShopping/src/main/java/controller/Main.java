package controller;
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
        controller.MainLoop();
    }

}