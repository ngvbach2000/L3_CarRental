/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.orderdetail;

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
public class OrderDetailDAO implements Serializable {

    public boolean createNewOrderDetail(OrderDetailDTO orderDetail) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Insert Into OrderDetail (orderDetailID, orderID, carID, amount, price, total, rentalDate, returnDate) "
                        + "Values(?,?,?,?,?,?,?,?) ";
                ps = con.prepareStatement(sql);
                ps.setString(1, orderDetail.getOrderDetailID());
                ps.setString(2, orderDetail.getOrderID());
                ps.setString(3, orderDetail.getCar().getCarID());
                ps.setInt(4, orderDetail.getAmount());
                ps.setFloat(5, orderDetail.getPrice());
                ps.setFloat(6, orderDetail.getTotal());
                java.sql.Date sqlRentalDate = new java.sql.Date(orderDetail.getRentalDate().getTime());
                ps.setDate(7, sqlRentalDate);
                java.sql.Date sqlReturnDate = new java.sql.Date(orderDetail.getReturnDate().getTime());
                ps.setDate(8, sqlReturnDate);
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

    private List<OrderDetailDTO> listOrderDetail;

    public List<OrderDetailDTO> getListOrderDetail() {
        return this.listOrderDetail;
    }

    public void getOrderDetailByOrderID(String orderID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select orderDetailID, carID, amount, price, total, rentalDate, returnDate "
                        + "From OrderDetail "
                        + "Where orderID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, orderID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String orderDetailID = rs.getString(1);
                    String carID = rs.getString(2);
                    int amount = rs.getInt(3);
                    Float price = rs.getFloat(4);
                    Float total = rs.getFloat(5);
                    Date rentalDate = rs.getDate(6);
                    Date returnDate = rs.getDate(7);
                    OrderDetailDTO orderDetail = new OrderDetailDTO(orderDetailID, orderID, null, amount, price, total, rentalDate, returnDate);
                    if (listOrderDetail == null) {
                        listOrderDetail = new ArrayList<>();
                    }
                    listOrderDetail.add(orderDetail);
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

    public int amountCarRentInDate(String carID, Date rentalDate, Date returnDate) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select amount "
                        + "From OrderDetail "
                        + "INNER JOIN [Order] "
                        + "On OrderDetail.orderID = [Order].orderID "
                        + "Where carID = ? AND [Order].status != 'Cancelled' AND "
                        + "((? <= rentalDate AND ? >= rentalDate) "
                        + "OR (rentalDate <= ? AND returnDate >= ?) "
                        + "OR (rentalDate < ? AND returnDate >= ?)) ";
                ps = con.prepareStatement(sql);
                ps.setString(1, carID);
                ps.setDate(2, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(3, new java.sql.Date(returnDate.getTime()));
                ps.setDate(4, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(5, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(6, new java.sql.Date(returnDate.getTime()));
                ps.setDate(7, new java.sql.Date(returnDate.getTime()));
                rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
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
        return 0;
    }

}
