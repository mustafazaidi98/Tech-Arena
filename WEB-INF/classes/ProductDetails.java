import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;


public class ProductDetails implements Serializable{
	private String description;
    private String ProductName;
	private String Price;
	private String ImagePath;
    private String category;
	public String subcategory;
	
	public ProductDetails(String description, String ProductName, String Price, String ImagePath,String category, String subcategory) {
        this.description = description;
		this.ProductName=ProductName;
		this.Price=Price;
        this.ImagePath = ImagePath;
        this.category = category;
		this.subcategory = subcategory;
	}
	public ProductDetails(String ProductName, String Price, String ImagePath) {
		this.ProductName=ProductName;
		this.Price=Price;
        this.ImagePath = ImagePath;
	}
	public String getdescription() {
		return description;
	}

	public void setdescription(String description) {
		this.description = description;
	}

	public String getProductName() {
		return ProductName;
	}

	public void setProductName(String ProductName) {
		this.ProductName = ProductName;
	}
    public String getPrice() {
		return Price;
	}
	public void setPrice(String Price){
		this.Price = Price;
	}
	public void setImagePath(String ImagePath) {
		this.ImagePath = ImagePath;
	}
	public String getImagePath() {
		return ImagePath;
	}
	public String getcategory() {
		return category;
	}
	public void setcategory(String category) {
		this.category = category;
	}
}
