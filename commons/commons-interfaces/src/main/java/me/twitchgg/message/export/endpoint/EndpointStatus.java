package me.twitchgg.message.export.endpoint;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public enum EndpointStatus {
    READY(0), STOPED(1), STARTED(2);
    private int status;

    EndpointStatus(int status) {
        this.status = status;
    }
}
