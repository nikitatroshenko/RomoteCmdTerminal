package by.bsu.cmdsocket.common.exceptions;

/**
 * Created by Никита on 24.04.2016.
 */
public class CmdTerminalSocketException extends Exception {
    /**
     * Constructs a new {@code CmdTerminalException} that includes the current stack trace.
     */
    public CmdTerminalSocketException() {
        super();
    }

    /**
     * Constructs a new {@code CmdTerminalException} with the current stack trace and the
     * specified detail message.
     *
     * @param detailMessage the detail message for this exception.
     */
    public CmdTerminalSocketException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code CmdTerminalException} with the current stack trace, the
     * specified detail message and the specified cause.
     *
     * @param detailMessage the detail message for this exception.
     * @param throwable
     */
    public CmdTerminalSocketException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code CmdTerminalException} with the current stack trace and the
     * specified cause.
     *
     * @param throwable the cause of this exception.
     */
    public CmdTerminalSocketException(Throwable throwable) {
        super(throwable);
    }
}
