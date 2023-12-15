package fijalkowskim_eshopping.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fijalkowskim_eshopping.controller.Controller;
import fijalkowskim_eshopping.model.*;
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
import com.google.gson.Gson;

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
                  savedItems.put(itemIndex, controller.getTargetedShopStock().GetItemContainerByIndex(itemIndex).getCount());
                  String cookieName = CookieVariables.itemsCookie + Integer.toString(itemIndex);
                  String cookieValue = savedItems.get(itemIndex).toString();
                  response.addCookie(new Cookie(cookieName, cookieValue));
                }

        } catch (NotEnoughMoneyException e) {
            exceptionType = ExceptionType.NOT_ENOUGH_MONEY;
        } catch (ItemNotInStockException e) {
            exceptionType = ExceptionType.ITEM_NOT_IN_STOCK;
        } catch (ItemNotInDatabaseException e) {
            exceptionType = ExceptionType.ITEM_NOT_IN_DATABASE;
        }finally {
            String displayedData = controller.getDataManager().displayedDataToJson(controller.getCurrentItemIndex(),exceptionType);
            PrintWriter out = response.getWriter();
            out.println(displayedData);
        }

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

