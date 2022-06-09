package DatabaseConnection;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ATM.AccountDetails;



public class DBConnection {

	static String url = "jdbc:sqlserver://MAVCHN1121638\\SQLEXPRESS;databaseName=testdb;integratedSecurity=true;encrypt=false;";

public static List<AccountDetails> ConnectWithDB() {
	List<AccountDetails> accDetails = new ArrayList<AccountDetails>();
	try
	{
	
	
		String query = "select * from AccountDetails";
		try(Connection conn = DriverManager.getConnection(url))
			{
			
			System.out.println("Connection Successfull!");
			
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(query);
			while(result.next())
			{
			AccountDetails ad = new AccountDetails();
			ad.setCustomerNumber(Integer.parseInt(result.getString(1)));
			ad.setPIN(Integer.parseInt(result.getString(2)));
			ad.setLastName(result.getString(3));
			ad.setFirstName(result.getString(4));
			ad.setBankName(result.getString(5));
			ad.setAccountType(result.getString(6));
			ad.setBalance(Double.parseDouble(result.getString(7)));
		
			accDetails.add(ad);
			}
			
			conn.close();
			}
	
	}
	catch(SQLException e)
	{
	System.out.println("Connection failed!");
	e.printStackTrace();
	}
	return accDetails;
}

public static void updateDB(AccountDetails ad)
{
	try
	{
		String query = "Update AccountDetails set Balance="+ad.getBalance()+ " where CustomerNumber='"+ad.getCustomerNumber()+"'" ;
		System.out.println("Update Query ->"+query);
		try(Connection conn = DriverManager.getConnection(url))
			{
			
			System.out.println("Connection Successfull!");
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			conn.close();
			}
		catch(SQLException e)
		{
		System.out.println("Connection failed!");
		e.printStackTrace();
		}
		
	}
	catch(Exception e)
	{
	System.out.println("Connection failed!");
	e.printStackTrace();
	}
}
public static void updateTransferDB(int cst_no,double bal)
{
	try
	{
		String query = "Update AccountDetails set Balance="+bal+ " where CustomerNumber='"+cst_no+"'" ;
		System.out.println("Update Query ->"+query);
		try(Connection conn = DriverManager.getConnection(url))
			{
			
			System.out.println("Connection Successfull!");
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			conn.close();
			}
		catch(SQLException e)
		{
		System.out.println("Connection failed!");
		e.printStackTrace();
		}
		
	}
	catch(Exception e)
	{
	System.out.println("Connection failed!");
	e.printStackTrace();
	}
}
public static void insertDB(AccountDetails ad)
{
	try
	{
		String query = "Insert into AccountDetails values('"+ad.getCustomerNumber()+"','"+ad.getPIN()+"','"+ad.getFirstName()+"','"+ad.getLastName()+"','"+ad.getBankName()+"','"+ad.getAccountType()+"',"+ad.getBalance()+")";
		System.out.println("Insert Query ->"+query);
		try(Connection conn = DriverManager.getConnection(url))
			{
			
			System.out.println("Connection Successfull!");
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			conn.close();
			}
		catch(SQLException e)
		{
		System.out.println("Connection failed!");
		e.printStackTrace();
		}
		
	}
	catch(Exception e)
	{
	System.out.println("Connection failed!");
	e.printStackTrace();
	}
}
}