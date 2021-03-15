/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.cart.CartObject;
import bachnv.discount.DiscountDAO;
import bachnv.discount.DiscountDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.NamingException;
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
@WebServlet(name = "ApplyDiscountServlet", urlPatterns = {"/ApplyDiscountServlet"})
public class ApplyDiscountServlet extends HttpServlet {

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

        String discountCode = request.getParameter("txtDiscountCode");
        String url = CART_PAGE;
        Date today = new Date();

        try {
            if (discountCode != null) {
                if (discountCode.isEmpty()) {
                    HttpSession session = request.getSession();
                    CartObject cart = (CartObject) session.getAttribute("CUSTCART");
                    if (cart != null) {
                        cart.removeDiscount();
                        session.setAttribute("CUSTCART", cart);
                    }
                } else {
                    DiscountDAO discountDAO = new DiscountDAO();
                    DiscountDTO discount = discountDAO.getDiscountByCode(discountCode);
                    if (discount != null) {
                        if (today.before(discount.getExpiryDate())) {
                            HttpSession session = request.getSession();
                            CartObject cart = (CartObject) session.getAttribute("CUSTCART");
                            if (cart != null) {
                                cart.setDiscount(discount);
                                session.setAttribute("CUSTCART", cart);
                            }
                        } else {
                            request.setAttribute("DISCOUNTERR", "Discount code has expired!");
                        }
                    } else {
                        request.setAttribute("DISCOUNTERR", "Discount code not found!");
                    }
                }
            }
        } catch (SQLException ex) {
            log("ApplyDiscountServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("ApplyDiscountServlet _ Naming " + ex.getMessage());
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
