package fijalkowskim_eshopping.servlets;

import fijalkowskim_eshopping.controller.Controller;
import fijalkowskim_eshopping.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/buyItem")
public class BuyItemServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        Controller controller = Controller.getInstance();
        ExceptionType exceptionType = ExceptionType.NONE;

        try {
            controller.TryToBuyItem();
              int itemID = controller.getCurrentShopItemContainer().getShopItem().getID();
              int newCount = controller.getCurrentShopItemContainer().getCount();
            Connection con = Controller.getInstance().getDbConnection();
            if(con == null) {
                exceptionType = ExceptionType.DATABASE_EXCEPTION;
            }
            else {

                try {
                    String updateQuery = "UPDATE ItemContainers SET count = ? WHERE itemId = ?";
                    try (PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {
                        preparedStatement.setInt(1, newCount);
                        preparedStatement.setInt(2, itemID);
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {

                    }

                    String updateCashQuery = "UPDATE SessionData SET cash = ? WHERE userId = ?";
                    try (PreparedStatement preparedStatement = con.prepareStatement(updateCashQuery)) {
                        preparedStatement.setFloat(1, controller.getDataManager().getUserData().getCash());
                        preparedStatement.setInt(2, controller.getDataManager().getUserData().getId());
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {

                    }

                    con.commit(); // Commit the transaction if everything is successful
                } catch (SQLException e) {

                }
            }


        } catch (NotEnoughMoneyException e) {
            exceptionType = ExceptionType.NOT_ENOUGH_MONEY;
        } catch (ItemNotInStockException e) {
            exceptionType = ExceptionType.ITEM_NOT_IN_STOCK;
        } catch (ItemNotInDatabaseException e) {
            exceptionType = ExceptionType.ITEM_NOT_IN_DATABASE;
        }finally {
            String displayedData = controller.getDataManager().displayedDataToJson(controller.getCurrentPage(),exceptionType);
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

