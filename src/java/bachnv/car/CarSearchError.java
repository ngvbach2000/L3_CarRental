/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.car;

import java.io.Serializable;

/**
 *
 * @author ngvba
 */
public class CarSearchError implements Serializable{
    
    private String rentalDateIsEmpty;
    private String returnDateIsEmpty;
    private String amountLengthErr;
    private String rangeOfDateErr;

    public CarSearchError() {
    }

    public String getRentalDateIsEmpty() {
        return rentalDateIsEmpty;
    }

    public void setRentalDateIsEmpty(String rentalDateIsEmpty) {
        this.rentalDateIsEmpty = rentalDateIsEmpty;
    }

    public String getReturnDateIsEmpty() {
        return returnDateIsEmpty;
    }

    public void setReturnDateIsEmpty(String returnDateIsEmpty) {
        this.returnDateIsEmpty = returnDateIsEmpty;
    }

    public String getAmountLengthErr() {
        return amountLengthErr;
    }

    public void setAmountLengthErr(String amountLengthErr) {
        this.amountLengthErr = amountLengthErr;
    }

    public String getRangeOfDateErr() {
        return rangeOfDateErr;
    }

    public void setRangeOfDateErr(String rangeOfDateErr) {
        this.rangeOfDateErr = rangeOfDateErr;
    }
    
}
