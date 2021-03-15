/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.feedback;

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
public class FeedbackDAO implements Serializable{
    
    public boolean createNewFeedback(FeedbackDTO feedback) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Insert Into feedback (feedbackID, orderID, [content], rating) "
                        + "Values(?,?,?,?) ";
                ps = con.prepareStatement(sql);
                ps.setString(1, feedback.getFeedbackID());
                ps.setString(2, feedback.getOrderID());
                ps.setString(3, feedback.getContent());
                ps.setInt(4, feedback.getRating());
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
    
    public FeedbackDTO getFeedbackByOrderID(String orderID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select feedbackID, [content], rating "
                        + "From Feedback "
                        + "Where orderID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, orderID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String feedbackID = rs.getString(1);
                    String content = rs.getString(2);
                    int rating = rs.getInt(3);
                    FeedbackDTO feedback = new FeedbackDTO(feedbackID, orderID, content, rating);
                    return feedback;
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
