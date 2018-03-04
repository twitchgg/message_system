package me.twitchgg.message.common.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class EndpointStartException extends EndpointException{
    public EndpointStartException() {
    }

    public EndpointStartException(String message) {
        super(message);
    }

    public EndpointStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public EndpointStartException(Throwable cause) {
        super(cause);
    }

    public EndpointStartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
