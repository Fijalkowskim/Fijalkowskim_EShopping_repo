package fijalkowskim_eshopping.servlets;

import fijalkowskim_eshopping.controller.Controller;
import fijalkowskim_eshopping.model.CookieVariables;
import fijalkowskim_eshopping.model.ExceptionType;
import fijalkowskim_eshopping.model.ShopItemContainer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/changeItem")
public class ChangeItemServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String arg1 = request.getParameter("arg1");
        if(arg1.equals("next") || arg1.equals("previous")){
            boolean nextItem = arg1.equals("next");
            ShopItemContainer shopItemContainer = Controller.getInstance().ChangeItem(nextItem);

            Connection con = Controller.getInstance().getDbConnection();
            if(con != null) {
                try {
                    String updatePageQuery = "UPDATE SessionData SET pageIndex = ? WHERE userId = ?";
                    try (PreparedStatement preparedStatement = con.prepareStatement(updatePageQuery)) {
                        preparedStatement.setInt(1, Controller.getInstance().getCurrentPage());
                        preparedStatement.setInt(2, Controller.getInstance().getDataManager().getUserData().getId());
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                    }
                } catch (Exception e) {
                }
            }
            String jsonItem = shopItemContainer.toJson();
            PrintWriter out = response.getWriter();
            out.println(jsonItem);
        }
        else{
            response.sendError(response.SC_BAD_REQUEST, "Invalid parameters");
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
