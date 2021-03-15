/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.cart.CartItem;
import bachnv.cart.CartObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ngvba
 */
@WebServlet(name = "ModifyCartServlet", urlPatterns = {"/ModifyCartServlet"})
public class ModifyCartServlet extends HttpServlet {

    private final String CART_PAGE = "cartPage";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String button = request.getParameter("btAction");
        String carID = request.getParameter("txtCarID");
        int updateAmount = Integer.parseInt(request.getParameter("txtAmount"));
        String url = CART_PAGE;

        try {
            HttpSession session = request.getSession();
            CartObject cart = (CartObject) session.getAttribute("CUSTCART");
            if (cart != null) {
                List<CartItem> cars = cart.getCars();
                if (cars != null) {
                    if ("Update Amount".equals(button)) {
                        for (int i = 0; i < cars.size(); i++) {
                            if (cars.get(i).getCar().getCarID().equals(carID)) {
                                cart.updateAmount(carID, updateAmount);
                            }
                        }
                    } else if ("Delete Car".equals(button)) {
                        for (int i = 0; i < cars.size(); i++) {
                            if (cars.get(i).getCar().getCarID().equals(carID)) {
                                cart.removeCarFromCart(carID);
                            }
                        }
                    }
                }
                if (cars.isEmpty()) {
                    cart = null;
                }
                session.setAttribute("CUSTCART", cart);
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
