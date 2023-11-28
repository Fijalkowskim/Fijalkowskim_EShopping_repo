package fijalkowskim_eshopping.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import fijalkowskim_eshopping.controller.Controller;
import fijalkowskim_eshopping.model.ExceptionType;
import fijalkowskim_eshopping.model.ItemNotInDatabaseException;
import fijalkowskim_eshopping.model.ItemNotInStockException;
import fijalkowskim_eshopping.model.NotEnoughMoneyException;
import fijalkowskim_eshopping.model.CookieVariables;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/buyItem")
public class BuyItemServlet extends HttpServlet {
    static Map<Integer, Integer> savedItems;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Controller controller = Controller.getInstance();
        ExceptionType exceptionType = ExceptionType.NONE;

        try {
            controller.TryToBuyItem();
            response.addCookie(new Cookie(CookieVariables.cashCookie, Float.toString(controller.getDataManager().getUserData().getCash())));
              int itemIndex = controller.getCurrentItemIndex();
              if(savedItems != null){
                savedItems.replace(itemIndex, controller.getTargetedShopStock().GetItemContainerByIndex(itemIndex).getCount());
                    ObjectMapper objectMapper = new ObjectMapper();
                    String itemsJson = objectMapper.writeValueAsString(savedItems);
                   response.addCookie(new Cookie(CookieVariables.itemsCookie, itemsJson));
                }

        } catch (NotEnoughMoneyException e) {
            exceptionType = ExceptionType.NOT_ENOUGH_MONEY;
        } catch (ItemNotInStockException e) {
            exceptionType = ExceptionType.ITEM_NOT_IN_STOCK;
        } catch (ItemNotInDatabaseException e) {
            exceptionType = ExceptionType.ITEM_NOT_IN_DATABASE;
        }
        String displayedData = controller.getDataManager().displayedDataToJson(controller.getCurrentItemIndex(),exceptionType);
        PrintWriter out = response.getWriter();
        out.println(displayedData);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}

