import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class ProductSalesChart extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("text/html");
        HttpSession session=request.getSession();
        java.io.PrintWriter pw = response.getWriter();
        AppHelper helper = new AppHelper(request,pw);
        helper.printHtmltoResponse("Header.html");
		pw.println("<div id='body' style=\"background-color:white;width:100%;\">");
		pw.println("<section id='content' style=\"width:100%;background-color:white;\">");
		pw.println("<article> <h1 align=\"center\"><span style='color:red;'>"+"Sales Chart</span></h1> ");
		pw.println("<div style=\"margin-top:10px\" id=\"sales_barchart\" ></div>");
		pw.println("</article></section>");
    }
}