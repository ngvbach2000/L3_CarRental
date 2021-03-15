/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.car.CarDAO;
import bachnv.cart.CartItem;
import bachnv.cart.CartObject;
import bachnv.discount.DiscountDAO;
import bachnv.discount.DiscountDTO;
import bachnv.order.OrderDAO;
import bachnv.order.OrderDTO;
import bachnv.orderdetail.OrderDetailDAO;
import bachnv.orderdetail.OrderDetailDTO;
import bachnv.user.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private final String CHECK_OUT_PAGE = "checkOutPage";
    private final String LOAD_CAR_SERVLET = "";

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

        String url = CHECK_OUT_PAGE;
        boolean error = false;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UserDTO user = (UserDTO) session.getAttribute("USER");
                if (user != null) {
                    CartObject cart = (CartObject) session.getAttribute("CUSTCART");
                    if (cart != null) {
                        List<String> outOfStock = null;
                        CarDAO carDAO = new CarDAO();
                        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                        for (int i = 0; i < cart.getCars().size(); i++) {
                            CartItem cars = cart.getCars().get(i);
                            int quantityInStore = carDAO.getQuantity(cars.getCar().getCarID());
                            int quantityRent = orderDetailDAO.amountCarRentInDate(cars.getCar().getCarID(), cars.getRentalDate(), cars.getReturnDate());
                            int quantityRemaining = quantityInStore - quantityRent;
                            if (quantityRemaining < cart.getCars().get(i).getAmount()) {
                                if (outOfStock == null) {
                                    outOfStock = new ArrayList<>();
                                }
                                String oos = cart.getCars().get(i).getCar().getCarName() + " is out of stock!"
                                        + " The quantity in store is " + quantityRemaining;
                                outOfStock.add(oos);
                                request.setAttribute("OUTOFSTOCK", outOfStock);
                                error = true;
                            }
                        }
                        if (cart.getDiscount() != null) {
                            DiscountDAO discountDAO = new DiscountDAO();
                            DiscountDTO discount = discountDAO.getDiscountByCode(cart.getDiscount().getDiscountCode());
                            if (discount != null) {
                                Date today = new Date();
                                if (!today.before(discount.getExpiryDate())) {
                                    request.setAttribute("DISCOUNTERR", "Discount code has expired!");
                                    error = true;
                                }
                            }
                        }

                        if (!error) {
                            String orderID = UUID.randomUUID().toString();
                            OrderDTO order;
                            if (cart.getDiscount() != null) {
                                order = new OrderDTO(orderID, user.getEmail(), cart.getTotalPrice(), "Pending", cart.getDiscount(), new Date());
                            } else {
                                order = new OrderDTO(orderID, user.getEmail(), cart.getTotalPrice(), "Pending", null, new Date());
                            }
                            OrderDAO orderDAO = new OrderDAO();
                            boolean resultCreateOrder = orderDAO.createNewOrder(order);
                            if (resultCreateOrder) {
                                for (int i = 0; i < cart.getCars().size(); i++) {
                                    String orderDetailID = UUID.randomUUID().toString();
                                    CartItem car = cart.getCars().get(i);
                                    OrderDetailDTO orderDetail = new OrderDetailDTO(orderDetailID, orderID,
                                            cart.getCars().get(i).getCar(), car.getAmount(), car.getPrice(), car.getTotal(),
                                            car.getRentalDate(), car.getReturnDate());
                                    boolean resultCreateNewOrderDetail = orderDetailDAO.createNewOrderDetail(orderDetail);
                                    if (resultCreateNewOrderDetail) {
                                        url = LOAD_CAR_SERVLET;
                                        session.removeAttribute("CUSTCART");
                                    }
                                }
                            }
                        }

                    }
                }
            }
        } catch (SQLException ex) {
            log("CheckOutServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("CheckOutServlet _ Naming " + ex.getMessage());
        } finally {
            if (error) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                response.sendRedirect(url);
            }
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
