package me.twitchgg.message.export.config;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public interface EndpointConfig extends Config {
    int getPort();

    String getBindAddress();

    boolean enableNio();
}
