import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet("/Trending")

public class Trending extends HttpServlet {
	ArrayList <Bestrating> bestrated = new ArrayList <Bestrating> ();
    ArrayList <MostSold> mostsold = new ArrayList <MostSold> ();
    ArrayList <Mostsoldzip> mostsoldzip = new ArrayList <Mostsoldzip> ();
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		bestrated = MongoDataStoreUtilities.topProducts();
        mostsold = MongoDataStoreUtilities.mostsoldProducts();
        mostsoldzip = MongoDataStoreUtilities.mostsoldZip();
		String name = "Trending";
        AppHelper helper = new AppHelper(request,pw);
        helper.printHtmltoResponse("Header.html");
        pw.println("<div id='page'>");
        helper.printHtmltoResponse("LeftNavigationBar.html");
        pw.print("<h1>Length"+bestrated.size()+"</h1>");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Best Products</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		Iterator itr2 = bestrated.iterator();
        while(itr2.hasNext()) {
         Bestrating best = (Bestrating)itr2.next();
 		pw.print("<tr>");
		pw.print("<td>");
		pw.print(best.ProductName);
		pw.print("</td>");
		pw.print("<td>");
		pw.print(best.reviewRating);
		pw.print("</td>");
		pw.print("</tr>");
        }
		pw.print("</table></div></div></div>");	
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Most Sold Products</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		
         Iterator itr = mostsold.iterator();
        while(itr.hasNext()) {
         MostSold most = (MostSold)itr.next();
 		pw.println("<tr>");
		pw.println("<td border: 1px >");
		pw.println(most.productname);
		pw.println("</td>");
		pw.println("<td border: 1px >");
		pw.println(most.count);
		pw.println("</td>");
		pw.println("</tr>");
        }
		pw.print("</table></div></div></div>");

        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Most Sold Products by Zipcode</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		Iterator itr1 = mostsoldzip.iterator();
         while(itr1.hasNext()) {
         Mostsoldzip mostzip = (Mostsoldzip)itr1.next();
 		pw.print("<tr>");
		pw.println("<td border: 1px >");
		
		pw.println(mostzip.zipcode);
		pw.println("</td>");
		pw.println("<td border: 1px >");
		pw.println(mostzip.count);
		pw.println("</td>");
		pw.println("</tr>");
        }
		pw.print("</table></div></div></div>");	
		pw.print("</div>");
    helper.printHtmltoResponse("Footer.html");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
