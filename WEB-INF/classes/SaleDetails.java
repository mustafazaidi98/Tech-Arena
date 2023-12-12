import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class SaleDetails implements Serializable{
    public Integer quantity;
    public Integer price;
    public String name;
    
    public SaleDetails(String name,Integer quantity,Integer price){
        this.name =name;
        this.quantity = quantity;
        this.price = price;

    }
}