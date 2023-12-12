/*
 * LoginServlet.java
 *
 */
 
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;


@WebServlet("/RegisterServlet")

public class RegisterServlet extends HttpServlet {
    private String success_msg=null;
    private String error_msg=null;
    static Connection conn = null;
    String TOMCAT_HOME = System.getProperty("catalina.home");
    /** 
     * Initializes the servlet with some usernames/password
    */  
    public void init() {
                
    }
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        String fullname = request.getParameter("fullname");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String number = request.getParameter("number");
        String password = request.getParameter("password");
        String confirmpassword = request.getParameter("confirmpassword");
        String userrole = request.getParameter("userrole");
        if(!password.equals(confirmpassword))
		{
			error_msg = "Passwords doesn't match!";
		}
        else{
            if(!MySQLDataStoreUtilities.checkUsernameExists(username))
            {
                MySQLDataStoreUtilities.RegisterUser(fullname, username, confirmpassword, email, number, userrole);
            }
            else{
                    error_msg = "Username already exist";
                }
            success_msg = "Username Created";
            response.sendRedirect("LoginServlet");
        }
    }

    protected void DisplayRegistration(HttpServletRequest request,HttpServletResponse response)
    throws ServletException, java.io.IOException {
        java.io.PrintWriter pw = response.getWriter();
        AppHelper helper = new AppHelper(request,pw);
        helper.printHtmltoResponse("Header.html");
        pw.println("<div id=\"page\">");
        helper.printHtmltoResponse("LeftNavigationBar.html");
        if(error_msg!=null){
            pw.print("<h4 style='color:red'>"+error_msg+"!</h4>");
            error_msg=null;
        }
        if(success_msg!=null){
            pw.print("<h4 style='color:green'>"+success_msg+"!</h4>");
            success_msg=null;
        }
        pw.println("<div>");
        helper.printHtmltoResponse("Registration.html");
        helper.printHtmltoResponse("Footer.html");
    }
    /**
     * Actually shows the <code>HTML</code> result page
     */
    protected void showPage(HttpServletResponse response, String message)
    throws ServletException, java.io.IOException {
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Login Servlet Result</title>");  
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>" + message + "</h2>");
        out.println("</body>");
        out.println("</html>");
        out.close();
 
    }
    
    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        DisplayRegistration(request,response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    }
}
