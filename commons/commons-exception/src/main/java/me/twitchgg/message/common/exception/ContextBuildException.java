package me.twitchgg.message.common.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class ContextBuildException extends MessageSystemException {
    public ContextBuildException() {
    }

    public ContextBuildException(String message) {
        super(message);
    }

    public ContextBuildException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextBuildException(Throwable cause) {
        super(cause);
    }

    public ContextBuildException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
