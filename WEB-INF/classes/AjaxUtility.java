import java.sql.*;
import java.util.*;
import java.io.*;

public class AjaxUtility
{
	Connection conn = null;
	
	Connection con = null;
	public boolean connect()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/webapp1","root","root");
			if(con.isClosed() || con==null){
				return false;
			}
			else
			{
				return true;
			}
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			return false;
		}
	}
	
	public HashMap getAllItems()
	{
		Item item = null;
		HashMap<String,Item> items = new HashMap<>();
		try
		{	
			PreparedStatement ps =  con.prepareStatement("select * from ProductsDetails");
			ResultSet rs = ps.executeQuery();

			while(rs.next())
			{
				item = new Item();
				item.setPrice((float)rs.getDouble("pprice"));
				item.setName(rs.getString("pname"));
				item.setRetailer(rs.getString("rname"));
				item.setCompany(rs.getString("pcompany"));
				item.setColor(rs.getString("pcolor"));
				item.setCondition(rs.getString("pcondition"));
				item.setId(rs.getInt("pid")+"");
				item.setDescription(rs.getString("pdescription"));
				item.setImage(rs.getString("pimage"));
				item.setCategory(rs.getString("pcategory"));
				items.put(rs.getString("pname"),item);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return items;
	}
	
}	