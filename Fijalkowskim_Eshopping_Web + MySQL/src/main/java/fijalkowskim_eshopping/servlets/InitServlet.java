package fijalkowskim_eshopping.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import fijalkowskim_eshopping.controller.Controller;
import fijalkowskim_eshopping.model.ExceptionType;
import fijalkowskim_eshopping.model.ShopItem;
import fijalkowskim_eshopping.model.ShopItemContainer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/init")
public class InitServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws JsonProcessingException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        createTables();
        loginUser();
        loadSessionFromDatabase();
        //insertExampleItems();
        String initDataJson = Controller.getInstance().getDataManager().displayedDataToJson(Controller.getInstance().getCurrentPage(), ExceptionType.NONE);
        PrintWriter out = response.getWriter();
        out.println(initDataJson);
    }
    public void createTables() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eshopping?useSSL=false", "root", "root")) {
            Statement statement = con.createStatement();

            try {
                statement.executeUpdate("CREATE TABLE Items "
                        + "(id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "name VARCHAR(255), price FLOAT, description VARCHAR(255), imageUrl VARCHAR(255))");
                System.out.println("Table Items created");
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            try{
            statement.executeUpdate("CREATE TABLE ItemContainers "
                    + "(itemId INT, count INT, "
                    + "FOREIGN KEY (itemId) REFERENCES Items(id))");
            System.out.println("Table ItemContainers created");
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            try{
                statement.executeUpdate("CREATE TABLE SessionData "
                        + "(userID INT, cash FLOAT, pageIndex INT)");
                System.out.println("Table SessionData created");
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    void loginUser() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eshopping?useSSL=false", "root", "root")) {
            int currentUserId = Controller.getInstance().getDataManager().getUserData().getId();
            String selectUserQuery = "SELECT * FROM SessionData WHERE userId = ?";
            try (PreparedStatement preparedStatementUser = con.prepareStatement(selectUserQuery)) {
                preparedStatementUser.setInt(1, currentUserId);
                ResultSet userResultSet = preparedStatementUser.executeQuery();

                if (!userResultSet.next()) {
                    String insertUserQuery = "INSERT INTO SessionData (userId, cash, pageIndex) VALUES (?, ?, ?)";
                    try (PreparedStatement preparedStatementInsertUser = con.prepareStatement(insertUserQuery)) {
                        preparedStatementInsertUser.setInt(1, currentUserId);
                        preparedStatementInsertUser.setFloat(2, Controller.getInstance().getDataManager().getUserData().getCash());
                        preparedStatementInsertUser.setInt(3, 0);
                        preparedStatementInsertUser.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void loadSessionFromDatabase() {
        List<ShopItemContainer> loadedItemContainers = new ArrayList<>();
        float loadedCash = 2000;
        int loadedPageIndex = 0;
        int loggedUserID = Controller.getInstance().getDataManager().getUserData().getId();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eshopping?useSSL=false", "root", "root")) {
            String selectItemsQuery = "SELECT * FROM Items";
            try (PreparedStatement preparedStatementItems = con.prepareStatement(selectItemsQuery)) {
                ResultSet itemsResultSet = preparedStatementItems.executeQuery();
                while (itemsResultSet.next()) {
                    int id = itemsResultSet.getInt("id");
                    String name = itemsResultSet.getString("name");
                    float price = itemsResultSet.getFloat("price");
                    String description = itemsResultSet.getString("description");
                    String imageUrl = itemsResultSet.getString("imageUrl");

                    ShopItem shopItem = new ShopItem(id, name, price, description, imageUrl);

                    String selectItemContainersQuery = "SELECT count FROM ItemContainers WHERE itemId = ?";
                    try (PreparedStatement preparedStatementItemContainers = con.prepareStatement(selectItemContainersQuery)) {
                        preparedStatementItemContainers.setInt(1, id);
                        ResultSet itemContainersResultSet = preparedStatementItemContainers.executeQuery();

                        int count = 0;
                        if (itemContainersResultSet.next()) {
                            count = itemContainersResultSet.getInt("count");
                        }
                        loadedItemContainers.add(new ShopItemContainer(shopItem, count));
                    }
                }
            }

            String selectSessionQuery = "SELECT * FROM SessionData WHERE userId = ?";
            try (PreparedStatement preparedStatementItems = con.prepareStatement(selectSessionQuery)) {
                preparedStatementItems.setInt(1, loggedUserID);
                ResultSet sessionResultSet = preparedStatementItems.executeQuery();
                while (sessionResultSet.next()) {
                    loadedPageIndex = sessionResultSet.getInt("pageIndex");
                    loadedCash = sessionResultSet.getFloat("cash");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Controller.getInstance().LoadSavedData(loadedCash, loadedItemContainers, loadedPageIndex);
    }
    public void addItemToDatabase(ShopItem item, int count){
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/eshopping?useSSL=false", "root", "root")) {
                String insertItemsQuery = "INSERT INTO Items (name, price, description, imageUrl) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatementItems = con.prepareStatement(insertItemsQuery, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatementItems.setString(1, item.getName());
                    preparedStatementItems.setFloat(2, item.getPrice());
                    preparedStatementItems.setString(3, item.getDescription());
                    preparedStatementItems.setString(4, item.getImageUrl());

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
            System.out.println("Data inserted");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
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
