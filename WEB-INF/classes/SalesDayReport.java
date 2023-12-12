import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class SalesDayReport extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("text/html");
        java.io.PrintWriter pw = response.getWriter();
        AppHelper helper = new AppHelper(request,pw);
        helper.printHtmltoResponse("Header.html");
        pw.println("<div id='page'>");
        helper.printHtmltoResponse("LeftNavigationBar.html"); 

		pw.println("<div id='body' style=\"background-color:white;width:100%;\">");
		pw.println("<section id='content' style=\"background-color:white;\">");
		pw.println("<article> <h1 align=\"center\">");
		pw.println("<span style='color:blue;'>"+"Sales Report</span></h1> ");
		pw.println("<table style='width:100%;height:100%;border:2px solid #000;'>");
		HashMap<String, Integer> hmap = MySQLDataStoreUtilities.GetDailySales();
        ArrayList<String> products = new ArrayList<String>(hmap.keySet());
		int j=1;
		if(products.isEmpty())
		{
			pw.println("<h1 style='background-color:green;color:white;'>Sorry! No Sale Found!</h1>");
		}
		else
		{
			pw.println("<tr style='background-color:green;color:white;border:2px solid #000'>");
			pw.println("<td>Index</td>");
			pw.println("<td>Date</td>");
			pw.println("<td>Total Sale ($)</td>");
			pw.println("</tr>");
			for(String item : products)
			{
				pw.println("<tr><td>"+j+"</td>");
				j++;
				pw.println("<td>"+item+"</td>");
				pw.println("<td>"+hmap.get(item)+"</td>");
				pw.println("</tr>");
			}
		}
		pw.println("</table>");
		pw.println("</article></section>");
        pw.println("</div>");
        helper.printHtmltoResponse("Footer.html");
	}

}