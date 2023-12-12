
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


@WebServlet("/Home")

/* 
	Home class uses the printHtml Function of Utilities class and prints the Header,LeftNavigationBar,
	Content,Footer of Game Speed Application.

*/

public class Home extends HttpServlet {
	String TOMCAT_HOME = System.getProperty("catalina.home");
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		AppHelper helper = new AppHelper(request,pw);
		helper.printHtmltoResponse("Header.html");
		helper.printHtmltoResponse("LeftNavigationBar.html");
		helper.printHtmltoResponse("Content.html");
		helper.printHtmltoResponse("Footer.html");	
	}
}
