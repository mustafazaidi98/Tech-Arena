import java.io.IOException;
import java.io.*;

public class Review implements Serializable{
	public String ProductName;
	public String productCategory;
	public String productPrice;
	public String storeName;
	public String productOnSale;
	public String manufactorName;
	public String userName;
	public String userOccupation;
	public String userGender;
	public String reviewRating;
    public String reviewDate;
    public String reviewText;
    public String zipCode;
	
	public Review (String ProductName,
    String productCategory, String productPrice,
    String storeName, String productOnSale,
    String manufactorName, String userName,
    String userOccupation, String userGender,
    String reviewRating, String reviewDate,
    String reviewText,String zipCode){
        this.ProductName = ProductName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.storeName = storeName;
        this.productOnSale = productOnSale;
        this.manufactorName = manufactorName;
        this.userName= userName;
        this.userOccupation = userOccupation;
        this.userGender = userGender;
        this.reviewRating = reviewRating;
        this.reviewDate=  reviewDate;
        this.reviewText = reviewText;
        this.zipCode = zipCode;
	}

	public Review(String ProductName, String reviewRating, String reviewText) {
       this.ProductName = ProductName;
       this.reviewRating = reviewRating;
       this.reviewText = reviewText;
    }
}