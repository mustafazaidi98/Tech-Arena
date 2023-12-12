import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.AggregationOutput;
import java.util.*;
                	
public class MongoDataStoreUtilities
{
static DBCollection myReviews;
public static DBCollection getConnection()
{
MongoClient mongo;
mongo = new MongoClient("localhost", 27017);

DB db = mongo.getDB("TechArena");
 myReviews= db.getCollection("myReviews");	
return myReviews; 
}


public static String insertReview(String ProductName,
String productCategory, String productPrice,
String storeName, String productOnSale,
String manufactorName, String userName,
String userOccupation, String userGender,
String reviewRating, String reviewDate,
String reviewText, String zipcode)
{
	try
		{		
			getConnection();
			BasicDBObject doc = new BasicDBObject("title", "myReviews").
				append("ProductName", ProductName).
                append("productCategory", productCategory).
				append("productPrice", productPrice).
				append("storeName", storeName).
				append("productOnSale", productOnSale).
				append("manufactorName",manufactorName).
				append("userName", userName).
				append("userOccupation", userOccupation).
				append("userGender", userGender).
				append("reviewRating", reviewRating).
                append("reviewDate", reviewDate).
                append("reviewRating", reviewRating).
                append("zipcode", zipcode).
				append("reviewText",reviewText);
			myReviews.insert(doc);
			return "Successfull";
		}
		catch(Exception e)
		{
		return "UnSuccessfull";
		}	
		
}

public static HashMap<String, ArrayList<Review>> selectReview()
{	
	HashMap<String, ArrayList<Review>> reviews=null;
	
	try
		{

	getConnection();
	DBCursor cursor = myReviews.find();
	reviews=new HashMap<String, ArrayList<Review>>();
	while (cursor.hasNext())
	{
			BasicDBObject obj = (BasicDBObject) cursor.next();				
	
		   if(!reviews.containsKey(obj.getString("ProductName")))
			{	
				ArrayList<Review> arr = new ArrayList<Review>();
				reviews.put(obj.getString("ProductName"), arr);
			}
			ArrayList<Review> listReview = reviews.get(obj.getString("ProductName"));		
			Review review =new Review(obj.getString("ProductName"),
            obj.getString("productCategory"),obj.getString("productPrice"),
            obj.getString("storeName"),obj.getString("productOnSale"),
            obj.getString("manufactorName"),obj.getString("userName"),
            obj.getString("userOccupation"),obj.getString("userGender"),
            obj.getString("reviewRating"), obj.getString("reviewDate"),
            obj.getString("reviewText"),obj.getString("zipcode"));
			//add to review hashmap
			listReview.add(review);
		
			}
 		return reviews;
		}
		catch(Exception e)
		{
		 reviews=null;
		 return reviews;	
		}	
	}
    public static  ArrayList <Bestrating> topProducts(){
        ArrayList <Bestrating> Bestrate = new ArrayList <Bestrating> ();
        try{
            
        getConnection();
        int retlimit = 5;
        DBObject sort = new BasicDBObject();
        sort.put("reviewRating",-1);
        DBCursor cursor = myReviews.find().limit(retlimit).sort(sort);
        while(cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
                                 
            String prodcutnm = obj.get("ProductName").toString();
            String rating = obj.get("reviewRating").toString();
            Bestrating best = new Bestrating(prodcutnm,rating);
            Bestrate.add(best);
        }
      
      }catch (Exception e){ System.out.println(e.getMessage());}
     return Bestrate;
    }
    public static ArrayList <MostSold> mostsoldProducts(){
        ArrayList <MostSold> mostsold = new ArrayList <MostSold> ();
        try{
            
        
        getConnection();
        DBObject groupProducts = new BasicDBObject("_id","$ProductName"); 
        groupProducts.put("count",new BasicDBObject("$sum",1));
        DBObject group = new BasicDBObject("$group",groupProducts);
        DBObject limit=new BasicDBObject();
        limit=new BasicDBObject("$limit",5);
        
        DBObject sortFields = new BasicDBObject("count",-1);
        DBObject sort = new BasicDBObject("$sort",sortFields);
        AggregationOutput output = myReviews.aggregate(group,sort,limit);
        
        for (DBObject res : output.results()) {
        
        
         
          String prodcutname =(res.get("_id")).toString();
          String count = (res.get("count")).toString();	
          MostSold mostsld = new MostSold(prodcutname,count);
          mostsold.add(mostsld);
      
        }
        
       
        
      }catch (Exception e){ System.out.println(e.getMessage());}
        return mostsold;
    }
    public static ArrayList <Mostsoldzip> mostsoldZip(){
        ArrayList <Mostsoldzip> mostsoldzip = new ArrayList <Mostsoldzip> ();
        try{
            
        getConnection();
        DBObject groupProducts = new BasicDBObject("_id","$zipcode"); 
        groupProducts.put("count",new BasicDBObject("$sum",1));
        DBObject group = new BasicDBObject("$group",groupProducts);
        DBObject limit=new BasicDBObject();
        limit=new BasicDBObject("$limit",5);
        
        DBObject sortFields = new BasicDBObject("count",-1);
        DBObject sort = new BasicDBObject("$sort",sortFields);
        AggregationOutput output = myReviews.aggregate(group,sort,limit);
        for (DBObject res : output.results()) {
          String zipcode =(res.get("_id")).toString();
          String count = (res.get("count")).toString();	
          Mostsoldzip mostsldzip = new Mostsoldzip(zipcode,count);
          mostsoldzip.add(mostsldzip);
      
        }
        
       
        
      }catch (Exception e){ System.out.println(e.getMessage());}
        return mostsoldzip;
    }
}	