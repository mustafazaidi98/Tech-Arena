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

public class ProductsServlet extends HttpServlet {
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
    protected void DisplayProducts(HttpServletRequest request,HttpServletResponse response)
    throws ServletException, java.io.IOException {
        HashMap<String, ProductDetails> hm= MySQLDataStoreUtilities.GetAllProducts();
        int i = 1; int size= hm.size();
        java.io.PrintWriter pw = response.getWriter();
        AppHelper helper = new AppHelper(request,pw);
        String subcategory = request.getParameter("subcategory");
        String category = request.getParameter("category");
        helper.printHtmltoResponse("Header.html");
        pw.println("<div id='page'>");
        helper.printHtmltoResponse("LeftNavigationBar.html");
        helper.printHtmltoResponse("products.html");
        for(Map.Entry<String, ProductDetails> entry : hm.entrySet())
		{
			ProductDetails product = entry.getValue();
            if((category==null||category.equalsIgnoreCase(product.getcategory()))&&
            (subcategory==null||subcategory.equalsIgnoreCase("Other")||subcategory.equalsIgnoreCase(product.subcategory))){
			if(i%3==1) pw.print("<tr>");
            pw.println("<td>");
            pw.println("<div class='itemcard'>");
            pw.println("<form method='post' action='/csj/ProductsServlet'>");
            pw.println("<input style='display:none' type='hidden' name='ProductName' value='"+entry.getKey()+"'>");
            pw.println("<input style='display:none' type='hidden' name='ImagePath' value='"+product.getImagePath()+"'>");
            pw.println("<input style='display:none' type='hidden' name='Price' value='"+product.getPrice()+"'>");
            pw.println("<img src='"+product.getImagePath()+"' alt='Product' style='width:100%'>");
            pw.println("<h1>"+product.getProductName()+"</h1>");
            pw.println("<p class='price'>$"+product.getPrice()+"</p>");
            pw.println("<p>"+product.getdescription()+"</p>");
            pw.println("<p><input class='AddToCart' type='submit' value='Add to Cart'</p>");
            pw.println("</form>");
            pw.println("<form method='get' action='/csj/WriteReviews'>");
            pw.println("<input style='display:none' type='hidden' name='ProductName' value='"+entry.getKey()+"'>");
            pw.println("<p><input class='AddToCart' type='submit' value='Write Review'</p>");
            pw.println("</form>");
            pw.println("<form method='get' action='/csj/ShowReview'>");
            pw.println("<input style='display:none' type='hidden' name='name' value='"+entry.getKey()+"'>");
            pw.println("<p><input class='AddToCart' type='submit' value='View Reviews'</p>");
            pw.println("</form>");
            pw.println("</div>");
            pw.println("</td>");
            if(i%3==0 || i == size) pw.print("</tr>");
			i++;
            }
        }
        pw.println("</table>");
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
        DisplayProducts(request,response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        HttpSession session = request.getSession(true);	
        if (session.getAttribute("username")==null)
        {
        session.setAttribute("login_msg", "Please Login to add items to cart");
        response.sendRedirect("LoginServlet");
        }
        else{
            String ProductName = request.getParameter("ProductName");
            String Price = request.getParameter("Price");
            String ImagePath = request.getParameter("ImagePath");
            ProductDetails productDetails = new ProductDetails(ProductName,Price,ImagePath);
            HashMap<String, ProductDetails> hmCart=new HashMap<String, ProductDetails>();
            if (session.getAttribute("currentCart")==null)
            {
                hmCart.put(ProductName,productDetails);
                session.setAttribute("currentCart", hmCart);
            }
            else{
                hmCart =(HashMap<String, ProductDetails>) session.getAttribute("currentCart");
                hmCart.put(ProductName,productDetails);
            }
        response.sendRedirect("ProductsServlet");
        }
    }
}
