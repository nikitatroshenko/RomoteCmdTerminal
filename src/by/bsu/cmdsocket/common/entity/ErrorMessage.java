package by.bsu.cmdsocket.common.entity;

import by.bsu.cmdsocket.common.enums.ResponseInfo;

/**
 * Created by Никита on 24.04.2016.
 */
public class ErrorMessage extends MessageResponse {
    public ErrorMessage(ResponseInfo responseInfo) {
        super(responseInfo);
    }
}
