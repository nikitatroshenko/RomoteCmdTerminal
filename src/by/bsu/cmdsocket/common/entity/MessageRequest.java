package by.bsu.cmdsocket.common.entity;

/**
 * Created by Никита on 24.04.2016.
 */
public class MessageRequest extends Message {
    private String clientName;

    public MessageRequest(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
