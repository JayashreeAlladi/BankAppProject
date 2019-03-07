package com.bankdatabase.daoimpl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

import com.bankdatabase.dao.Registration;
import com.bankdatabase.model.User;
import com.bankdatabase.utility.DataConnection;

public class RegistrationImpl implements Registration{

	User user=new User();
	DataConnection d=new DataConnection();
	Connection con=d.connect();

	@Override
	public void registration() {

		try {
			PreparedStatement preparedStatement=con.prepareStatement("insert into user values(?,?,?,?,?,?,?,?,?,?)");


			getUserInfo();


			preparedStatement.setString(1,user.getFirstName());
			preparedStatement.setString(2,user.getLastName());
			preparedStatement.setString(3,user.getAccountType());
			preparedStatement.setString(4, user.getPanCard());
			preparedStatement.setString(5, user.getAddress());
			preparedStatement.setLong(6, user.getAadharCardNo());
			preparedStatement.setLong(7, user.getPhoneNo());
			preparedStatement.setString(8,user.getPassword());
			preparedStatement.setLong(9,user.getAccountNo());
			preparedStatement.setLong(10, 0);

			int i=preparedStatement.executeUpdate();
			if (i==1) {
				System.out.println("Registered successfully with account number"+user.getAccountNo());
			} else {
				System.err.println("Enter proper details");
				registration();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void getUserInfo() {
		Random r=new Random();
		Scanner scanner=new Scanner(System.in);
		System.out.println("=============Registration Page============");

		System.out.println("First name");
		user.setFirstName(scanner.next());
		System.out.println("Last name");
		user.setLastName(scanner.next());
		System.out.println("Aadhar number");
		user.setAadharCardNo(scanner.nextLong());
		System.out.println("Account Type");
		user.setAccountType(scanner.next());
		System.out.println("Pancar number");
		user.setPanCard(scanner.next());
		System.out.println("Phone number");
		user.setPhoneNo(scanner.nextLong());
		System.out.println("Address");
		user.setAddress(scanner.next());
		System.out.println("Create password");
		user.setPassword(scanner.next());

		if(validateUser(user.getAadharCardNo()))
		{
			user.setAccountNo(Math.abs(r.nextInt()));
		}
		else
		{
			System.err.println("User already exists ");
			registration();
		}

		
	}

	public boolean validateUser(long aadharCardNo) {
		boolean check = true;
		try {
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from user");
			while(rs.next())
			{
				if(rs.getLong(6)==aadharCardNo)
				{
				check = false;
				break;
				}
			}



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;

	}
}