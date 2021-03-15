/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.car.CarDAO;
import bachnv.car.CarDTO;
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
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "ViewHistoryServlet", urlPatterns = {"/ViewHistoryServlet"})
public class ViewHistoryServlet extends HttpServlet {

    private final String ORDER_HISTORY_PAGE = "orderHistoryPage";

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

        String url = ORDER_HISTORY_PAGE;

        try {

            HttpSession session = request.getSession(false);
            if (session != null) {
                UserDTO user = (UserDTO) session.getAttribute("USER");
                if (user != null) {
                    OrderDAO orderDAO = new OrderDAO();
                    orderDAO.getOrderByEmail(user.getEmail());
                    List<OrderDTO> listOrder = orderDAO.getListOrder();
                    if (listOrder != null) {
                        for (int i = 0; i < listOrder.size(); i++) {
                            DiscountDAO discountDAO = new DiscountDAO();
                            DiscountDTO discount = discountDAO.getDiscountByOrderID(listOrder.get(i).getOrderID());
                            if (discount != null) {
                                listOrder.get(i).setDiscount(discount);
                            }
                        }
                        Map<OrderDTO, List<OrderDetailDTO>> orderHistory = new LinkedHashMap<>();
                        Map<String, Map<Date, Date>> rentDate = new LinkedHashMap<>();
                        for (int i = 0; i < listOrder.size(); i++) {
                            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                            orderDetailDAO.getOrderDetailByOrderID(listOrder.get(i).getOrderID());
                            List<OrderDetailDTO> listOrderDetail = orderDetailDAO.getListOrderDetail();
                            Map<Date, Date> minmaxDate = new LinkedHashMap<>();
                            List<Date> date = new ArrayList<>();
                            for (int j = 0; j < listOrderDetail.size(); j++) {
                                CarDAO carDAO = new CarDAO();
                                CarDTO car = carDAO.getCarByOrderDetailID(listOrderDetail.get(j).getOrderDetailID());
                                listOrderDetail.get(j).setCar(car);
                                date.add(listOrderDetail.get(j).getRentalDate());
                                date.add(listOrderDetail.get(j).getReturnDate());
                            }
                            orderHistory.put(listOrder.get(i), listOrderDetail);
                            Collections.sort(date);
                            minmaxDate.put(date.get(0), date.get(date.size() - 1));
                            rentDate.put(listOrder.get(i).getOrderID(), minmaxDate);
                        }
                        request.setAttribute("ORDERHISTORY", orderHistory);
                    }
                }
            }

        } catch (SQLException ex) {
            log("ViewHistoryServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("ViewHistoryServlet _ Naming " + ex.getMessage());
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
