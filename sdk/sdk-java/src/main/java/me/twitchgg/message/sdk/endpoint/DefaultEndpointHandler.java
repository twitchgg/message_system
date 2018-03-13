package me.twitchgg.message.sdk.endpoint;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.twitchgg.message.proto.client.top.Message;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/5
 */
public class DefaultEndpointHandler extends ChannelInboundHandlerAdapter {
    private DefaultClient client;

    DefaultEndpointHandler(DefaultClient client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
