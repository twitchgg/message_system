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
import me.twitchgg.message.common.exception.MessageSendException;
import me.twitchgg.message.common.exception.PermissionException;
import me.twitchgg.message.netty.MessageFrameDecoder;
import me.twitchgg.message.netty.MessageEncoder;
import me.twitchgg.message.proto.client.top.Message;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/5
 */
public class DefaultClient implements Client {
    private String host;
    private int port;
    private boolean isAutoReconnect;
    private Admin admin;
    private Bootstrap bootstrap;
    private ChannelFuture channelFuture;
    private Channel channel;
    private CancelContext context;

    DefaultClient(String host, int port,
                  boolean isEnableNio, boolean isAutoReconnect) {
        this.host = host;
        this.port = port;
        this.isAutoReconnect = isAutoReconnect;
        EventLoopGroup workerGroup;
        if (isEnableNio)
            workerGroup = new NioEventLoopGroup();
        else
            workerGroup = new OioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                DefaultClient clientRef = DefaultClient.this;
                ch.pipeline()
                        .addLast(new MessageEncoder(),
                                new MessageFrameDecoder(),
                                new DefaultEndpointHandler(clientRef));

            }
        });
    }

    @Override
    public void send(Message message) throws MessageSendException {
        if (channel != null && channel.isActive()) {
            try {
                channel.writeAndFlush(message).await(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new MessageSendException(e);
            }
        } else {
            throw new MessageSendException("Can't send message to inactive connection");
        }
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
        channelFuture = bootstrap.connect(host, port);
        try {
            channelFuture.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new ConnectException(e);
        }
        if (isAutoReconnect) {
            channelFuture.addListener(new ChannelFutureListener() {

                @Override
                public void operationComplete(ChannelFuture future) throws ConnectException {
                    if (!future.isSuccess()) {
                        future.channel().close();
                        try {
                            channelFuture = bootstrap.connect(host, port).addListener(this);
                            channelFuture.await(2, TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            throw new ConnectException(e);
                        }
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
        }
        if (channelFuture.isSuccess())
            channel = channelFuture.channel();
        else
            throw new ConnectException("channel future is not success");
        if (isBackground) {
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
                channelFuture.channel().close();
            });
        }
    }

    @Override
    public void getAdmin() throws PermissionException {

    }
}
