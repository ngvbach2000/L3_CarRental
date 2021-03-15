/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.cart;

import bachnv.car.CarDTO;
import bachnv.discount.DiscountDTO;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ngvba
 */
public class CartObject implements Serializable {

    private List<CartItem> cars;
    private float totalPrice;
    private DiscountDTO discount;

    public List<CartItem> getCars() {
        return cars;
    }

    public float getTotalPrice() {
        return (float) Math.round(totalPrice * 100) / 100;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public DiscountDTO getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountDTO discount) {
        this.discount = discount;
        calculateTotalPrice();
    }

    public void removeDiscount() {
        this.discount = null;
        calculateTotalPrice();
    }

    public void addCarToCart(CarDTO carDTO, int amount, Date rentalDate, Date returnDate) {
        if (cars == null) {
            cars = new ArrayList();
        }
        boolean isExited = false;
        for (int i = 0; i < cars.size(); i++) {
            CartItem car = cars.get(i);
            if (car.getCar().getCarID().equals(carDTO.getCarID())) {
                car.setAmount(car.getAmount() + 1);
                car.setRentalDate(rentalDate);
                car.setReturnDate(returnDate);
                car.setTotal(car.getPrice() * car.getAmount() * calculateDay(i));
                isExited = true;
            }
        }
        if (!isExited) {
            if (amount > 0) {
                CartItem car = new CartItem(carDTO, amount, carDTO.getPrice(),
                        0, rentalDate, returnDate);
                cars.add(car);
                cars.get(cars.size() - 1).setTotal(car.getPrice() * car.getAmount() * calculateDay(cars.size() - 1));
            }
        }
        calculateTotalPrice();
    }

    public void removeCarFromCart(String id) {
        if (cars == null) {
            return;
        }
        for (int i = 0; i < cars.size(); i++) {
            CartItem car = cars.get(i);
            if (car.getCar().getCarID().equals(id)) {
                cars.remove(i);
            }
        }
        if (cars.size() == 0) {
            cars = null;
            totalPrice = 0;
        } else if (cars != null) {
            calculateTotalPrice();
        }
    }

    public void updateAmount(String id, int amount) {
        for (int i = 0; i < cars.size(); i++) {
            CartItem car = cars.get(i);
            if (car.getCar().getCarID().equals(id)) {
                car.setAmount(amount);
                car.setTotal(car.getPrice() * car.getAmount() * calculateDay(i));
                calculateTotalPrice();
            }
        }
    }

    private void calculateTotalPrice() {
        float totalPrice = 0;
        for (int i = 0; i < cars.size(); i++) {
            CartItem car = (CartItem) cars.get(i);
            totalPrice += car.getTotal();
        }
        if (discount != null) {
            totalPrice = totalPrice - (totalPrice * discount.getSaleOff());
        }
        setTotalPrice(totalPrice);
    }

    private int calculateDay(int pos) {
        Instant start = cars.get(pos).getRentalDate().toInstant();
        Instant end = cars.get(pos).getReturnDate().toInstant();
        long duration = Duration.between(start, end).toDays();
        int rentDay = Math.round(duration);
        return rentDay;
    }

}
