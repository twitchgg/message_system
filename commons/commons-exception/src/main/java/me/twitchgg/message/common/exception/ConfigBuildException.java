package me.twitchgg.message.common.exception;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class ConfigBuildException extends MessageSystemException {
    public ConfigBuildException() {
    }

    public ConfigBuildException(String message) {
        super(message);
    }

    public ConfigBuildException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigBuildException(Throwable cause) {
        super(cause);
    }

    public ConfigBuildException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
