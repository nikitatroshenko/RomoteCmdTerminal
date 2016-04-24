package by.bsu.cmdsocket.common.entity;

import by.bsu.cmdsocket.common.enums.ResponseInfo;

import java.text.MessageFormat;

/**
 * Created by Никита on 24.04.2016.
 */
public class MessageResponse extends Message {
    private by.bsu.cmdsocket.common.enums.ResponseInfo responseInfo;

    public MessageResponse(ResponseInfo responseInfo) {
        this.responseInfo = responseInfo;
    }

    public ResponseInfo getResponseInfo() {
        return responseInfo;
    }

    public void setResponseInfo(ResponseInfo responseInfo) {
        this.responseInfo = responseInfo;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}: {1}", responseInfo.getCode(), responseInfo.getDescription());
    }
}
