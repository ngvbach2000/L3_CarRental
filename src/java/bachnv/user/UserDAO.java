/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.user;

import bachnv.util.DBAccess;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author ngvba
 */
public class UserDAO implements Serializable{
    
    public UserDTO checkLogin(String username, String password) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select fullname, phone, address, status, role "
                        + "From [User] "
                        + "Where email = ? and password = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String fullname = rs.getString(1);
                    String phone = rs.getString(2);
                    String address = rs.getString(3);
                    String status = rs.getString(4);
                    String role = rs.getString(5);
                    UserDTO dto = new UserDTO(username, password, phone, fullname, address, null, status, role);
                    return dto;
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
    
    public boolean registerAccount(UserDTO user) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Insert Into [User](email, password, fullname, phone, address, createDate, status, role) "
                        + "Values(?,?,?,?,?,?,?,?) ";
                ps = con.prepareStatement(sql);
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getPassword());
                ps.setNString(3, user.getFullname());
                ps.setString(4, user.getPhone());
                ps.setNString(5, user.getAddress());
                java.sql.Date sqlDate = new java.sql.Date(user.getCreateDate().getTime());
                ps.setDate(6, sqlDate);
                ps.setString(7, user.getStatus());
                ps.setString(8, user.getRole());
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        }
     finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
    public boolean updateStatusToActive(String email) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Update [User] "
                        + "Set status = 'active' "
                        + "Where email = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        }
     finally {
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
