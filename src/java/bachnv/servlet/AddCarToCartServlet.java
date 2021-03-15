/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.car.CarDAO;
import bachnv.car.CarDTO;
import bachnv.cart.CartObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "AddCarToCartServlet", urlPatterns = {"/AddCarToCartServlet"})
public class AddCarToCartServlet extends HttpServlet {

    private final String LOAD_CAR_SERVLET = "";
    private final String LOGIN_PAGE = "loginPage";

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String carID = request.getParameter("txtCarID");
        String rentalDate = request.getParameter("txtRentalDate");
        String returnDate = request.getParameter("txtReturnDate");
        String amount = request.getParameter("txtAmount");
        String currentPage = request.getParameter("currentPage");
        String searchCategoryValue = request.getParameter("txtSearchCategoryValue");
        String searchNameValue = request.getParameter("txtSearchNameValue");
        String url = LOGIN_PAGE;

        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("USER") != null) {
                url = LOAD_CAR_SERVLET;
                if (rentalDate.isEmpty() || returnDate.isEmpty() || amount.isEmpty()) {
                    request.setAttribute("DATEERR", "Please search with Amount, Rental Date and Return Date to see the free Car!");
                } else {
                    CartObject cart = (CartObject) session.getAttribute("CUSTCART");
                    if (cart == null) {
                        cart = new CartObject();
                    }
                    CarDAO dao = new CarDAO();
                    CarDTO car = dao.getCarByID(carID);
                    if (car != null) {
                        cart.addCarToCart(car, Integer.parseInt(amount), dateFormat.parse(rentalDate), dateFormat.parse(returnDate));
                        session.setAttribute("CUSTCART", cart);
                    }
                    if (searchCategoryValue.isEmpty() && searchNameValue.isEmpty()) {
                        url = "searchCar?"
                                + "txtRentalDate=" + rentalDate
                                + "&txtReturnDate=" + returnDate
                                + "&txtAmount=" + amount
                                + "&page=" + currentPage;
                    } else {
                        url = "searchCar?"
                                + "txtSearchNameValue=" + searchNameValue
                                + "&txtSearchCategoryValue=" + searchCategoryValue
                                + "&txtRentalDate=" + rentalDate
                                + "&txtReturnDate=" + returnDate
                                + "&txtAmount=" + amount
                                + "&page=" + currentPage;
                    }
                }
            }
        } catch (SQLException ex) {
            log("AddToCartServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("AddToCartServlet _ Naming " + ex.getMessage());
        } catch (ParseException ex) {
            log("AddToCartServlet _ Parse " + ex.getMessage());
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
