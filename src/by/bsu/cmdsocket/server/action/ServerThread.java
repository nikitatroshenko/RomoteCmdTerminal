package by.bsu.cmdsocket.server.action;

import by.bsu.cmdsocket.common.entity.*;
import by.bsu.cmdsocket.common.enums.ResponseInfo;
import by.bsu.cmdsocket.common.exceptions.CmdTerminalSocketException;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Никита on 24.04.2016.
 */
public class ServerThread extends Thread {

    private static final Logger LOGGER = Logger.getLogger(ServerThread.class.getName());

    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private InetAddress address;
    private String clientName;

    public ServerThread(Socket socket) throws CmdTerminalSocketException {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            address = socket.getInetAddress();
        } catch (IOException e) {
            throw new CmdTerminalSocketException(e);
        }
    }

    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */
    @Override
    public void run() {
        try {
            while (true) {
                MessageRequest request = (MessageRequest) ois.readObject();
                boolean haveToDisconnect = false;

                if (request instanceof CommandMessageRequest) {
                    prepareAndSendCommandResponseToClient((CommandMessageRequest) request);
                } else if (request instanceof ConnectMessageRequest) {
                    boolean connected = tryConnectUser((ConnectMessageRequest) request);
                    haveToDisconnect = !connected;
                } else if (request instanceof DisconnectMessageRequest) {
                    haveToDisconnect = true;
                }
                if (haveToDisconnect) {
                    // go to block <finally>
                    return;
                }
            }
        } catch (CmdTerminalSocketException ex) {
            LOGGER.log(Level.SEVERE, "Disconnect...", ex);
        } catch (ClassNotFoundException | IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
    }

    private void sendResponseToClient(MessageResponse response) throws CmdTerminalSocketException {
        try {
            oos.writeObject(response);
        } catch (IOException ex) {
            throw new CmdTerminalSocketException(ex);
        }
    }

    private boolean tryConnectUser(ConnectMessageRequest request) throws CmdTerminalSocketException {
        boolean result = false;
        clientName = request.getClientName();

        ConnectMessageResponse response;
        synchronized (ConnectionsKeeper.INSTANCE) {
            boolean alreadyConnected = ConnectionsKeeper.INSTANCE.userAlreadyConnected(clientName);
            if (alreadyConnected) {
                response = new ConnectMessageResponse(ResponseInfo.USER_ALREADY_CONNECTED);
            } else {
                ConnectionsKeeper.INSTANCE.addServerThreadForUser(clientName, this);
                response = new ConnectMessageResponse(ResponseInfo.OK);
                LOGGER.log(Level.INFO, "User \"{0}\" is connected [host = {1}]",
                        new Object[]{clientName, address.getHostName()});
                result = true;
            }
        }
        sendResponseToClient(response);
        return result;
    }

    private void disconnect() {
        try {
            synchronized (ConnectionsKeeper.INSTANCE) {
                if (clientName != null) {
                    ConnectionsKeeper.INSTANCE.removeUser(clientName);
                }
            }
            oos.close();
            ois.close();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Something wrong with streams closing", ex);
        } finally {
            LOGGER.log(Level.INFO, "\"{0}\" is disconnected", address.getHostName());
            this.interrupt();
        }
    }

    private void prepareAndSendCommandResponseToClient(CommandMessageRequest request) throws CmdTerminalSocketException {
        String clientText = request.getCommand();
        MessageResponse response;
        if (clientText == null || clientText.isEmpty()) {
            LOGGER.log(Level.INFO, "\"{0}\" sent empty command", request.getClientName());
            response = new ErrorMessage(ResponseInfo.EMPTY_COMMAND_LINE_ERROR);
        } else {
            CommandResult cr;
            LOGGER.log(Level.INFO, "\"{0}\" sent: \"{1}\"", new Object[]{request.getClientName(), clientText});

            try {
                cr = executeShellCommand(clientText);
            } catch (IOException e) {
                response = new CommandMessageResponse(ResponseInfo.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
                sendResponseToClient(response);
                return;
            }
            if (cr.errorCode == 0) {
                response = new CommandMessageResponse(ResponseInfo.OK, cr.output);
            } else {
                response = new CommandMessageResponse(ResponseInfo.COMMAND_DEFINED_ERROR,
                        MessageFormat.format("Command finished with code {0}:\n{1}", cr.errorCode, cr.output));
            }
        }
        sendResponseToClient(response);
    }

    private CommandResult executeShellCommand(String command) throws IOException {
        StringBuilder sb = new StringBuilder();
        Process cmd = Runtime.getRuntime().exec(command);
        BufferedReader br = new BufferedReader(new InputStreamReader(cmd.getInputStream()));
        String outputLine;

        while ((outputLine = br.readLine()) != null) {
            sb.append(outputLine);
        }

        return new CommandResult(sb.toString(), cmd.exitValue());
    }

    private static class CommandResult {
        private String output;
        private int errorCode;

        public CommandResult(String output, int errorCode) {
            this.output = output;
            this.errorCode = errorCode;
        }
    }
}
