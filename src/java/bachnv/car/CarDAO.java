/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.car;

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
public class CarDAO implements Serializable {

    public CarDTO getCarByID(String carID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select carID, carName, seats, transmission, color, year, category, price, quantity "
                        + "From Car "
                        + "Where carID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, carID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String carName = rs.getString(2);
                    int seats = rs.getInt(3);
                    String transmission = rs.getString(4);
                    String color = rs.getString(5);
                    String year = rs.getString(6);
                    String category = rs.getString(7);
                    float price = rs.getFloat(8);
                    int quantity = rs.getInt(9);
                    CarDTO car = new CarDTO(carID, carName, seats, transmission, color, year, category, price, quantity);
                    return car;
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

    private List<CarDTO> listCar;

    public List<CarDTO> getListCar() {
        return this.listCar;
    }

    public void getCar(int offset, int fetch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select carID, carName, seats, transmission, color, year, category, price, quantity "
                        + "From Car "
                        + "Where quantity > '0' "
                        + "Order by createDate desc "
                        + "Offset ? Rows Fetch Next ? Rows Only";
                ps = con.prepareStatement(sql);
                ps.setInt(1, offset);
                ps.setInt(2, fetch);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String carID = rs.getString(1);
                    String carName = rs.getString(2);
                    int seats = rs.getInt(3);
                    String transmission = rs.getString(4);
                    String color = rs.getString(5);
                    String year = rs.getString(6);
                    String category = rs.getString(7);
                    float price = rs.getFloat(8);
                    int quantity = rs.getInt(9);
                    CarDTO car = new CarDTO(carID, carName, seats, transmission, color, year, category, price, quantity);
                    if (listCar == null) {
                        listCar = new ArrayList();
                    }
                    listCar.add(car);
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

    public int noOfCarRecord() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select count (carID) "
                        + "From Car "
                        + "Where quantity > '0' ";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int noOfRecords = rs.getInt(1);
                    return noOfRecords;
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

    public void searchCarByRentalDateAndReturnDate(Date rentalDate, Date returnDate, int amount, int offset, int fetch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select carID, carName, seats, transmission, color, year, category, price, quantity "
                        + "From Car "
                        + "Where quantity >= ? AND CarID NOT IN "
                        + "(Select Car.carID "
                        + "From Car "
                        + "Inner Join OrderDetail "
                        + "On Car.carID = OrderDetail.carID "
                        + "Inner Join [Order] "
                        + "On OrderDetail.orderID = [Order].orderID "
                        + "Where ((? <= rentalDate AND ? >= rentalDate) "
                        + "OR (rentalDate <= ? AND returnDate >= ?) "
                        + "OR (rentalDate < ? AND returnDate >= ?)) "
                        + "And (Car.quantity - orderDetail.amount < ?) "
                        + "And ([Order].status != 'Cancelled')) "
                        + "Order by createDate desc "
                        + "Offset ? Rows Fetch Next ? Rows Only ";
                ps = con.prepareStatement(sql);
                ps.setInt(1, amount);
                ps.setDate(2, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(3, new java.sql.Date(returnDate.getTime()));
                ps.setDate(4, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(5, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(6, new java.sql.Date(returnDate.getTime()));
                ps.setDate(7, new java.sql.Date(returnDate.getTime()));
                ps.setInt(8, amount);
                ps.setInt(9, offset);
                ps.setInt(10, fetch);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String carID = rs.getString(1);
                    String carName = rs.getString(2);
                    int seats = rs.getInt(3);
                    String transmission = rs.getString(4);
                    String color = rs.getString(5);
                    String year = rs.getString(6);
                    String category = rs.getString(7);
                    float price = rs.getFloat(8);
                    int quantity = rs.getInt(9);
                    CarDTO car = new CarDTO(carID, carName, seats, transmission, color, year, category, price, quantity);
                    if (listCar == null) {
                        listCar = new ArrayList();
                    }
                    listCar.add(car);
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

    public int noOfSearchCarRentalDateAndReturnDateRecord(Date rentalDate, Date returnDate, int amount) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select Count (carID) "
                        + "From Car "
                        + "Where quantity >= ? AND CarID NOT IN "
                        + "(Select Car.carID "
                        + "From Car "
                        + "Inner Join OrderDetail "
                        + "On Car.carID = OrderDetail.carID "
                        + "Inner join [Order] "
                        + "On OrderDetail.orderID = [Order].orderID "
                        + "Where ((? <= rentalDate AND ? >= rentalDate) "
                        + "OR (rentalDate <= ? AND returnDate >= ?) "
                        + "OR (rentalDate < ? AND returnDate >= ?)) "
                        + "And (Car.quantity - orderDetail.amount < ?) "
                        + "And ([Order].status != 'Cancelled')) ";
                ps = con.prepareStatement(sql);
                ps.setInt(1, amount);
                ps.setDate(2, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(3, new java.sql.Date(returnDate.getTime()));
                ps.setDate(4, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(5, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(6, new java.sql.Date(returnDate.getTime()));
                ps.setDate(7, new java.sql.Date(returnDate.getTime()));
                ps.setInt(8, amount);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int noOfRecords = rs.getInt(1);
                    return noOfRecords;
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

    public void searchCarByName(String name, Date rentalDate, Date returnDate, int amount, int offset, int fetch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select carID, carName, seats, transmission, color, year, category, price, quantity "
                        + "From Car "
                        + "Where quantity >= ? AND carname LIKE ? And CarID NOT IN "
                        + "(Select Car.carID "
                        + "From Car "
                        + "Inner Join OrderDetail "
                        + "On Car.carID = OrderDetail.carID "
                        + "Inner Join [Order] "
                        + "On OrderDetail.orderID = [Order].orderID "
                        + "Where ((? <= rentalDate AND ? >= rentalDate) "
                        + "OR (rentalDate <= ? AND returnDate >= ?) "
                        + "OR (rentalDate < ? AND returnDate >= ?)) "
                        + "And (Car.quantity - orderDetail.amount < ?) "
                        + "And ([Order].status != 'Cancelled')) "
                        + "Order by createDate desc "
                        + "Offset ? Rows Fetch Next ? Rows Only";
                ps = con.prepareStatement(sql);
                ps.setInt(1, amount);
                ps.setString(2, "%" + name + "%");
                ps.setDate(3, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(4, new java.sql.Date(returnDate.getTime()));
                ps.setDate(5, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(6, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(7, new java.sql.Date(returnDate.getTime()));
                ps.setDate(8, new java.sql.Date(returnDate.getTime()));
                ps.setInt(9, amount);
                ps.setInt(10, offset);
                ps.setInt(11, fetch);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String carID = rs.getString(1);
                    String carName = rs.getString(2);
                    int seats = rs.getInt(3);
                    String transmission = rs.getString(4);
                    String color = rs.getString(5);
                    String year = rs.getString(6);
                    String category = rs.getString(7);
                    float price = rs.getFloat(8);
                    int quantity = rs.getInt(9);
                    CarDTO car = new CarDTO(carID, carName, seats, transmission, color, year, category, price, quantity);
                    if (listCar == null) {
                        listCar = new ArrayList();
                    }
                    listCar.add(car);
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

    public int noOfSearchCarNameRecord(String name, Date rentalDate, Date returnDate, int amount) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select count (carID) "
                        + "From Car "
                        + "Where quantity >= ? AND carname LIKE ? And CarID NOT IN "
                        + "(Select Car.carID "
                        + "From Car "
                        + "Inner Join OrderDetail "
                        + "On Car.carID = OrderDetail.carID "
                        + "Inner Join [Order] "
                        + "On OrderDetail.orderID = [Order].orderID "
                        + "Where ((? <= rentalDate AND ? >= rentalDate) "
                        + "OR (rentalDate <= ? AND returnDate >= ?) "
                        + "OR (rentalDate < ? AND returnDate >= ?)) "
                        + "And (Car.quantity - orderDetail.amount < ?) "
                        + "And ([Order].status != 'Cancelled')) ";
                ps = con.prepareStatement(sql);
                ps.setInt(1, amount);
                ps.setString(2, "%" + name + "%");
                ps.setDate(3, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(4, new java.sql.Date(returnDate.getTime()));
                ps.setDate(5, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(6, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(7, new java.sql.Date(returnDate.getTime()));
                ps.setDate(8, new java.sql.Date(returnDate.getTime()));
                ps.setInt(9, amount);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int noOfRecords = rs.getInt(1);
                    return noOfRecords;
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

    public void searchCarByCategory(String categoryValue, Date rentalDate, Date returnDate, int amount, int offset, int fetch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select carID, carName, seats, transmission, color, year, category, price, quantity "
                        + "From Car "
                        + "Where quantity >= ? AND category = ? And CarID NOT IN "
                        + "(Select Car.carID "
                        + "From Car "
                        + "Inner Join OrderDetail "
                        + "On Car.carID = OrderDetail.carID "
                        + "Inner Join [Order] "
                        + "On OrderDetail.orderID = [Order].orderID "
                        + "Where ((? <= rentalDate AND ? >= rentalDate) "
                        + "OR (rentalDate <= ? AND returnDate >= ?) "
                        + "OR (rentalDate < ? AND returnDate >= ?)) "
                        + "And (Car.quantity - orderDetail.amount < ?) "
                        + "And ([Order].status != 'Cancelled')) "
                        + "Order by createDate desc "
                        + "Offset ? Rows Fetch Next ? Rows Only";
                ps = con.prepareStatement(sql);
                ps.setInt(1, amount);
                ps.setString(2, categoryValue);
                ps.setDate(3, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(4, new java.sql.Date(returnDate.getTime()));
                ps.setDate(5, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(6, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(7, new java.sql.Date(returnDate.getTime()));
                ps.setDate(8, new java.sql.Date(returnDate.getTime()));
                ps.setInt(9, amount);
                ps.setInt(10, offset);
                ps.setInt(11, fetch);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String carID = rs.getString(1);
                    String carName = rs.getString(2);
                    int seats = rs.getInt(3);
                    String transmission = rs.getString(4);
                    String color = rs.getString(5);
                    String year = rs.getString(6);
                    String category = rs.getString(7);
                    float price = rs.getFloat(8);
                    int quantity = rs.getInt(9);
                    CarDTO car = new CarDTO(carID, carName, seats, transmission, color, year, category, price, quantity);
                    if (listCar == null) {
                        listCar = new ArrayList();
                    }
                    listCar.add(car);
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

    public int noOfSearchCarCategoryRecord(String category, Date rentalDate, Date returnDate, int amount) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select count (carID) "
                        + "From Car "
                        + "Where quantity >= ? AND category = ? And CarID NOT IN "
                        + "(Select Car.carID "
                        + "From Car "
                        + "Inner Join OrderDetail "
                        + "On Car.carID = OrderDetail.carID "
                        + "Inner Join [Order] "
                        + "On OrderDetail.orderID = [Order].orderID "
                        + "Where ((? <= rentalDate AND ? >= rentalDate) "
                        + "OR (rentalDate <= ? AND returnDate >= ?) "
                        + "OR (rentalDate < ? AND returnDate >= ?)) "
                        + "And (Car.quantity - orderDetail.amount < ?) "
                        + "And ([Order].status != 'Cancelled')) ";
                ps = con.prepareStatement(sql);
                ps.setInt(1, amount);
                ps.setString(2, category);
                ps.setDate(3, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(4, new java.sql.Date(returnDate.getTime()));
                ps.setDate(5, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(6, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(7, new java.sql.Date(returnDate.getTime()));
                ps.setDate(8, new java.sql.Date(returnDate.getTime()));
                ps.setInt(9, amount);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int noOfRecords = rs.getInt(1);
                    return noOfRecords;
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

    public void searchCarByNameAndCategory(String nameValue, String categoryValue, Date rentalDate, Date returnDate, int amount, int offset, int fetch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select carID, carName, seats, transmission, color, year, category, price, quantity "
                        + "From Car "
                        + "Where quantity >= ? AND carName LIKE ? and category = ? And CarID NOT IN "
                        + "(Select Car.carID "
                        + "From Car "
                        + "Inner Join OrderDetail "
                        + "On Car.carID = OrderDetail.carID "
                        + "Inner Join [Order] "
                        + "On OrderDetail.orderID = [Order].orderID "
                        + "Where ((? <= rentalDate AND ? >= rentalDate) "
                        + "OR (rentalDate <= ? AND returnDate >= ?) "
                        + "OR (rentalDate < ? AND returnDate >= ?)) "
                        + "And (Car.quantity - orderDetail.amount < ?) "
                        + "And ([Order].status != 'Cancelled')) "
                        + "Order by createDate desc "
                        + "Offset ? Rows Fetch Next ? Rows Only";
                ps = con.prepareStatement(sql);
                ps.setInt(1, amount);
                ps.setString(2, "%" + nameValue + "%");
                ps.setString(3, categoryValue);
                ps.setDate(4, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(5, new java.sql.Date(returnDate.getTime()));
                ps.setDate(6, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(7, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(8, new java.sql.Date(returnDate.getTime()));
                ps.setDate(9, new java.sql.Date(returnDate.getTime()));
                ps.setInt(10, amount);
                ps.setInt(11, offset);
                ps.setInt(12, fetch);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String carID = rs.getString(1);
                    String carName = rs.getString(2);
                    int seats = rs.getInt(3);
                    String transmission = rs.getString(4);
                    String color = rs.getString(5);
                    String year = rs.getString(6);
                    String category = rs.getString(7);
                    float price = rs.getFloat(8);
                    int quantity = rs.getInt(9);
                    CarDTO car = new CarDTO(carID, carName, seats, transmission, color, year, category, price, quantity);
                    if (listCar == null) {
                        listCar = new ArrayList();
                    }
                    listCar.add(car);
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

    public int noOfSearchCarNameAndCategoryRecord(String name, String category, Date rentalDate, Date returnDate, int amount) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select count (carID) "
                        + "From Car "
                        + "Where quantity >= ? AND carName LIKE ? and category = ? And CarID NOT IN "
                        + "(Select Car.carID "
                        + "From Car "
                        + "Inner Join OrderDetail "
                        + "On Car.carID = OrderDetail.carID "
                        + "Inner Join [Order] "
                        + "On OrderDetail.orderID = [Order].orderID "
                        + "Where ((? <= rentalDate AND ? >= rentalDate) "
                        + "OR (rentalDate <= ? AND returnDate >= ?) "
                        + "OR (rentalDate < ? AND returnDate >= ?)) "
                        + "And (Car.quantity - orderDetail.amount < ?) "
                        + "And ([Order].status != 'Cancelled')) ";
                ps = con.prepareStatement(sql);
                ps.setInt(1, amount);
                ps.setString(2, "%" + name + "%");
                ps.setString(3, category);
                ps.setDate(4, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(5, new java.sql.Date(returnDate.getTime()));
                ps.setDate(6, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(7, new java.sql.Date(rentalDate.getTime()));
                ps.setDate(8, new java.sql.Date(returnDate.getTime()));
                ps.setDate(9, new java.sql.Date(returnDate.getTime()));
                ps.setInt(10, amount);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int noOfRecords = rs.getInt(1);
                    return noOfRecords;
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

    private List<String> listCategory;

    public List<String> getListCategory() {
        return this.listCategory;
    }

    public void getCategory() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select Distinct category "
                        + "From Car ";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String category = rs.getString(1);
                    if (listCategory == null) {
                        listCategory = new ArrayList();
                    }
                    listCategory.add(category);
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

    public int getQuantity(String carID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select quantity "
                        + "From Car "
                        + "Where carID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, carID);
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

    public CarDTO getCarByOrderDetailID(String orderDetailID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAccess.openConnection();
            if (con != null) {
                String sql = "Select Car.carID, carName, seats, transmission, color, year, category, car.price, quantity  "
                        + "From Car "
                        + "Inner Join OrderDetail "
                        + "On Car.carID = OrderDetail.CarID "
                        + "Where OrderDetailID = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, orderDetailID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String carID = rs.getString(1);
                    String carName = rs.getString(2);
                    int seats = rs.getInt(3);
                    String transmission = rs.getString(4);
                    String color = rs.getString(5);
                    String year = rs.getString(6);
                    String category = rs.getString(7);
                    float price = rs.getFloat(8);
                    int quantity = rs.getInt(9);
                    CarDTO car = new CarDTO(carID, carName, seats, transmission, color, year, category, price, quantity);
                    return car;
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
