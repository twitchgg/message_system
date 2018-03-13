package me.twitchgg.message.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.twitchgg.message.proto.client.top.Message;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/5
 */
public class MessageFrameDecoder extends ByteToMessageDecoder {
    //    private ByteBuf buff;
    private AtomicBoolean flag = new AtomicBoolean(false);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] data = new byte[1440];
        in.readBytes(data);
        Message message = new Message().decode(data);
        out.add(message);
        if (!flag.get()) {
            System.out.println(System.currentTimeMillis());
            flag.set(true);
        }
    }
}
