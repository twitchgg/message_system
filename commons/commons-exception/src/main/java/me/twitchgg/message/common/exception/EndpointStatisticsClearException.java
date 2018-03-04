package me.twitchgg.message.common.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class EndpointStatisticsClearException extends EndpointException {
    public EndpointStatisticsClearException() {
    }

    public EndpointStatisticsClearException(String message) {
        super(message);
    }

    public EndpointStatisticsClearException(String message, Throwable cause) {
        super(message, cause);
    }

    public EndpointStatisticsClearException(Throwable cause) {
        super(cause);
    }

    public EndpointStatisticsClearException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
