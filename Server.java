/* @author Lekhraj
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Database server java
 *
 */
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;
import java.sql.*;

public class Server {
    static PreparedStatement pstmt;
    static Connection con;
    static Statement stmt;
    static ResultSet rs = null;
    public static void main(String[] args) throws IOException {
        try {

            String n = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            makeConnection();
            Scanner sc = new Scanner(System.in);
//            Class.forName("com.mysql.jdbc.Driver");
//            String url = "jdbc:mysql://localhost:3306/chatapp";
//            con = DriverManager.getConnection(url, "root", "");
//            stmt = con.createStatement();

            boolean chatStarted = false;
            boolean condition = true;
            ServerSocket server = new ServerSocket(1234);
            Socket client;
            System.out.println("Waiting for client to connect...");
            client = server.accept();
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            DataInputStream dis = new DataInputStream(client.getInputStream());
            while (condition) {

                if (chatStarted == false) {
                    System.out.println("Connected to Client. Type `Quit` to quit/cancel the chat");
                    System.out.println();
//                    replyToClient= "Hello Client";
//                    dos.writeUTF(replyToClient);//UTF= unicode transformtion format. this helps to transfer the data without loss  because it converts the text into unicode
                    chatStarted = true;
                }
                String replyFromClient = dis.readUTF();

                if (chatStarted == true && !replyFromClient.equals("")) {
                    System.out.println("Client Says: ");
                    String currentTime = sdf.format(new Date());
                    System.out.println(replyFromClient);
                    String name = "Client";
                    String query = "Insert into chat (name, DateTime, Chats) "
                            + "values('" + name + "','" + currentTime + "','" + replyFromClient + "')";

                    System.out.println("Your Reply: ");
                    String chatReply = sc.nextLine();
                    name = "Server";
                    currentTime = sdf.format(new Date());
                    query = "Insert into chat (name, DateTime, Chats) values('" + name + "','" + currentTime + "','" + chatReply + "')";
                    //select chat from chat
                    pstmt = con.prepareStatement("Select * from chat where name='Server' order by id desc limit 1 ");
                    rs = pstmt.executeQuery();
                    stmt.execute(query);
                    //
                    stmt.execute(query);
                    if ("Quit".equals(chatReply) || "quit".equals(chatReply)) {
                        dos.writeUTF("Bye Bye Client");
                        condition = false;
                    } else {
                        dos.writeUTF(chatReply);
                        System.out.println("Press '1' to update, press '2' to delete ");
                        n = sc.nextLine();
                        if (n.equals("1") || n.equals("2")) {
                            switch (n) {
                                case "1":
                                    //logic to update the chat
//                                    updateChat();
                                    break;
                                case "2":
                                    deleteChat();
                                    break;
                            }
                        }
                        else{
                            continue;
                        }
                    }
                }

            }
        } catch (Exception ex) {
            System.out.println("Exception " + ex);
        }
    }

    public void getChatDetail(ResultSet rs) {

    }
    static void updateChat(){
        
        System.out.println("Updating Chat");
    }
    static void deleteChat(){
        System.out.println("Deleting Chat");
    }
    static void makeConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/chatapp";
            con = DriverManager.getConnection(url, "root", "");
            stmt = con.createStatement();
        }
        catch(Exception ex){
            System.out.println("Exception " + ex);
        }
    }
}
//System.out.println("S.no |      user |      TimeStamp |     Chat");
//                int sn = 0;
//                String update;
//
//                while (rs.next()) {
//                    int id = rs.getInt("id");
//                    String user = rs.getString("name");
//                    String time = rs.getString("DateTime");
//                    String Chat = rs.getString("Chats");
//                    System.out.println(id + " |     " + user + " |      " + time + " |      " + Chat);
//
//                }
//                System.out.println("Which one do you want to delete");
//                update = sc.nextLine();
