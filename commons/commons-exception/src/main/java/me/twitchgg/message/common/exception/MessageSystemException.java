package me.twitchgg.message.common.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class MessageSystemException extends RuntimeException {
    public MessageSystemException() {
    }

    public MessageSystemException(String message) {
        super(message);
    }

    public MessageSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageSystemException(Throwable cause) {
        super(cause);
    }

    public MessageSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
