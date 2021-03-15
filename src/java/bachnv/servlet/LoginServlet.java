/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.util.VerifyUtils;
import bachnv.user.UserDAO;
import bachnv.user.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String LOGIN_ACCOUNT_ERROR_PAGE = "loginAccountErrorPage";
    private final String LOGIN_RECAPTCHA_ERROR_PAGE = "loginReCaptchaErrorPage";
    private final String LOAD_CAR_SERVLET = "";
    private final String GENERATE_OTP_SERVLET = "generateOTP";

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

        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String url = LOGIN_ACCOUNT_ERROR_PAGE;

        try {

            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            if (VerifyUtils.verify(gRecaptchaResponse)) {
                UserDAO userDAO = new UserDAO();
                UserDTO user = userDAO.checkLogin(email, password);
                if (user != null) {
                    if ("cust".equalsIgnoreCase(user.getRole())) {
                        HttpSession session = request.getSession();
                        session.setAttribute("USER", user);
                        session.removeAttribute("CUSTCART");
                        if ("new".equalsIgnoreCase(user.getStatus())) {
                            url = GENERATE_OTP_SERVLET;
                        } else if ("active".equalsIgnoreCase(user.getStatus())) {
                            url = LOAD_CAR_SERVLET;
                        }
                    }
                }
            } else {
                url = LOGIN_RECAPTCHA_ERROR_PAGE;
            }

        } catch (SQLException ex) {
            log("Login _ SQL " + ex.getMessage());
        } catch (NamingException ex) {
            log("Login _ Naming " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
