package me.twitchgg.message.endpoint;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import me.twitchgg.message.export.config.EndpointConfig;
import me.twitchgg.message.netty.MessageFrameDecoder;
import me.twitchgg.message.netty.MessageEncoder;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
@Singleton
public class DefaultNettyTcpNioServer implements NettyTcpNioServer {
    private EndpointConfig config;
    private ServerBootstrap bootstrap;

    @Inject
    public DefaultNettyTcpNioServer(EndpointConfig config) {
        this.config = config;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new DiscardServerHandler());
                            ch.pipeline().addLast(new FixedLengthFrameDecoder(1440));
                            ch.pipeline().addLast(new MessageEncoder());
                            ch.pipeline().addLast(new MessageFrameDecoder());
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);
            ChannelFuture f = bootstrap.bind(config.getBindAddress(), config.getPort()).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
