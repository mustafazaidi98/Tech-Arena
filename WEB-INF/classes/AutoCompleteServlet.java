import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;
import java.io.*;

public class AutoCompleteServlet extends HttpServlet {

String query = null,searchValue =null;
public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		StringBuffer sb = new StringBuffer();
		boolean found = false;
		AjaxUtility ajaxUtil = new AjaxUtility();
		ajaxUtil.connect();
		HashMap<String,Item> items = ajaxUtil.getAllItems(); 
		PrintWriter out =response.getWriter();
		query = request.getParameter("query");
        searchValue = request.getParameter("search_query");
	
		if(!searchValue.equals(""))
		{
			if(query.equals("display"))
			{
				Item item = (Item)items.get(searchValue);
				request.setAttribute("itemObj", items.get(searchValue));
				RequestDispatcher dis = request.getRequestDispatcher("/DisplayResultPage");  
				dis.forward(request, response);  
			}
			if(query.equals("find"))
			{
				searchValue=searchValue.trim().toLowerCase();
				for(Map.Entry<String,Item> it : items.entrySet())
				{
					Item item=(Item)it.getValue();
					if (item.getName().toLowerCase().startsWith(searchValue))
					{
						sb.append("<item>");
                        sb.append("<itemId>" + item.getId() + "</itemId>");
                        sb.append("<itemName>" + item.getName() + "</itemName>");
                        sb.append("</item>");
						found=true;
					}
				}	
				
				if(!found)
				{
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				}		
				else 
				{
					response.setContentType("text/xml");
					out.write("<items>");
					out.write(sb.toString());
					out.write("</items>");
				}
			}
			
		}
	}
}