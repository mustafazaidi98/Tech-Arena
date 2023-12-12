import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.google.gson.Gson;

public class SalesJson extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("application/json");
		PrintWriter out =response.getWriter();
		Gson gson=new Gson();
        ArrayList<SaleDetails> invent = new ArrayList<SaleDetails>(); 
        HashMap<String, Integer> hmap = MySQLDataStoreUtilities.GetTotalSales();
        ArrayList<String> items = new ArrayList<String>(hmap.keySet()); 
		for(String item : items)
			{
                invent.add(new SaleDetails(item,hmap.get(item),Integer.parseInt(MySQLDataStoreUtilities.GetProductPrice(item))));
            }
		String result=gson.toJson(invent);
		out.println(result);
	}
}