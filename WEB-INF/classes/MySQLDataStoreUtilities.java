import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import javax.servlet.http.HttpSession;
public class MySQLDataStoreUtilities
{
static Connection conn = null;
public static void getConnection()
{
try
{
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TechArena","root","root");							
}
catch(Exception e)
{}
}
public static boolean CheckLogin(String username,String password,HttpServletRequest request){
    try{
	getConnection();
	Statement stmt=conn.createStatement();
	String selectCustomerQuery="select * from  UserLogin where username = ?;";
	PreparedStatement pst = conn.prepareStatement(selectCustomerQuery);
	pst.setString(1,username);
	ResultSet rs = pst.executeQuery();
	if(rs.next())
	{	
		UserDetails user = new UserDetails(rs.getString("username"),rs.getString("password"),rs.getString("userrole"));
		if(user.getPassword().compareToIgnoreCase(password)==0)
		{
			HttpSession session = request.getSession(true);
			session.setAttribute("username", user.getUsername());
			session.setAttribute("usertype", user.getuserrole());
			return true;
		}
	}
}
catch(Exception e){}
	return false;
}
public static void RegisterUser(String fullname, String username, String password, String email,String number,String userrole){
    try{
    getConnection();
    String insertIntoCustomerOrderQuery = "INSERT INTO UserLogin(username,password,fullname,email,pnumber,userrole) "
    + "VALUES (?,?,?,?,?,?);";	
        
    PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
    pst.setString(1,username);
    pst.setString(2,password);
    pst.setString(3,fullname);
    pst.setString(4,email);
    pst.setString(5,number);
    pst.setString(6,userrole);
    pst.execute();
}
    catch(Exception e){}
}

public static void AddProduct(ProductDetails product){
	try
	{
	
		getConnection();
		String insertIntoCustomerOrderQuery = "INSERT INTO ProductDetails(ProductName,description,Price,ImagePath,category,subcategory) "
		+ "VALUES (?,?,?,?,?,?);";	
			
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
		//set the parameter for each column and execute the prepared statement
		pst.setString(1,product.getProductName());
		pst.setString(2,product.getdescription());
		pst.setString(3,product.getPrice());
		pst.setString(4,product.getImagePath());
		pst.setString(5,product.getcategory());
		pst.setString(6,product.subcategory);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}	
        
}
public static void AddOrder(Order order){
	try
	{
	
		getConnection();
		String insertIntoCustomerOrderQuery = "INSERT INTO OrderDetails"+
        "(id, username , firstname ,email ,address ,"+
        "city ,state ,zip ,cardname ,expmonth ,"+
        "expyear ,cvv ,Deliver ,Date, total) "
		+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";	
			
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
		pst.setInt(1,order.id);
		pst.setString(2,order.username);
		pst.setString(3,order.firstname);
		pst.setString(4,order.email);
		pst.setString(5,order.address);
		pst.setString(6,order.city);
        pst.setString(7,order.state);
		pst.setString(8,order.zip);
		pst.setString(9,order.cardname);
		pst.setString(10,order.expmonth);
		pst.setString(11,order.expyear);
		pst.setString(12,order.cvv);
        pst.setString(13,order.Deliver);
		pst.setString(14,order.Date);
		pst.setString(15,order.total);
		pst.execute();
	}
	catch(Exception e)
	{
	
	}	
        
}
public static void AddOrderProduct(Order order){
    String insertIntoCustomerOrderQuery =null;
    for(Map.Entry<String, ProductDetails> entry : order.Products.entrySet()){
        try{
            getConnection();
            insertIntoCustomerOrderQuery = "INSERT INTO OrderProducts(id,ProductName) "
            + "VALUES (?,?);";	
                
            PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
            //set the parameter for each column and execute the prepared statement
            pst.setInt(1,order.id);
            pst.setString(2,entry.getKey());
            pst.execute();
			ManageInventoryProduct(entry.getKey());
        }
        catch(Exception e){}
    }
}
public static void ManageInventoryProduct(String ProductName){
	try{
		getConnection();
		PreparedStatement pss =  conn.prepareStatement("select * from ProductInventory where ProductName=?");
		pss.setString(1,ProductName);
		ResultSet rss = pss.executeQuery();
		int q=0;
		if(rss.next())
		{	
			q = rss.getInt("Quantity") - 1;
		}	

		PreparedStatement ps = conn.prepareStatement("update ProductInventory set quantity=? where ProductName = ?");
		ps.setInt(1,q);
		ps.setString(2,ProductName);
		ps.execute();
	}
	catch(Exception e){}
}
public static HashMap<Integer,Order> GetAllOrders(){
    try
	{					
		getConnection();
        //select the table 
        HashMap<Integer,Order> orders = new HashMap<Integer,Order>();
        Order order = null;
		String selectOrderQuery ="select * from OrderDetails;";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			order=new Order(rs.getInt("id"),rs.getString("username"),rs.getString("firstname"),
            rs.getString("email"),rs.getString("address"),
            rs.getString("city"),rs.getString("state"),rs.getString("zip"),
            rs.getString("cardname"),rs.getString("expmonth"),
            rs.getString("expyear"),rs.getString("cvv") ,rs.getString("Deliver"),rs.getString("Date"),null);
			order.Products = GetOrderProducts(rs.getInt("id"));
            orders.put(rs.getInt("id"), order);
		}
        return orders;
	}
	catch(Exception e)
	{
		
	}
return null;
}
public static HashMap<String, ProductDetails> GetOrderProducts(int orderID){
    ArrayList<String> products = new ArrayList<String>();
    HashMap<String, ProductDetails> pds =  new HashMap<String, ProductDetails>();
    try
	{					
		getConnection();
        //select the table 
		String selectOrderQuery ="select * from OrderProducts where id = ?;";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
        pst.setInt(1, orderID);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			products.add(rs.getString("ProductName"));		
		}
        for(int i=0;i<products.size();i++){
            pds.put(products.get(i),GetProduct(products.get(i)));
        }
        return pds;
	}
	catch(Exception e)
	{
		
	}
    return null;
}
public static ProductDetails GetProduct(String name){
    try
	{					
		getConnection(); 
		String selectOrderQuery ="select * from ProductDetails where ProductName Like ?;";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
        pst.setString(1,name);
		ResultSet rs = pst.executeQuery();	
		ProductDetails product= null;
		while(rs.next())
		{	
				product=new ProductDetails( rs.getString("description"),
                rs.getString("ProductName"),rs.getString("Price"),
                rs.getString("ImagePath"),rs.getString("category"),
                rs.getString("subcategory"));
				return product;
		}
	}
	catch(Exception e)
	{
		
	}
	return null;
}
public static HashMap<String, ProductDetails> GetAllProducts()
{	

	HashMap<String, ProductDetails> products =new HashMap<String, ProductDetails>();
		
	try
	{					

		getConnection();
        //select the table 
		String selectOrderQuery ="select * from ProductDetails;";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();	
		ProductDetails product= null;
		while(rs.next())
		{
			if(!products.containsKey(rs.getString("ProductName")))
			{	
				product=new ProductDetails( rs.getString("description"),
                rs.getString("ProductName"),rs.getString("Price"),
                rs.getString("ImagePath"),rs.getString("category"),
                rs.getString("subcategory"));
				products.put(rs.getString("ProductName"), product);
			}		
		}
	}
	catch(Exception e)
	{
		
	}
	return products;
}

