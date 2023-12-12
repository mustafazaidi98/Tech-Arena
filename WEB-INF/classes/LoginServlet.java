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

public class LoginServlet extends HttpServlet {
   		String error_msg = null;
        String TOMCAT_HOME = System.getProperty("catalina.home");
    /** 
     * Initializes the servlet with some usernames/password
    */  

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    static Connection conn = null;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userrole = request.getParameter("userrole");
		if(MySQLDataStoreUtilities.CheckLogin(username, password, request))
        {
            HttpSession session = request.getSession(true);
            session.setAttribute("usertype",userrole);
            response.sendRedirect("ProductsServlet"); 
		    return;
        }
        error_msg ="Login Failure!, Username Or Password Incorrect";
        DisplayLogin(request,response);
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
    protected void DisplayLogin(HttpServletRequest request,HttpServletResponse response)
    throws ServletException, java.io.IOException {
        java.io.PrintWriter pw = response.getWriter();
        AppHelper helper = new AppHelper(request,pw);
        helper.printHtmltoResponse("Header.html");
        pw.println("<div id='page'>");
        helper.printHtmltoResponse("LeftNavigationBar.html");
        if(error_msg!=null){
            pw.print("<h4 style='color:red'>"+error_msg+"!</h4>");
        }
        helper.printHtmltoResponse("login.html");
        pw.println("</div>");
        helper.printHtmltoResponse("Footer.html");

    }
    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(true);
        String userRole = (String) session.getAttribute("usertype");
        if(userRole!=null){
            response.sendRedirect("ProductsServlet");
        }
        DisplayLogin(request,response);
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
