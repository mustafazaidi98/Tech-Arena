import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;
	//once the user clicks writereview button from products page he will be directed
 	//to write review page where he can provide reqview for item rating reviewtext	
	
public class WriteReviews extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException,IOException{
        response.setContentType("text/html");
        PrintWriter pw =response.getWriter();
		HttpSession session= request.getSession();
        AppHelper helper = new AppHelper(request,pw);
        String productName=request.getParameter("ProductName");
        ProductDetails product = MySQLDataStoreUtilities.GetProduct(productName);
        helper.printHtmltoResponse("Header.html");
        pw.println("<div id='page'>");
        helper.printHtmltoResponse("LeftNavigationBar.html");
        Date todaydate = new Date();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String d = df.format(todaydate);
        String username = (String) session.getAttribute("username");
        pw.println("<form action = 'WriteReviews' method='Post' style='background-color:lightgrey;'>");
        pw.println("<div style='text-align:left;margin-left: 300px;'>");
        pw.println("<img style=\"width:150px;height:200px;\"  src='"+product.getImagePath()+"' style=\"display:block;\" />");
        pw.println("<h3 style='color:black;'>"+product.getProductName()+"</h3>");
        pw.println("<b>Price : $</b>"+product.getPrice()+"<b/><br></br>");
        pw.println("<b>Manufacturer Name : </b>"+product.subcategory+"<br></br>");
        pw.println("<input type='hidden' name='ManufactorName' value='"+product.subcategory+"'>");
        pw.println("<input type='hidden' name='username' value='"+username+"'>");
        pw.println("<input type='hidden' name='pname' value='"+product.getProductName()+"'>");
        pw.println("<label style='text-align:left;'>Gender:</label>");
        pw.println("<input type='radio' name='gender' value='male'> Male  <input type='radio' name='gender' value='female'> Female ");
        pw.println("<br><label style='text-align:left;'><b>Age:</b></label><br>");
        pw.println("<input type='number' name='userage' value=''>");
        pw.println("<br><label style='text-align:left;'><b>zip code:</b></label><br>");
        pw.println("<input type='number' name='zipcode' value=''> <br>");
        pw.println("<label style='text-align:left;'><b>User occupation:</b></label><br>");
        pw.println("<input type='text' name='userOccupation' value=''>");
        pw.println("<input type='hidden' name='pcompany' value='"+product.subcategory+"'>");
        pw.println("<input type='hidden' name='pprice' value='"+product.getPrice()+"'>");
        pw.println("<input type='hidden' name='rdate' value='"+d+"'><br>");
        pw.println("<label style='text-align:left;' ><b>Write Review:</b></label><br><textarea style='width:60%;' placeholder='Enter Review Text' name='rtext' required ></textarea><br>");
        pw.println("<b>Today's Date : </b><b>"+d+"</b><br>");
        pw.println("<label><b>Rate This Product</b></label><br>");
        pw.println("<select style='width:60%;' name='rrating'><option value='1' selected>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option></select>");	
        pw.println("<input type='hidden' name='pCategory' value='"+product.getcategory()+"'>");
        pw.println("<input class=\"button\" type=\"submit\"style=' width:60%;background-color:green;padding:16px 21px;margin:8px;color: white;cursor: pointer;' name=\"action\" value=\"Submit Review\">");
        pw.println("</div>");
        pw.println("</form>");
        pw.println("</div>");

        helper.printHtmltoResponse("Footer.html");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException,IOException{
        String ProductName = request.getParameter("pname");
        String productCategory =  request.getParameter("pCategory");
        String productPrice = request.getParameter("pprice");
        String storeName = "TechArena";
        String productOnSale = "No";
        String manufactorName = request.getParameter("ManufactorName");
        String userName = request.getParameter("username");
        String userOccupation = request.getParameter("userOccupation");
        String userGender = request.getParameter("gender");
        String reviewRating =  request.getParameter("rrating");
        String reviewDate = request.getParameter("rdate");
        String reviewText = request.getParameter("rtext");
        String zipcode = request.getParameter("zipcode");
        java.io.PrintWriter pw = response.getWriter();
        MongoDataStoreUtilities.insertReview(ProductName, productCategory, productPrice, storeName, productOnSale, manufactorName, userName, userOccupation, userGender, reviewRating, reviewDate, reviewText,zipcode);
        response.sendRedirect("ProductsServlet");


    }
}