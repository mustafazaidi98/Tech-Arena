import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.google.gson.Gson;

public class ProductInventoryJson extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("application/json");
		PrintWriter out =response.getWriter();
		Gson gson=new Gson();
        HashMap<String, ProductDetails> hmap = MySQLDataStoreUtilities.GetAllProducts();
        ArrayList<ProductDetails> items =new ArrayList<ProductDetails>(hmap.values());
        ArrayList<InventoryDetails> invent = new ArrayList<InventoryDetails>(); 
		for(ProductDetails item : items)
			{
                invent.add(new InventoryDetails(item.getProductName(),MySQLDataStoreUtilities.GetProductQuantity(item.getProductName())));
            }
		String result=gson.toJson(invent);
		out.println(result);
	}
}