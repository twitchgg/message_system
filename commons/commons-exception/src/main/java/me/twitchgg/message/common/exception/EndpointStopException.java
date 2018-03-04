package me.twitchgg.message.common.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class EndpointStopException extends EndpointException{
    public EndpointStopException() {
    }

    public EndpointStopException(String message) {
        super(message);
    }

    public EndpointStopException(String message, Throwable cause) {
        super(message, cause);
    }

    public EndpointStopException(Throwable cause) {
        super(cause);
    }

    public EndpointStopException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
