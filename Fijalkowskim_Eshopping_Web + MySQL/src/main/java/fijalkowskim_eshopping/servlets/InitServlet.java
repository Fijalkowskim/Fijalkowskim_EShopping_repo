package fijalkowskim_eshopping.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.sql.*;
import java.util.HashMap;
import java.util.List;

@WebServlet("/init")
public class InitServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws JsonProcessingException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        BuyItemServlet.savedItems = new HashMap<Integer,Integer>();
        createTables();
        //insertExampleItems();
        String initDataJson = Controller.getInstance().getDataManager().displayedDataToJson(Controller.getInstance().getCurrentItemIndex(), ExceptionType.NONE);
        PrintWriter out = response.getWriter();
        out.println(initDataJson);
    }
    public void createTables() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eshopping?useSSL=false", "root", "root")) {
            Statement statement = con.createStatement();

            // Create Items table with auto-incremented id
            statement.executeUpdate("CREATE TABLE Items "
                    + "(id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(255), price FLOAT, description VARCHAR(255), imageUrl VARCHAR(255))");
            System.out.println("Table Items created");

            // Create ItemContainers table with foreign key reference
            statement.executeUpdate("CREATE TABLE ItemContainers "
                    + "(itemId INT, count INT, "
                    + "FOREIGN KEY (itemId) REFERENCES Items(id))");
            System.out.println("Table ItemContainers created");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertExampleItems() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eshopping?useSSL=false", "root", "root")) {
            List<ShopItemContainer> itemContainers = Controller.getInstance().getTargetedShopStock().getItemDatabase();
            for (ShopItemContainer container : itemContainers) {
                String name = container.getShopItem().getName();
                float price = container.getShopItem().getPrice();
                String description = container.getShopItem().getDescription();
                String imageUrl = container.getShopItem().getImageUrl();
                int count = container.getCount();

                // Insert data into Items table using PreparedStatement
                String insertItemsQuery = "INSERT INTO Items (name, price, description, imageUrl) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatementItems = con.prepareStatement(insertItemsQuery, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatementItems.setString(1, name);
                    preparedStatementItems.setFloat(2, price);
                    preparedStatementItems.setString(3, description);
                    preparedStatementItems.setString(4, imageUrl);

                    preparedStatementItems.executeUpdate();

                    // Get the auto-generated itemId
                    ResultSet keys = preparedStatementItems.getGeneratedKeys();
                    int generatedItemId = -1;
                    if (keys.next()) {
                        generatedItemId = keys.getInt(1);

                        // Insert data into ItemContainers table using another PreparedStatement
                        if (generatedItemId != -1) {
                            String insertItemContainersQuery = "INSERT INTO ItemContainers (itemId, count) VALUES (?, ?)";
                            try (PreparedStatement preparedStatementItemContainers = con.prepareStatement(insertItemContainersQuery)) {
                                preparedStatementItemContainers.setInt(1, generatedItemId);
                                preparedStatementItemContainers.setInt(2, count);
                                preparedStatementItemContainers.executeUpdate();
                            }
                        }
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("Data inserted");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
    void processCookies(Cookie[] cookies){
        float savedCash = -1f;
        int savedPage = -1;
        for(Cookie cookie : cookies) {
            if(cookie == null) continue;
            if (cookie.getName().contains("item")){
                try {
                    int key = Integer.parseInt(cookie.getName().replaceAll("\\D+", ""));
                    BuyItemServlet.savedItems.put(key, Integer.parseInt(cookie.getValue()));
                } catch (NumberFormatException e) {
                }
            }else{
                switch (cookie.getName()){
                    case CookieVariables.cashCookie:
                        savedCash = Float.parseFloat(cookie.getValue());
                        break;
                    case CookieVariables.pageCookie:
                        savedPage = Integer.parseInt(cookie.getValue());
                        break;
                    default:
                }
            }

        }

        Controller.getInstance().LoadSavedData(savedCash, BuyItemServlet.savedItems, savedPage);
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
