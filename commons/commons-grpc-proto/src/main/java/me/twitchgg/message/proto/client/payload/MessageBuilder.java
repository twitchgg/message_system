package me.twitchgg.message.proto.client.payload;

import me.twitchgg.message.proto.client.top.Message;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/3
 */
public interface MessageBuilder {
    Message[] build();

    MessageBuilder withData(byte[] data);
}
