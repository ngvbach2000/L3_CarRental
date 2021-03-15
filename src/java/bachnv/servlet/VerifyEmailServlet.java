/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.user.UserDAO;
import bachnv.user.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ngvba
 */
@WebServlet(name = "VerifyEmailServlet", urlPatterns = {"/VerifyEmailServlet"})
public class VerifyEmailServlet extends HttpServlet {

    private final String VERIFY_EMAIL_PAGE = "verifyEmailPage";
    private final String GENERATE_OTP_SERVLET = "generateOTP";
    private final String LOAD_CAR_SERVLET = "";

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

        String otp = request.getParameter("txtOTP");
        String url = VERIFY_EMAIL_PAGE;
        boolean error = false;

        try {
            HttpSession session = request.getSession();
            String otpGenerate = (String) session.getAttribute("OTP");
            if (otpGenerate != null) {
                if (otp.equalsIgnoreCase(otpGenerate)) {
                    UserDTO user = (UserDTO) session.getAttribute("USER");
                    if (user != null) {
                        UserDAO userDAO = new UserDAO();
                        userDAO.updateStatusToActive(user.getEmail());
                        user.setStatus("active");
                        session.setAttribute("USER", user);
                        url = LOAD_CAR_SERVLET;
                    }
                } else {
                    error = true;
                    request.setAttribute("OTPERR", "OTP not match! Please try again!");
                    url = GENERATE_OTP_SERVLET;
                }
            }
        } catch (SQLException ex) {
            log("VerifyEmailServlet _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("VerifyEmailServlet _ Naming " + ex.getMessage());
        } finally {
            if (error) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                response.sendRedirect(url);
            }
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
