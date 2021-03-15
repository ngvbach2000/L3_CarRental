/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.discount;

import bachnv.util.DBAccess;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.NamingException;

/**
 *
 * @author ngvba
 */
public class DiscountDAO implements Serializable{
    
    public DiscountDTO getDiscountByCode(String code) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select discountName, saleOff, expiryDate "
                        + "From Discount "
                        + "Where discountCode = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, code);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String discountName = rs.getString(1);
                    float saleOff = rs.getFloat(2);
                    Date experyDate = rs.getDate(3);
                    DiscountDTO discount = new DiscountDTO(code, discountName, saleOff, experyDate);
                    return discount;
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
        return null;
    }
    
    public DiscountDTO getDiscountByOrderID(String OrderID) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select Discount.discountCode, discountName, saleOff "
                        + "From Discount "
                        + "Inner Join [Order] "
                        + "On Discount.discountCode = [Order].discountCode "
                        + "Where [Order].orderID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, OrderID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String discountCode = rs.getString(1);
                    String discountName = rs.getString(2);
                    float saleOff = rs.getFloat(3);
                    DiscountDTO discount = new DiscountDTO(discountCode, discountName, saleOff, null);
                    return discount;
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
        return null;
    }
    
}
