/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.feedback;

import java.io.Serializable;

/**
 *
 * @author ngvba
 */
public class FeedbackDTO implements Serializable{
    private String feedbackID;
    private String orderID;
    private String content;
    private int rating;

    public FeedbackDTO(String feedbackID, String orderID, String content, int rating) {
        this.feedbackID = feedbackID;
        this.orderID = orderID;
        this.content = content;
        this.rating = rating;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
}
