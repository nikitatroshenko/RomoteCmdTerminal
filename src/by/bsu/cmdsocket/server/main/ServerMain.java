package by.bsu.cmdsocket.server.main;

import by.bsu.cmdsocket.common.exceptions.CmdTerminalSocketException;
import by.bsu.cmdsocket.server.action.ServerThread;
import by.bsu.cmdsocket.server.config.ServerProperties;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Никита on 24.04.2016.
 */
public class ServerMain {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(ServerProperties.getPort())) {
            System.out.println("Server is initialized successfully.");
            while (true) {
                System.out.println("Listening...");
                Socket socket = server.accept();
                System.out.println(socket.getInetAddress().getHostName() + " connected");
                ServerThread connectionThread = new ServerThread(socket);
                connectionThread.start();
            }
        } catch (CmdTerminalSocketException | IOException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
