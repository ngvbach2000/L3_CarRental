/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.discount;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ngvba
 */
public class DiscountDTO implements Serializable{
    private String discountCode;
    private String discountName;
    private float saleOff;
    private Date expiryDate;

    public DiscountDTO(String discountCode, String discountName, float saleOff, Date expiryDate) {
        this.discountCode = discountCode;
        this.discountName = discountName;
        this.saleOff = saleOff;
        this.expiryDate = expiryDate;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public float getSaleOff() {
        return saleOff;
    }

    public void setSaleOff(float saleOff) {
        this.saleOff = saleOff;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    
}
