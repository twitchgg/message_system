package me.twitchgg.message.proto.client.top;

import java.io.Serializable;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/3
 */
public interface Protocol extends Serializable{

    /**
     * 消息编码为字节数组
     * @return
     */
    byte[] encode();

    /**
     * 获取编码后的消息字节数组长度
     *
     * @return
     */
    int getLength();

}
