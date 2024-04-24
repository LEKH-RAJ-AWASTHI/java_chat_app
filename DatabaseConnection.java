/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dell
 */
import java.sql.*;
import java.util.Scanner;
public class DatabaseConnection {
    public static void main(String args[]){
        Connection con;
        Statement stmt;
        Scanner sc = new Scanner(System.in);
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/My_DB";
            con = DriverManager.getConnection(url,"root","");
            stmt= con.createStatement();
            System.out.println("Connection Succesfull");
            String n, e, p;
            System.out.println("Enter Your Name");
            n=sc.nextLine();
            System.out.println("Enter Your Email");
            e=sc.nextLine();
            System.out.println("Enter Your Phone Number");
            p=sc.nextLine();
            stmt.executeUpdate("Insert into (name, email, phone_number) values('"+n+"', '"+e+"', '"+p+"')");
            System.out.println("Data Saved Successfully");
        }
        catch(Exception ex)
        {
            System.out.println("Database Error: "+ex);
        }
        finally{
            
        }
    }
}
