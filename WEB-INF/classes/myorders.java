/*
 * ProductServlet.java
 *
 */
 

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

public class myorders extends HttpServlet {
   		String error_msg = null;
        String TOMCAT_HOME = System.getProperty("catalina.home");
    /** 
     * Initializes the servlet with some usernames/password
    */  

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {

    } 
    /**
     * Actually shows the <code>HTML</code> result page
     */
    protected void DisplayCart(HttpServletRequest request,HttpServletResponse response)
    throws ServletException, java.io.IOException {
        HashMap<Integer, Order> orders= MySQLDataStoreUtilities.GetAllOrders();
        java.io.PrintWriter pw = response.getWriter();
        AppHelper helper = new AppHelper(request,pw);
        helper.printHtmltoResponse("Header.html");
        pw.println("<div id=\"page\">");
        helper.printHtmltoResponse("LeftNavigationBar.html");
        helper.printHtmltoResponse("myorders.html");
        HttpSession session = request.getSession(true);
        int sum = 0;
        for(Map.Entry<Integer, Order> entry : orders.entrySet())
		{
			Order product = entry.getValue();
            String username = (String)session.getAttribute("username");
            String usertype = (String)session.getAttribute("usertype");
            if(username.compareToIgnoreCase(product.username)==0 ||
            usertype.compareToIgnoreCase("salesmen")==0
            )
            pw.println("<tr>");
            pw.println("<td class=\"tdCart\">");
            pw.println("<p>"+product.id+"</p>");
            pw.println("</div>");
            pw.println("</div>");
            pw.println("</td>");
            pw.println("<td class=\"tdCart\">"+product.Date+"</td>");
            pw.println("<td class=\"tdCart\">"+product.Products.size()+"</td>");
            pw.println("<td class=\"tdCart\">"+product.Deliver+"</td>");
            pw.println("</tr>");
        }
        pw.println("</table>");
        pw.println("</div>");
        pw.println("</div>");
        pw.println("<div class='CheckoutBtn'><input type='submit' value='Checkout'></div>");
        pw.println("</form>");
        helper.printHtmltoResponse("Footer.html");
    }
    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        response.setContentType("text/html");
        DisplayCart(request,response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
}
}
