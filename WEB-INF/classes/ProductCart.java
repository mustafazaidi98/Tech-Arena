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

public class ProductCart extends HttpServlet {
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
        HashMap<String, ProductDetails> hm=new HashMap<String, ProductDetails>();
        FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\csj\\ProductDetails.txt"));
        try{
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
         hm= (HashMap<String,ProductDetails>)objectInputStream.readObject();
        }
        catch(Exception e){

        }
        java.io.PrintWriter pw = response.getWriter();
        AppHelper helper = new AppHelper(request,pw);
        helper.printHtmltoResponse("Header.html");
        pw.println("<div id=\"page\">");
        helper.printHtmltoResponse("LeftNavigationBar.html");
        helper.printHtmltoResponse("productCart.html");
        HttpSession session = request.getSession(true);	
        int sum = 0;
        HashMap<String, ProductDetails> hmCart =(HashMap<String, ProductDetails>) session.getAttribute("currentCart");
        for(Map.Entry<String, ProductDetails> entry : hmCart.entrySet())
		{
			ProductDetails product = entry.getValue();
            pw.println("<tr>");
            pw.println("<td class=\"tdCart\">");
            pw.println("<div class=\"cart-info\">");
            pw.println("<img style=\"width: 80px;height: 80px; margin-right: 10px;\" src=\""+product.getImagePath()+"\" >");
            pw.println("<div>");
            pw.println("<p>"+product.getProductName()+"</p>");
            pw.println("</div>");
            pw.println("</div>");
            pw.println("</td>");
            pw.println("<td class=\"tdCart\">$"+product.getPrice()+"</td>");
            sum += Integer.parseInt(product.getPrice());
            pw.println("</tr>");
        }
        pw.println("</table>");
        pw.println("</div>");
        pw.println("<div class=\"total-price\">");
        pw.println("<table class=\"cart-table\">");
        pw.println("<tr>");
        pw.println("<td>SubTotal</td>");
        pw.println("<td>$"+sum+"</td>");
        pw.println("</tr>");
        pw.println("<tr>");
        pw.println("<td>Tax</td>");
        pw.println("<td>$35</td>");
        pw.println("</tr>");
        pw.println("<tr>");  
        pw.println("<td>Total</td>");
        sum = sum+35;
        session.setAttribute("total",sum);
        pw.println("<td>$"+sum+"</td>");
        pw.println("</tr>");
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
