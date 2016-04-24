package by.bsu.cmdsocket.common.enums;

/**
 * Created by Никита on 24.04.2016.
 */
public enum ResponseInfo {
    OK(200, "OK"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    USER_ALREADY_CONNECTED(600, "User already connected"),
    COMMAND_DEFINED_ERROR(700, "Execution of the command caused an error"),
    EMPTY_COMMAND_LINE_ERROR(800, "Command line is empty");

    private final int code;
    private final String description;

    ResponseInfo(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
