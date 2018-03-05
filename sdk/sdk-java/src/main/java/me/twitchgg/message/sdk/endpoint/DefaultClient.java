package me.twitchgg.message.sdk.endpoint;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.twitchgg.message.common.ctx.CancelContext;
import me.twitchgg.message.common.ctx.Context;
import me.twitchgg.message.common.exception.ConnectException;
import me.twitchgg.message.common.exception.ConnectionCloseException;
import me.twitchgg.message.common.exception.PermissionException;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/5
 */
public class DefaultClient implements Client {
    private String host;
    private int port;
    private boolean isAutoReconnect;
    private boolean isEnableNio;
    private Admin admin;
    private Bootstrap bootstrap;
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;
    private Channel channel;
    private CancelContext context;

    DefaultClient(String host, int port,
                  boolean isEnableNio, boolean isAutoReconnect) {
        this.host = host;
        this.port = port;
        this.isEnableNio = isEnableNio;
        this.isAutoReconnect = isAutoReconnect;

        if (isEnableNio)
            workerGroup = new NioEventLoopGroup();
        else
            workerGroup = new OioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                DefaultClient clientRef = DefaultClient.this;
                ch.pipeline().addLast(new DefaultEndpointHandler(clientRef));
            }
        });
    }

    @Override
    public boolean isConnected() {
        return !isClosed();
    }

    @Override
    public boolean isClosed() {
        return !channelFuture.channel().isActive();
    }

    @Override
    public void close() throws ConnectionCloseException {
        if (context != null)
            context.cancel();
        else {
            channelFuture.channel().close();
        }
    }

    @Override
    public void connect(boolean isBackground) throws ConnectException {
        try {
            channelFuture = bootstrap.connect(host, port);
            channelFuture.addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        future.channel().close();
                        bootstrap.connect(host, port).addListener(this);
                    } else {
                        channel = future.channel();
                        channel.closeFuture().addListener((ChannelFutureListener) future1 -> scheduleConnect(5));

                    }
                }

                private void scheduleConnect(long millis) {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            connect(true);
                        }
                    }, millis);
                }
            });
            if (!isBackground)
                channelFuture = channelFuture.sync();
            else {
                ExecutorService backgroundService = Executors.newSingleThreadExecutor();
                Context backgroundContext = Context.Builder.buildBackgroudContext();
                context = Context.Builder.buildCancelContext(backgroundContext);
                backgroundService.submit(() -> {
                    while (!context.isDone()) {
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        channelFuture.awaitUninterruptibly().sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (InterruptedException e) {
            throw new ConnectException(e);
        }
    }

    @Override
    public void getAdmin() throws PermissionException {

    }
}
