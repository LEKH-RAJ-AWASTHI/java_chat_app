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
    static Scanner sc;
    static SimpleDateFormat sdf;
    static Socket client;
    static ServerSocket server;

    public static void main(String[] args) throws IOException {
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            makeConnection();
            sc = new Scanner(System.in);
//            Class.forName("com.mysql.jdbc.Driver");
//            String url = "jdbc:mysql://localhost:3306/chatapp";
//            con = DriverManager.getConnection(url, "root", "");
//            stmt = con.createStatement();

            boolean chatStarted = false;
            
            boolean[] condition = {true};
            server = new ServerSocket(1234);
            System.out.println("Waiting for client to connect...");
            client = server.accept();
            DataInputStream dis = new DataInputStream(client.getInputStream());
            while (condition[0]) {
                //chatstarted == false is the logic to give information to that client is connected
                if (chatStarted == false) {
                    System.out.println("Connected to Client. Type `Quit` to quit/cancel the chat");
                    System.out.println();
//                    replyToClient= "Hello Client";
//                    dos.writeUTF(replyToClient);//UTF= unicode transformtion format. this helps to transfer the data without loss  because it converts the text into unicode
                    chatStarted = true;
                }
                String replyFromClient = dis.readUTF();
                
                //this condition checks if replyfrom client is null or the chat is started or not
                if (chatStarted == true && !replyFromClient.equals("")) {
                    System.out.println("Client Says: ");
                    String currentTime = sdf.format(new Date());
                    System.out.println(replyFromClient);
                    String name = "Client";
                    String query = "Insert into chat (name, DateTime, Chats) "
                            + "values('" + name + "','" + currentTime + "','" + replyFromClient + "')";
                    stmt.executeUpdate(query);

                    System.out.println("Your Reply: ");
                    //applying conditon to delete update or continue chat
                    System.out.println("Press '1' to update, press '2' to delete ");
                    String chatReply = sc.nextLine();
                    if (chatReply.equals("1") || chatReply.equals("2")) {
                        switch (chatReply) {
                            case "1" -> {
                                //logic to update the chat
                                updateChat();
                                System.out.println("You have updated your chat. Now give reply to client");
                                chatReply= getChatString();
                                replyToClient(chatReply, condition);
//                                condition= false;
                                continue;
                            }
                            case "2" -> {
                                deleteChat();
                                System.out.println("You have updated your chat. Now give reply to client");
                                chatReply = getChatString();
                                replyToClient(chatReply, condition);
//                                condition =false;
                                continue;
                            }
                        }
                    } else {
                        replyToClient(chatReply, condition);

                    }
                    //end of the switch/ if else condition

                }

            }
        } catch (IOException | SQLException ex) {
            System.out.println("Exception " + ex);
        }
    }

    static void updateChat() {
        try {
            Scanner scan = new Scanner(System.in);
            makeConnection();
            getChats(5);
            System.out.println("Which one do you want to delete. Choose S.no ");
            int id = sc.nextInt();
            String modifiedChat;
            System.out.println("Write your changed chat: ");
            modifiedChat = scan.nextLine();
            String query = "UPDATE CHAT SET chats='" + modifiedChat + "' WHERE id=" + id;
            stmt.executeUpdate(query);
            getChats(5);
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex);
        }
    }

    static void deleteChat() {
        try {
            makeConnection();
            getChats(5);
            System.out.println("Which one do you want to delete. Choose S.no ");
            int id = sc.nextInt();
            String query = "DELETE FROM chat WHERE id=" + id;
            stmt.executeUpdate(query);
            getChats(5);
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex);
        }
    }

    static void makeConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/chatapp";
            con = DriverManager.getConnection(url, "root", "");
            stmt = con.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Exception " + ex);
        }
    }

    static void getChats(int num) {
        try {
            pstmt = con.prepareStatement("Select * from chat where name='Server' order by id desc limit  " + num);
            rs = pstmt.executeQuery();
            System.out.println("S.no |user      |TimeStamp                |Chat");
            String user;
            String time;
            String chat ;
            while (rs.next()) {
                int id = rs.getInt("id");
                user = rs.getString("name");
                time = rs.getString("DateTime");
                chat = rs.getString("Chats");
                System.out.println(id + "   |" + user + "    |" + time + "    |" + chat);

            }
        } catch (SQLException ex) {
            System.out.println("Exception: " + ex);
        }
    }

    static void replyToClient(String chatReply, boolean[] condition) throws IOException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String name = "Server";
        String currentTime = sdf1.format(new Date());
        DataOutputStream dos;
        dos = new DataOutputStream(client.getOutputStream());
        String query = "Insert into chat (name, DateTime, Chats) values('" + name + "','" + currentTime + "','" + chatReply + "')";
        try {
            stmt.executeUpdate(query);
            if ("Quit".equals(chatReply) || "quit".equals(chatReply)) {
                dos.writeUTF("Bye Bye Client");
                condition[0] = false;
            } else {
                dos.writeUTF(chatReply);
            }
        } catch (IOException | SQLException ex) {
            System.out.println("Exception: " + ex);
        }

    }
    static String getChatString(){
        Scanner scan = new Scanner(System.in);
        String chatReply= scan.nextLine();
        if(chatReply.equals("")){
            chatReply= scan.nextLine();
        }
        return chatReply;
    }
}
