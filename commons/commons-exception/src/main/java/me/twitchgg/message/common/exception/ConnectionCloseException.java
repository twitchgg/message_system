package me.twitchgg.message.common.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/5
 */
public class ConnectionCloseException extends ConnectException {
    public ConnectionCloseException() {
    }

    public ConnectionCloseException(String message) {
        super(message);
    }

    public ConnectionCloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionCloseException(Throwable cause) {
        super(cause);
    }

    public ConnectionCloseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