public static boolean checkUsernameExists(String username){
    try{
        getConnection();
        Statement stmt=conn.createStatement();
        String selectCustomerQuery="select * from  UserLogin where username = ?;";
        PreparedStatement pst = conn.prepareStatement(selectCustomerQuery);
        pst.setString(1,username);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            return true;
        }}
        catch(Exception e){}
        return false;
}
public static boolean GetInventoryDetails(String ProductName){
    try{
        getConnection();
        Statement stmt=conn.createStatement();
        String selectCustomerQuery="select * from ProductInventory where ProductName LIKE "+ProductName;
		PreparedStatement pst = conn.prepareStatement(selectCustomerQuery);
		
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            return true;
        }}
        catch(Exception e){}
        return false;
}
public static ArrayList<String> GetRebateDetails(){
    try{
        getConnection();
        Statement stmt=conn.createStatement();
		ArrayList<String> products = new ArrayList<String>();
        String selectCustomerQuery="select ProductName from ProductInventory where Rebate = 1;";
		PreparedStatement pst = conn.prepareStatement(selectCustomerQuery);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            products.add(rs.getString("ProductName"));
        }
	return products;
	}
        catch(Exception e){}
        return null;
}
public static ArrayList<String> GetSaleDetails(){
    try{
        getConnection();
        Statement stmt=conn.createStatement();
		ArrayList<String> products = new ArrayList<String>();
        String selectCustomerQuery="select ProductName from ProductInventory where Onsale = 1;";
		PreparedStatement pst = conn.prepareStatement(selectCustomerQuery);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            products.add(rs.getString("ProductName"));
        }
	return products;
	}
        catch(Exception e){}
        return null;
}
public static Integer GetProductQuantity(String ProductName){
	try{
		getConnection();
		Statement stmt=conn.createStatement();
		String selectCustomerQuery="select * from ProductInventory where ProductName = ?;";
		PreparedStatement pst = conn.prepareStatement(selectCustomerQuery);
		pst.setString(1,ProductName);
		ResultSet rs = pst.executeQuery();
		if(rs.next())
		{
			Integer quantity =  rs.getInt("Quantity");
            return quantity;
        }
		return -1;
	}
        catch(Exception e){
			return -1;
		}
}
public static void DeleteProductFromInventory(String ProductName){
	try{
		getConnection();
		PreparedStatement ps =  conn.prepareStatement("delete from ProductInventory where ProductName =?;");
			ps.setString(1,ProductName);
			ps.execute();
	}
        catch(Exception e){
		}
}
public static HashMap<String,Integer> GetTotalSales(){
	HashMap<String, Integer> pds =  new HashMap<String, Integer>();
    try
	{					
		getConnection();
        //select the table 
		String selectOrderQuery ="select ProductName,Count(id) as count from OrderProducts group by ProductName;";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			pds.put(rs.getString("ProductName"),rs.getInt("count"));		
		}
        return pds;
	}
	catch(Exception e)
	{
		
	}
    return null;
}
public static HashMap<String,Integer> GetDailySales(){
	HashMap<String, Integer> pds =  new HashMap<String, Integer>();
    try
	{					
		getConnection();
        //select the table 
		String selectOrderQuery ="select sum(total) as tsum,date from OrderDetails group by date;";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			pds.put(rs.getString("date"),rs.getInt("tsum"));		
		}
        return pds;
	}
	catch(Exception e)
	{
		
	}
    return null;
}
public static String GetProductPrice(String ProductName){
    try
	{					
		getConnection();
		String selectOrderQuery ="select Price from ProductDetails where ProductName like '"+ProductName+"';";			
		PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			return rs.getString("Price");		
		}
        return null;
	}
	catch(Exception e)
	{
		return null;
	}
}
public static boolean AddInventoryDetails(String ProductName,Integer Quantity, boolean Rebate, boolean Onsale){
		Integer qt = GetProductQuantity(ProductName);
		if(qt!=-1){
			DeleteProductFromInventory(ProductName);
		}
	try{
		getConnection();
		String insertIntoCustomerOrderQuery = "INSERT INTO ProductInventory(ProductName,Quantity,Rebate,Onsale) VALUES (?,?,?,?);";	
		PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
		pst.setString(1,ProductName);
		pst.setInt(2,qt==-1?Quantity:Quantity+qt);
		pst.setBoolean(3,Rebate);
		pst.setBoolean(4,Onsale);
		pst.execute();
		return true;
	}
		catch(Exception e){}
		return false;
}
}