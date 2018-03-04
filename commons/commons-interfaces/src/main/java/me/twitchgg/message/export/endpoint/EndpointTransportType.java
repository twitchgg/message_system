package me.twitchgg.message.export.endpoint;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public enum EndpointTransportType {
    TCP(0), UDP(1);
    private int type;

    EndpointTransportType(int type) {
        this.type = type;
    }
}
