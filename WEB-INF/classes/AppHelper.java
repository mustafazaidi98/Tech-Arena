
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


@WebServlet("/AppHelper")

/* 
	Home class uses the printHtml Function of Utilities class and prints the Header,LeftNavigationBar,
	Content,Footer of Game Speed Application.

*/

public class AppHelper extends HttpServlet {
	String TOMCAT_HOME = System.getProperty("catalina.home");
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session; 
	public AppHelper(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.session = req.getSession(true);
	}
	public void printHtmltoResponse(String filename)
	throws ServletException, java.io.IOException{
        String sCurrentLine;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(TOMCAT_HOME+"\\webapps\\csj\\"+filename));
        while ((sCurrentLine = bufferedReader.readLine()) != null) {
            pw.println(sCurrentLine);
        }
	}
}
