import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
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

public class SaleProducts extends HttpServlet {
   		String error_msg = null;
        String TOMCAT_HOME = System.getProperty("catalina.home");
    /** 
     * Initializes the servlet with some usernames/password
    */  

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    
    protected void DisplaySales(HttpServletRequest request,HttpServletResponse response)
    throws ServletException, java.io.IOException {
        java.io.PrintWriter pw = response.getWriter();
        AppHelper helper = new AppHelper(request,pw);
        helper.printHtmltoResponse("Header.html");
        pw.println("<div id='page'>");
        helper.printHtmltoResponse("LeftNavigationBar.html");
        pw.println("<div id='body' style=\"background-color:white;width:100%;\">");
		pw.println("<section id='content' style=\"background-color:white;\">");
		pw.println("<article> <h1 align=\"center\">");
		pw.println("<span style='color:blue;'>"+"Product On Sales</span></h1> ");
		pw.println("<table style='width:100%; height:100% border:2px solid #000;'>");
        ArrayList<String> items = MySQLDataStoreUtilities.GetSaleDetails();
		int j=1;
		if(items.isEmpty())
		{
			pw.println("<h1 style='background-color:green;color:white;'>Sorry! No Products Found!</h1>");
		}
		else
		{
			pw.println("<tr style='background-color:green;color:white;border:2px solid #000'>");
			pw.println("<td>Index</td>");
			pw.println("<td>Name</td>");
			pw.println("</tr>");
			for(String item : items)
			{
				pw.println("<tr><td>"+j+"</td>");
				j++;
				pw.println("<td>"+item+"</td>");
				pw.println("</tr>");
			}
		}
		pw.println("</table>");
		pw.println("</article></section>");
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
        DisplaySales(request,response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
}
