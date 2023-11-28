package fijalkowskim_eshopping.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fijalkowskim_eshopping.controller.Controller;
import fijalkowskim_eshopping.model.CookieVariables;
import fijalkowskim_eshopping.model.ExceptionType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/init")
public class InitServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws JsonProcessingException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        BuyItemServlet.savedItems = new HashMap<>();

        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            processCookies(cookies);

        }
        String initDataJson = Controller.getInstance().getDataManager().displayedDataToJson(Controller.getInstance().getCurrentItemIndex(), ExceptionType.NONE);
        PrintWriter out = response.getWriter();
        out.println(initDataJson);
    }
    void processCookies(Cookie[] cookies){
        float savedCash = -1f;
        int savedPage = -1;
        Map<Integer, Integer> savedItems = null;
        for(Cookie cookie : cookies) {
            if(cookie == null) continue;
            switch (cookie.getName()){
                case CookieVariables.cashCookie:
                    savedCash = Float.parseFloat(cookie.getValue());
                    break;
                case CookieVariables.pageCookie:
                    savedPage = Integer.parseInt(cookie.getValue());
                    break;
                case CookieVariables.itemsCookie:
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        savedItems = objectMapper.readValue(cookie.getValue(), Map.class);
                    } catch (IOException e) {

                    }
                    break;
                default:
            }
        }

        Controller.getInstance().LoadSavedData(savedCash, savedItems, savedPage);
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
