import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class InventoryDetails implements Serializable{
    public Integer Quantity;
    public String ProductName;
    public InventoryDetails(String ProductName,Integer Quantity){
        this.ProductName =ProductName;
        this.Quantity = Quantity;
    }
}