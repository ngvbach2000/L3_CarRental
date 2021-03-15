/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.servlet;

import bachnv.user.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ngvba
 */
@WebServlet(name = "GenerateOTPServlet", urlPatterns = {"/GenerateOTPServlet"})
public class GenerateOTPServlet extends HttpServlet {
    
    private final String VERIFY_EMAIL_PAGE = "verifyEmailPage";

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

        String url = VERIFY_EMAIL_PAGE;
        
        try {

            int randomPin = (int) (Math.random() * 9000) + 1000;
            String otp = String.valueOf(randomPin);
            HttpSession session = request.getSession();
            session.setAttribute("OTP", otp);

            Properties mailServerProperties;
            Session getMailSession;
            MimeMessage mailMessage;

            mailServerProperties = System.getProperties();
            mailServerProperties.put("mail.smtp.port", "587");
            mailServerProperties.put("mail.smtp.auth", "true");
            mailServerProperties.put("mail.smtp.starttls.enable", "true");

            getMailSession = Session.getDefaultInstance(mailServerProperties, null);
            mailMessage = new MimeMessage(getMailSession);

            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user != null) {
                mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

                mailMessage.setSubject("Rental Car Verification Code");
                mailMessage.setText("You have entered your email at Rental Car to register a new account. "
                        + "So we can get you started, please enter the otp code below on the Verify Email page: [" + otp + "]");

                Transport transport = getMailSession.getTransport("smtp");

                transport.connect("smtp.gmail.com", "rental.car.2222", "Abc123@@@");
                transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
                transport.close();
            }

        } catch (AddressException ex) {
            log("GenerateOTPServlet _ SQL " + ex.getMessage());
        } catch (MessagingException ex) {
            log("GenerateOTPServlet _ Naming " + ex.getMessage());
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
