import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.io.*;

public class CheckOut extends HttpServlet {
   		String error_msg = null;
        String TOMCAT_HOME = System.getProperty("catalina.home");
    /** 
     * Initializes the servlet with some usernames/password
    */  

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    
    protected void DisplayCheckout(HttpServletRequest request,HttpServletResponse response)
    throws ServletException, java.io.IOException {
        java.io.PrintWriter pw = response.getWriter();
        AppHelper helper = new AppHelper(request,pw);
        helper.printHtmltoResponse("Header.html");
        pw.println("<div id='page'>");
        helper.printHtmltoResponse("LeftNavigationBar.html");
        helper.printHtmltoResponse("CheckOut.html");
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
        DisplayCheckout(request,response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        HttpSession session = request.getSession(true);	
        HashMap<String, ProductDetails> Products =(HashMap<String, ProductDetails>) session.getAttribute("currentCart");
        String total = String.valueOf(session.getAttribute("total"));
        String firstname = request.getParameter("firstname");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String cardname = request.getParameter("cardname");
        String expmonth = request.getParameter("expmonth");
        String expyear = request.getParameter("expyear");
        String cvv = request.getParameter("cvv");
        String Deliver = request.getParameter("Deliver");
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String Date = dateFormat.format(date);
        HashMap<Integer, Order> orders=new HashMap<Integer, Order>();
        orders = MySQLDataStoreUtilities.GetAllOrders();
        int id = orders.size();
        String username =(String) session.getAttribute("username");
        Order order = new Order(id,username, firstname, email, address, city, state, zip, cardname,
         expmonth, expyear, cvv, Deliver,Date, Products);
         order.total = total;
        MySQLDataStoreUtilities.AddOrder(order);
        MySQLDataStoreUtilities.AddOrderProduct(order);
        orders.put(id, order);
        java.io.PrintWriter pw = response.getWriter();
        AppHelper helper = new AppHelper(request,pw);
        helper.printHtmltoResponse("Header.html");
        pw.println("<div id='page'>");
        helper.printHtmltoResponse("LeftNavigationBar.html");
        pw.println("<h2> Order Placed! </h2>");
        pw.println("</div>");
        helper.printHtmltoResponse("Footer.html");
    }
}
