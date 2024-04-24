/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dell
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Client {
    public static void main(String[] args) {
        try{
            Scanner sc = new Scanner(System.in);
            boolean chatStarted=false;
            boolean condition =true;
            int chatCount=0;
            Socket client = new Socket("localhost", 1234); //localhost is 127.0.0.1
            if(client.isConnected()){
                System.out.println("Connected to server. Type  `Quit` to quit/cancel the chat");
                DataOutputStream dos= new DataOutputStream(client.getOutputStream());
                DataInputStream dis = new DataInputStream(client.getInputStream());
                String replyFromServer="";
//                String previousReply ="";

                while(condition){
//                    if(chatStarted == false){
//                        dos.writeUTF("Hello Server");
                        chatStarted =true;
//                    }
                    if(chatCount!= 0){
                        replyFromServer= dis.readUTF();
//                        previousReply = "";
                    }

                    if(chatStarted == true && (!"".equals(replyFromServer) || chatCount==0  )){
                        if(chatCount!=0){
                            System.out.println("Server Says: ");
                            System.out.println(replyFromServer);
                        }

                        System.out.println("Your Reply: ");
                        String chatReply= sc.nextLine();
                        if("Quit".equals(chatReply) || "quit".equals(chatReply)){
                            dos.writeUTF("Bye Bye Server");
                            condition=false;
                        }
                        else{
                            dos.writeUTF(chatReply);
                        }
//                        previousReply = replyFromServer;

                    } 
                    ++chatCount;
                }
               
            }
        }
        catch(Exception ex){
            System.out.println("Exception is "+ex.getMessage());
        }
    }
}
