package trabalho4;

import java.io.Serializable;
import java.util.HashMap;

public class Client implements Serializable 
{
	
	
	
	//
	private String email, password,name,affiliation;
	//constructor 
	public Client(String name, String email, String password, String affiliation)
	{
		this.email = email;     //email----- chave primaria
		this.password = password;
		this.name= name;
		this.affiliation= affiliation;
		
	}
	
	
	
	public String getName()
	{
		return name;
	}
	public void setName(String name )
	{
		this.name = name;
	}
	
	
	
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
	
	

	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}

	
	

	public String getAffiliation()
	{
		return affiliation;
	}
	public void setAffiliation(String affiliation)
	{
		this.affiliation = affiliation;
	}

	

}
	