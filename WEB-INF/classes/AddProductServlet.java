/*
 * LoginServlet.java
 *
 */
 

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


@WebServlet("/AddProductServlet")

public class AddProductServlet extends HttpServlet {
    private String error_msg=null;
    private String msg=null;
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
        String description = request.getParameter("description");
        String ProductName = request.getParameter("ProductName");
        String Price = request.getParameter("Price");
        String ImagePath = request.getParameter("ImagePath");
        String category = request.getParameter("category");
        String subcategory = request.getParameter("subcategory");
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
        boolean rebate = request.getParameter("rebate")!="false"||request.getParameter("rebate")==null;
        boolean onsale = request.getParameter("onsale")!="false"||request.getParameter("onsale")==null;
        HashMap<String, ProductDetails> hm= MySQLDataStoreUtilities.GetAllProducts();;
        if(hm.containsKey(ProductName))
            { 
                if(MySQLDataStoreUtilities.AddInventoryDetails(ProductName,quantity,rebate,onsale)){
                    msg = "Product added";
                }
                else{
                    msg = "Product Error";
                }
            }
        else{
				ProductDetails productDetails = new ProductDetails(description,ProductName,Price,ImagePath,category,subcategory);
				MySQLDataStoreUtilities.AddProduct(productDetails);
                MySQLDataStoreUtilities.AddInventoryDetails(ProductName,quantity,rebate,onsale);
                msg = "Product added";
            }
            DisplayAddProduct(request,response);

        }

    protected void DisplayAddProduct(HttpServletRequest request,HttpServletResponse response)
    throws ServletException, java.io.IOException {
        HttpSession session = request.getSession(true);
        String usertype = (String)session.getAttribute("usertype");
        if(usertype.compareToIgnoreCase("retailer")!=0)
        {response.sendRedirect("ProductsServlet");}
        java.io.PrintWriter pw = response.getWriter();
        AppHelper helper = new AppHelper(request,pw);
        helper.printHtmltoResponse("Header.html");
        pw.println("<div id='page'>");
        helper.printHtmltoResponse("LeftNavigationBar.html");
        if(error_msg!=null){
            pw.print("<h4 style='color:red'>"+error_msg+"!</h4>");
            error_msg = null;
        }if(msg!=null){
            pw.print("<h4 style='color:green'>"+msg+"!</h4>");
            msg = null;
        }
        helper.printHtmltoResponse("AddProduct.html");
        pw.println("</div>");
        helper.printHtmltoResponse("Footer.html");
    }
    /**
     * Actually shows the <code>HTML</code> result page
     */
    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        HttpSession session = request.getSession(true);
            DisplayAddProduct(request,response);
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
