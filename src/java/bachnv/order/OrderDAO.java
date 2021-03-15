/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.order;

import bachnv.util.DBAccess;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author ngvba
 */
public class OrderDAO implements Serializable {

    public boolean createNewOrder(OrderDTO order) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Insert Into [Order] (orderID, email, totalPrice, status, discountCode, bookingDate) "
                        + "Values(?,?,?,?,?,?) ";
                ps = con.prepareStatement(sql);
                ps.setString(1, order.getOrderID());
                ps.setString(2, order.getEmail());
                ps.setFloat(3, order.getTotalPrice());
                ps.setString(4, order.getStatus());
                if (order.getDiscount() != null) {
                    ps.setString(5, order.getDiscount().getDiscountCode());
                } else {
                    ps.setString(5, null);
                }
                ps.setDate(6, new java.sql.Date(order.getBookingDate().getTime()));
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    private List<OrderDTO> listOrder;

    public List<OrderDTO> getListOrder() {
        return this.listOrder;
    }

    public void getOrderByEmail(String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select orderID, totalPrice, status, bookingDate "
                        + "From [Order] "
                        + "Where email = ? "
                        + "Order By bookingDate desc ";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString(1);
                    Float totalPrice = rs.getFloat(2);
                    String status = rs.getString(3);
                    Date bookingDate = rs.getDate(4);
                    OrderDTO order = new OrderDTO(orderID, email, totalPrice, status, null, bookingDate);
                    if (listOrder == null) {
                        listOrder = new ArrayList<>();
                    }
                    listOrder.add(order);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public void getOrderByEmailAndBookingDate(String email, Date bookingDate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select orderID, totalPrice, status "
                        + "From [Order] "
                        + "Where email = ? and bookingDate = ? "
                        + "Order By bookingDate desc ";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setDate(2, new java.sql.Date(bookingDate.getTime()));
                rs = ps.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString(1);
                    Float totalPrice = rs.getFloat(2);
                    String status = rs.getString(3);
                    OrderDTO order = new OrderDTO(orderID, email, totalPrice, status, null, bookingDate);
                    if (listOrder == null) {
                        listOrder = new ArrayList<>();
                    }
                    listOrder.add(order);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public void getOrderByEmailAndCarName(String email, String carNameKeyword) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select [Order].orderID, [Order].totalPrice, [Order].status, [Order].bookingDate "
                        + "From [Order] "
                        + "Inner Join OrderDetail "
                        + "On [Order].orderID = OrderDetail.orderID "
                        + "Where [Order].email = ? and OrderDetail.carID IN "
                        + "(Select carID "
                        + "From Car "
                        + "Where carName LIKE ?) "
                        + "Order By bookingDate desc ";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, "%" + carNameKeyword + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString(1);
                    Float totalPrice = rs.getFloat(2);
                    String status = rs.getString(3);
                    Date bookingDate = rs.getDate(4);
                    OrderDTO order = new OrderDTO(orderID, email, totalPrice, status, null, bookingDate);
                    if (listOrder == null) {
                        listOrder = new ArrayList<>();
                    }
                    listOrder.add(order);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public void getOrderByEmailAndCarNameAndBookingDate(String email, String carNameKeyword, Date bookingDate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select [Order].orderID, [Order].totalPrice, [Order].status "
                        + "From [Order] "
                        + "Inner Join OrderDetail "
                        + "On [Order].orderID = OrderDetail.orderID "
                        + "Where [Order].email = ? and bookingDate = ? and OrderDetail.carID IN "
                        + "(Select Distinct carID "
                        + "From Car "
                        + "Where carName LIKE ?) "
                        + "Order By bookingDate desc ";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setDate(2, new java.sql.Date(bookingDate.getTime()));
                ps.setString(3, "%" + carNameKeyword + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString(1);
                    Float totalPrice = rs.getFloat(2);
                    String status = rs.getString(3);
                    OrderDTO order = new OrderDTO(orderID, email, totalPrice, status, null, bookingDate);
                    if (listOrder == null) {
                        listOrder = new ArrayList<>();
                    }
                    listOrder.add(order);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public boolean deleteOrder(String id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Update [Order] "
                        + "Set status = 'Cancelled' "
                        + "Where orderID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, id);
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

}
