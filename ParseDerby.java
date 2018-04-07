package business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.csvreader.CsvReader;
import java.sql.*;

public class ParseDerby {
	
	private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private static String protocol = "jdbc:derby:";
	
	static String dbName = "~/myDB";
	
	/**
	 * load the business data
	 * @throws IOException 
	 */
	public static ArrayList<Business> loadBusinessData(String csv_file)  {
		ArrayList<Business> arr = new ArrayList<Business>();
		try {
			Scanner in = new Scanner(new File(csv_file));
			// remove header
			in.nextLine();
            while (in.hasNext()){
            		String line = in.nextLine();
            		String[] tokens = line.split("\t");
            		if (tokens.length < 9) {
            			continue;
            		}
            		Business bn = new Business();
            		bn.register_name = tokens[0];
            		bn.bn_name = tokens[1];
            		bn.bn_status = tokens[2];
            		bn.bn_reg_dt = tokens[3];
            		bn.bn_cancel_dt = tokens[4];
            		bn.bn_renew_dt = tokens[5];
            		bn.bn_state_num = tokens[6];
            		bn.bn_state_of_reg = tokens[7];
            		bn.ibn_abn = tokens[8];
            		arr.add(bn);
            }
		} catch (IOException e) {
            System.err.println(e);
		}
		return arr;
	}
	
	/**
	 * load the driver for derby
	 */
	public static void loadDBDriver() {
		try {
            Class.forName(driver);
        } catch (Exception e) {
            System.err.println(e);
        }
	}
	
	/**
	 * do the create db and query demo with using derby 
	 */
	public static void doDerbyDemo(ArrayList<Business> arr) {
		try {
			// create database
			Connection conn = DriverManager.getConnection(protocol + dbName + ";user=root;password=root;create=true");
	        Statement statement = conn.createStatement();
	        
	        // create table
	        statement.execute("drop table business");
	        statement.execute("create table business(register_name varchar(20), bn_name varchar(200), bn_status varchar(60), bn_reg_dt char(20), "
	        		+ "bn_cancel_dt char(20), bn_renew_dt char(20), bn_state_num varchar(20), bn_state_of_reg varchar(20), ibn_abn char(20))");
	        
	        // insert the data
	        // start time
	        long startTime = System.currentTimeMillis();   
	        PreparedStatement psInsert = conn.prepareStatement("insert into business values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
	        for(Business bn: arr) {
	        		psInsert.setString(1, bn.register_name);
	        		psInsert.setString(2, bn.bn_name);
	        		psInsert.setString(3,  bn.bn_status);
	        		psInsert.setString(4,  bn.bn_reg_dt);
	        		psInsert.setString(5,  bn.bn_cancel_dt);
	        		psInsert.setString(6,  bn.bn_renew_dt);
	        		psInsert.setString(7,  bn.bn_state_num);
	        		psInsert.setString(8,  bn.bn_state_of_reg);
	        		psInsert.setString(9,  bn.ibn_abn);
	        		psInsert.executeUpdate();
	        }
	        conn.commit(); 
	        long endTime=System.currentTimeMillis(); 
	         
	        System.out.println("inserting the data costs： " + (endTime - startTime) + "ms");
	        
	        // query the data
	        ResultSet rs = statement.executeQuery(String.format("select * from business"));
	        while (rs.next()) {
	        		System.out.println(String.format("resister_name: %s, bn_name: %s, bn_status: %s, bn_reg_dt: %s, bn_cancel_dt: %s, bn_renew_dt: %s, "
	        				+ "bn_state_num: %s, bn_state_of_reg: %s, ibn_abn: %s", rs.getString(1), rs.getString(2), rs.getString(3), 
	        				rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
	        		break;
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Business> arr = loadBusinessData("BUSINESS_NAMES_201804.csv");	
		System.out.println(arr.get(0));
		loadDBDriver();
		doDerbyDemo(arr);
	}

}
