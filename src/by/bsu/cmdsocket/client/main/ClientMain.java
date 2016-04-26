/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsu.cmdsocket.client.main;

import by.bsu.cmdsocket.client.config.ClientProperties;
import by.bsu.cmdsocket.common.entity.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Антон
 */
public class ClientMain {
    
     public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter user name: ");
            
            String clientName = scanner.next();
            
            String serverHost = ClientProperties.getServerHost();
            int serverPort = ClientProperties.getServerPort();
            Socket socket = new Socket(serverHost, serverPort);

            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            
            os.writeObject(new ConnectMessageRequest(clientName));
            MessageResponse response = (MessageResponse) is.readObject();
            printResponse(response);
            
            os.writeObject(new CommandMessageRequest(clientName, "echo Hello"));
            response = (MessageResponse) is.readObject();
            printResponse(response);
            
            os.writeObject(new CommandMessageRequest(clientName, "dir"));
            response = (MessageResponse) is.readObject();
            printResponse(response);
            
            os.writeObject(new DisconnectMessageRequest(clientName));
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     private static void printResponse(MessageResponse response) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("GMT+3"));
        String nowString = now.format(DateTimeFormatter.ISO_DATE_TIME);
        String message = MessageFormat.format("[{0}] {1}", nowString, response);
        System.out.println(message);
    }
}
