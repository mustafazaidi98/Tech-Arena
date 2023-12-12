import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.io.*;


public class Order implements Serializable{
	public int id;
    public String username ;
    public String firstname ;
    public String email;
    public String address;
    public String city;
    public String state;
    public String zip;
    public String cardname;
    public String expmonth;
    public String expyear;
    public String cvv ;
    public String Deliver;
    public String Date;
    public String total;
    HashMap<String, ProductDetails> Products=new HashMap<String, ProductDetails>();
	
	public Order(int id,String username,String firstname,String email,String address,
    String city,String state,String zip,String cardname,String expmonth,
    String expyear,String cvv ,String Deliver,String Date,HashMap<String, ProductDetails> Products) {
        this.id = id;
        this.Date = Date;
        this.username = username;
        this.firstname = firstname;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip =zip;
        this.cardname = cardname;
        this.expmonth = expmonth;
        this.expyear = expyear;
        this.cvv = cvv;
        this.Deliver = Deliver;
        this.Products = Products;
	}
}
