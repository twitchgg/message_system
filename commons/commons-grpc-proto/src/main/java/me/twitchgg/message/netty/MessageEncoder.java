package me.twitchgg.message.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.twitchgg.message.proto.client.top.Message;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/5
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        System.out.println("write message: " + msg.getHeader().getPayloadType());
        ByteBuf byteBuf = ctx.alloc().buffer(1440);
        byteBuf.writeBytes(msg.encode());
        out.writeBytes(byteBuf);
    }
}
