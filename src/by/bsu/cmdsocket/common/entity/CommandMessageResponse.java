package by.bsu.cmdsocket.common.entity;

import by.bsu.cmdsocket.common.enums.ResponseInfo;

/**
 * Created by Никита on 24.04.2016.
 */
public class CommandMessageResponse extends MessageResponse {
    private final String serverResponse;

    public CommandMessageResponse(ResponseInfo responseInfo, String serverResponse) {
        super(responseInfo);
        this.serverResponse = serverResponse;
    }

    public String getServerResponse() {
        return serverResponse;
    }

    @Override
    public String toString() {
        return super.toString() + ":\n" + serverResponse;
    }
}
