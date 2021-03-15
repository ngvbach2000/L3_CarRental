/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.car.CarDAO;
import bachnv.car.CarDTO;
import bachnv.car.CarSearchError;
import bachnv.orderdetail.OrderDetailDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ngvba
 */
@WebServlet(name = "SearchCarServlet", urlPatterns = {"/SearchCarServlet"})
public class SearchCarServlet extends HttpServlet {

    public final String LOAD_CAR_SERVLET = "";
    public final String HOME_PAGE = "homePage";
    public final int RECORDS_PER_PAGE = 20;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String url = LOAD_CAR_SERVLET;
        String searchNameValue = request.getParameter("txtSearchNameValue");
        String searchCategoryValue = request.getParameter("txtSearchCategoryValue");
        String rentalDateValue = request.getParameter("txtRentalDate");
        String returnDateValue = request.getParameter("txtReturnDate");
        String amountValue = request.getParameter("txtAmount");

        CarSearchError errors = new CarSearchError();

        try {

            Date today = new Date();
            boolean error = false;
            if (rentalDateValue.isEmpty()) {
                errors.setRentalDateIsEmpty("Please input Rental Date!");
                error = true;
            }
            if (returnDateValue.isEmpty()) {
                errors.setReturnDateIsEmpty("Please input Return Date!");
                error = true;
            }
            if (amountValue.isEmpty()) {
                errors.setAmountLengthErr("Amount must be typing form 1 to 9999");
                error = true;
            } else {
                int amount = Integer.parseInt(amountValue);
                if (amount < 1 || amount > 9999) {
                    errors.setAmountLengthErr("Amount must be typing form 1 to 9999");
                    error = true;
                }
            }
            if (!rentalDateValue.isEmpty() && !returnDateValue.isEmpty()) {
                Date rentalDate = dateFormat.parse(rentalDateValue);
                Date returnDate = dateFormat.parse(returnDateValue);
                if (rentalDate.after(returnDate)) {
                    error = true;
                    errors.setRangeOfDateErr("Invalid Date! Rental date must be before Return date!");
                } else if (rentalDate.equals(returnDate)) {
                    error = true;
                    errors.setRangeOfDateErr("Invalid Date! Rental date must be before Return date!");
                } else if (rentalDate.before(today)) {
                    error = true;
                    errors.setRangeOfDateErr("Invalid Date! Rental date must be after today!");
                }
            }

            if (!error) {
                Date rentalDate = dateFormat.parse(rentalDateValue);
                Date returnDate = dateFormat.parse(returnDateValue);
                int amount = Integer.parseInt(amountValue);

                int page = 1;
                if (request.getParameter("page") != null) {
                    page = Integer.parseInt(request.getParameter("page"));
                }
                int noOfRecords = 0;
                CarDAO carDAO = new CarDAO();

                if (searchNameValue.isEmpty() && searchCategoryValue.isEmpty()) {
                    carDAO.searchCarByRentalDateAndReturnDate(rentalDate, returnDate, amount, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
                    noOfRecords = carDAO.noOfSearchCarRentalDateAndReturnDateRecord(rentalDate, returnDate, amount);
                } else if (!searchNameValue.isEmpty() && searchCategoryValue.isEmpty()) {
                    carDAO.searchCarByName(searchNameValue, rentalDate, returnDate, amount, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
                    noOfRecords = carDAO.noOfSearchCarNameRecord(searchNameValue, rentalDate, returnDate, amount);
                } else if (searchNameValue.isEmpty() && !searchCategoryValue.isEmpty()) {
                    carDAO.searchCarByCategory(searchCategoryValue, rentalDate, returnDate, amount, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
                    noOfRecords = carDAO.noOfSearchCarCategoryRecord(searchCategoryValue, rentalDate, returnDate, amount);
                } else {
                    carDAO.searchCarByNameAndCategory(searchNameValue, searchCategoryValue, rentalDate, returnDate, amount, (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
                    noOfRecords = carDAO.noOfSearchCarNameAndCategoryRecord(searchNameValue, searchCategoryValue, rentalDate, returnDate, amount);
                }

                List<CarDTO> listCar = carDAO.getListCar();
                OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                if (listCar != null) {
                    for (CarDTO car : listCar) {
                        int amountNow = orderDetailDAO.amountCarRentInDate(car.getCarID(), rentalDate, returnDate);
                        car.setQuantity(car.getQuantity() - amountNow);
                    }
                }

                request.setAttribute("LISTCAR", listCar);

                url = HOME_PAGE;

                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / RECORDS_PER_PAGE);
                request.setAttribute("NOOFPAGE", noOfPages);
                request.setAttribute("CURRENTPAGE", page);
            } else {
                request.setAttribute("SEARCHERR", errors);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            log("SearchCarServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("SearchCarServlet _ Naming " + ex.getMessage());
        } catch (ParseException ex) {
            log("SearchCarServlet _ Parse " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
