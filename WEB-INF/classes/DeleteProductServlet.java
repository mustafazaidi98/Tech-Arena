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

@WebServlet("/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
   		String msg = null;
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
        String ProductName = request.getParameter("ProductName");
        HashMap<String, ProductDetails> hm=new HashMap<String, ProductDetails>();
        try
        {
              FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\csj\\ProductDetails.txt"));
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
             hm= (HashMap<String,ProductDetails>)objectInputStream.readObject();
             msg = "Product Map read";
        }
        catch(Exception e)
        {
        
        }
    if(hm.containsKey(ProductName))
    {
        msg = "Product Exists";
        hm.remove(ProductName);
        FileOutputStream fileOutputStream = new FileOutputStream(TOMCAT_HOME+"\\webapps\\csj\\ProductDetails.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(hm);
        objectOutputStream.flush();
        objectOutputStream.close();       
        fileOutputStream.close();
        msg = "Product Deleted";
    }
    DisplayProducts(request,response);
    } 
    /**
     * Actually shows the <code>HTML</code> result page
     */
    protected void DisplayProducts(HttpServletRequest request,HttpServletResponse response)
    throws ServletException, java.io.IOException {
        HashMap<String, ProductDetails> hm=new HashMap<String, ProductDetails>();
        FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\csj\\ProductDetails.txt"));
        try{
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
         hm= (HashMap<String,ProductDetails>)objectInputStream.readObject();
        }
        catch(Exception e){

        }
        int i = 1; int size= hm.size();
        java.io.PrintWriter pw = response.getWriter();
        AppHelper helper = new AppHelper(request,pw);
        helper.printHtmltoResponse("Header.html");
        pw.println("<div id='page'>");
        helper.printHtmltoResponse("LeftNavigationBar.html");
        if(msg!=null){
            pw.print("<h4 style='color:green'>"+msg+"!</h4>");
            msg = null;
        }
        helper.printHtmltoResponse("products.html");
        for(Map.Entry<String, ProductDetails> entry : hm.entrySet())
		{
			ProductDetails product = entry.getValue();
			if(i%3==1) pw.print("<tr>");
            pw.println("<td>");
            pw.println("<div class='itemcard'>");
            pw.println("<form method='post' action='/csj/DeleteProductServlet'>");
            pw.println("<img src='"+product.getImagePath()+"' alt='Product' style='width:100%'>");
            pw.println("<input type='hidden' name='ProductName' value='"+entry.getKey()+"'>");
            pw.println("<p class='price'>$"+product.getPrice()+"</p>");
            pw.println("<p>"+product.getcategory()+"</p>");
            pw.println("<p><input class='AddToCart' type='submit' value='Delete'></p>");
            pw.println("</form>");
            pw.println("</div>");
            pw.println("</td>");
            if(i%3==0 || i == size) pw.print("</tr>");
			i++;
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
        processRequest(request, response);
    }
}
