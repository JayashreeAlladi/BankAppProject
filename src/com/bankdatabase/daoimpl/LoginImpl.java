package com.bankdatabase.daoimpl;


import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.bankdatabase.dao.Login;
import com.bankdatabase.model.User;
import com.bankdatabase.utility.DataConnection;

public class LoginImpl implements Login{

	DataConnection d=new DataConnection();
	static User user=new User();
	Scanner sc=new Scanner(System.in);
	@Override
	public long login() {
		Connection con=d.connect();
		System.out.println("Enter account number");
		user.setAccountNo(sc.nextLong());
		System.out.println("Enter password");
		user.setPassword(sc.next());
		int count=0;
		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs=st.executeQuery("select * from user");
			while(rs.next())
			{
				
				if(rs.getLong(9)==user.getAccountNo() && rs.getString(8).equals(user.getPassword()))
				{
					count++;
					break;
				}
				
			}
			if (count==1) {
				System.out.println("Login successful");
			} else {
				System.err.println("Error Loging");
				login();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user.getAccountNo();
		
		
		
	}

}