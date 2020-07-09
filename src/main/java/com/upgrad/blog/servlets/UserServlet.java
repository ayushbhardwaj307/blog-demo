//*******************DONE***********************
package com.upgrad.blog.servlets;

import com.upgrad.blog.dao.DAOFactory;
import com.upgrad.blog.dto.UserDTO;
import com.upgrad.blog.exceptions.EmailNotValidException;
import com.upgrad.blog.util.EmailValidator;
//import com.upgrad.blog.exceptions.EmailNotValidException;
//import com.upgrad.blog.util.EmailValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * TODO: 4.4. Modify the class definition to make it a Servlet class.
 * TODO: 4.5. Override doPost() method from the base Class.
 * TODO: 4.6. Retrieve the values of form attributes defined in the index.jsp file
 * TODO: 4.7. Check if password is empty or null. If empty or null, then redirect to
 * the index.jsp file with an error message "Password is a required field".
 * (Hint: Store the error message as an attribute inside request object before redirecting
 * to the index.jsp. This error message will be displayed in the index.jsp page when this
 * error arises.)
 * TODO: 4.8. If Sign In button is clicked, print "User Signed In" with the user
 * details on the console. Also, store the email id in the session object.
 * TODO: 4.9. If Sign Up button is clicked, then print "User Signed Up"
 * with the user details on the console. Also, store the email id in the session object.
 * TODO: 4.10. Check if the user is logged in or not. If yes, then redirect them
 * to the Home.jsp file. (Hint: Make use of the email id stored in the session object)
 */

/**
 * TODO: 5.4. Validate the email id that is retrieved from the request object using the
 * EmailValidator class. If the email is not valid, then redirect the user to the Sign In/
 * Sign Up page with the error message that is stored in the EmailNotValidException. This error
 * message should be displayed on the index.jsp page.
 * TODO: 5.5. Map this Servlet to "/blog/user" url using the @WebServlet annotation.
 * TODO: 5.6: Remove the same mapping from the Deployment Descriptor.
 */

/**
 * TODO: 6.10. When the user click on the Sign In button on the Sign In/ Sign Up page, handle the
 * following scenarios. (Hint: Use DAOFactory to get UserDAO)
 * 1. If the user's email is not found in the database, display "No user registered with the given email address!"
 * message on the Sign In/ Sign Up page.
 * 2. If the user's email is registered but the password is incorrect, display "Please enter valid credentials"
 * message on the Sign In/ Sign Up page.
 * 3. If the user's credentials are correct, then redirect the user to the Home.jsp page.
 *
 * TODO: 6.11. When the user click on the Sign Up button on the Sign In/ Sign Up page, handle the
 * following scenarios (Hint: Use DAOFactory to get UserDAO)
 * 1. If the user's email is already registered on the database, display
 * "A user with this email address already exists!" message on the Sign In/ Sign Up page.
 * 2. If the user's email is unregistered, then store the user's details in the database and
 * redirect the user to the Home.jsp page.
 */
@WebServlet("/blog/user")
public class UserServlet extends HttpServlet {


    private DAOFactory daoFactory;

    public UserServlet(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out=resp.getWriter();
        String emailId= req.getParameter("emailId");
        String password=req.getParameter("password");
        String actionType= req.getParameter("actionType");
        HttpSession httpSession=req.getSession();
        UserDTO userDTO = new UserDTO();
        userDTO.setEmailId(emailId);
        userDTO.setPassword(password);
        RequestDispatcher rd;
        EmailValidator em = new EmailValidator();
        try {
            em.isValidEmail(emailId);
        } catch (EmailNotValidException ex) {
            req.setAttribute("isError", true);
            req.setAttribute("errorMessage", ex.getMessage());
            rd = req.getRequestDispatcher("/index.jsp");
            rd.forward(req, resp);
        }

        try {

            UserDTO userDTO2 = daoFactory.getUserCRUDS().findByEmail(emailId);

            if (userDTO2.getEmailId() != null) {
                // User already exist, set error and send back
                req.setAttribute("isError", true);
                req.setAttribute("errorMessage", "A user with this email address already exists!");
                rd = req.getRequestDispatcher("/index.jsp");
                rd.forward(req, resp);
            }
            // Set session to identify user is loggedin
            else {
                daoFactory.getUserCRUDS().create(userDTO);
                httpSession.setAttribute("uemailId", emailId);
                resp.sendRedirect(req.getContextPath() + "/Home.jsp");
            }
        } catch (SQLException e) {
            req.setAttribute("isError", true);
            req.setAttribute("errorMessage", "Some unexpected error occured!");
            rd = req.getRequestDispatcher("/index.jsp");
            rd.forward(req, resp);
        }
        if (actionType.toUpperCase().equals("SIGN IN")) {
            try {
                if (userDTO.getEmailId() == null) {
                    req.setAttribute("isError", true);
                    req.setAttribute("errorMessage", "No user registered with the given email address!");
                    rd = req.getRequestDispatcher("/index.jsp");
                    rd.forward(req, resp);
                } else if (userDTO.getPassword().equals(password)) {
                    // Set session variable to check if user is loggedin
                    httpSession.setAttribute("uemailId", emailId);
                    resp.sendRedirect(req.getContextPath() + "/Home.jsp");
                } else {
                    req.setAttribute("isError", true);
                    req.setAttribute("errorMessage", "Please enter valid credentials");
                    rd = req.getRequestDispatcher("/index.jsp");
                    rd.forward(req, resp);
                }
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
//            } catch (SQLException e) {
//                req.setAttribute("isError", true);
//                req.setAttribute("errorMessage", "Some unexpected error occured!");
//                rd = req.getRequestDispatcher("/index.jsp");
//                rd.forward(req, resp);
//            }
            try {
                if (httpSession.getAttribute("uemailId") != null) {
                    resp.sendRedirect(req.getContextPath() + "/Home.jsp");
                }
            } catch (NullPointerException e) {
                // Means user is not Signed in and can access the servlet
            }
        }
    }
}
