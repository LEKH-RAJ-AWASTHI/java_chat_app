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
public class Search {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String search;
        System.out.println("Enter anything to search");
        search= sc.nextLine();
        

        try{
            Connection con;
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement pstmt;
            ResultSet rs;
            String url="jdbc:mysql://localhost:3306/My_Db";
            con= DriverManager.getConnection(url,"root","");
            pstmt= con.prepareStatement("Select * from student Where id=? or name=? or email=? or phone_number=?");
            pstmt.setString(1, search);
            pstmt.setString(2, search);
            pstmt.setString(3, search);
            pstmt.setString(4, search);
            rs= pstmt.executeQuery();
            
            while(rs.next()){
                String id, n, e, p;
                id= rs.getString("id");
                n= rs.getString("name");
                e= rs.getString("email");
                p= rs.getString("phone_number");
                
                System.out.println("Id: "+id);
                System.out.println("Name: "+n);
                System.out.println("Email: "+e);
                System.out.println("Phone Number: "+p);
                System.out.println("----------------------------------");

                                
                
            }
            if(!rs.next()){
                System.out.println("Data not Found");
            }
        }
        catch(Exception ex){
            System.out.println("Exception is: "+ex.getMessage());
        }

        
    }
}
