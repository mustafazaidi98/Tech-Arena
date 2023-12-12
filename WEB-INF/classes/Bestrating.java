import java.io.IOException;
import java.io.*;

public class Bestrating implements Serializable{
	public String ProductName;
	public String reviewRating;
	
	public Bestrating(String ProductName,String reviewRating){
        this.ProductName = ProductName;
        this.reviewRating = reviewRating;
	}
}