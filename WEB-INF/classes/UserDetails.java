import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;


public class UserDetails implements Serializable{
	private int id;
    private String username;
	private String fullname;
	private String password;
    private String number;
    private String email;
    private String userrole;
	
	public UserDetails(String fullname, String username, String password, String email,String number,String userrole) {
        this.username = username;
		this.fullname=fullname;
		this.password=password;
        this.email = email;
        this.number = number;
        this.userrole=userrole;
	}
	public UserDetails(String username, String password,String userrole) {
        this.username = username;
		this.password=password;
        this.userrole=userrole;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setuserrole(String userrole) {
		this.userrole = userrole;
	}
    	public String getuserrole() {
		return userrole;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return fullname;
	}

	public void setName(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
