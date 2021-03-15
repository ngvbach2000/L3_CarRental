/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.cart;

import bachnv.car.CarDTO;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ngvba
 */
public class CartItem implements Serializable {

    private CarDTO car;
    private int amount;
    private float price;
    private float total;
    private Date rentalDate;
    private Date returnDate;

    public CartItem() {
    }

    public CartItem(CarDTO car, int amount, float price, float total, Date rentalDate, Date returnDate) {
        this.car = car;
        this.amount = amount;
        this.price = price;
        this.total = total;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

}
