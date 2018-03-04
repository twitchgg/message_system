package me.twitchgg.message.endpoint.test;

import me.twitchgg.message.endpoint.DefaultEndpointConfig;
import me.twitchgg.message.endpoint.DefaultNettyTcpNioServer;
import me.twitchgg.message.export.config.EndpointConfig;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class NettyEndpointTestServer {
    public static void main(String[] args) throws Exception {
        EndpointConfig config = new DefaultEndpointConfig("127.0.0.1", 6666);
        DefaultNettyTcpNioServer server = new DefaultNettyTcpNioServer(config);
        server.start();
    }
}
