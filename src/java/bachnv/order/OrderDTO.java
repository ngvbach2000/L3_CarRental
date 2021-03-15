/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.order;

import bachnv.discount.DiscountDTO;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ngvba
 */
public class OrderDTO implements Serializable{
    
    private String orderID;
    private String email;
    private float totalPrice;
    private String status;
    private DiscountDTO discount;
    private Date bookingDate;

    public OrderDTO(String orderID, String email, float totalPrice, String status, DiscountDTO discount, Date bookingDate) {
        this.orderID = orderID;
        this.email = email;
        this.totalPrice = totalPrice;
        this.status = status;
        this.discount = discount;
        this.bookingDate = bookingDate;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DiscountDTO getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountDTO discount) {
        this.discount = discount;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
    
}
