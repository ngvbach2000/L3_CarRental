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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "SearchOrderHistoryServlet", urlPatterns = {"/SearchOrderHistoryServlet"})
public class SearchOrderHistoryServlet extends HttpServlet {

    private final String VIEW_HISTORY_SERVLET = "viewHistory";
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String url = ORDER_HISTORY_PAGE;
        String searchDateValue = request.getParameter("txtSearchDateValue");
        String searchNameValue = request.getParameter("txtSearchNameValue");

        try {
            if (searchDateValue != null) {
                if ("".equals(searchDateValue) && "".equals(searchNameValue)) {
                    url = VIEW_HISTORY_SERVLET;
                } else {
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        UserDTO user = (UserDTO) session.getAttribute("USER");
                        if (user != null) {
                            OrderDAO orderDAO = new OrderDAO();
                            if (!searchDateValue.isEmpty() && searchNameValue.isEmpty()) {
                                orderDAO.getOrderByEmailAndBookingDate(user.getEmail(), dateFormat.parse(searchDateValue));
                            } else if (searchDateValue.isEmpty() && !searchNameValue.isEmpty()) {
                                orderDAO.getOrderByEmailAndCarName(user.getEmail(), searchNameValue);
                            } else {
                                orderDAO.getOrderByEmailAndCarNameAndBookingDate(user.getEmail(), searchNameValue, dateFormat.parse(searchDateValue));
                            }
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
                                for (int i = 0; i < listOrder.size(); i++) {
                                    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                                    orderDetailDAO.getOrderDetailByOrderID(listOrder.get(i).getOrderID());
                                    List<OrderDetailDTO> listOrderDetail = orderDetailDAO.getListOrderDetail();
                                    for (int j = 0; j < listOrderDetail.size(); j++) {
                                        CarDAO carDAO = new CarDAO();
                                        CarDTO car = carDAO.getCarByOrderDetailID(listOrderDetail.get(j).getOrderDetailID());
                                        listOrderDetail.get(j).setCar(car);
                                    }
                                    orderHistory.put(listOrder.get(i), listOrderDetail);
                                }
                                request.setAttribute("ORDERHISTORY", orderHistory);
                            }
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            log("SearchOrderHistoryServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("SearchOrderHistoryServlet _ Naming " + ex.getMessage());
        } catch (ParseException ex) {
            log("SearchOrderHistoryServlet _ Parse " + ex.getMessage());
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
