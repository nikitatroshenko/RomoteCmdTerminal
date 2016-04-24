package by.bsu.cmdsocket.common.entity;

/**
 * Created by Никита on 24.04.2016.
 */
public class CommandMessageRequest extends MessageRequest {
    private final String command;

    public CommandMessageRequest(String clientName, String command) {
        super(clientName);
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
