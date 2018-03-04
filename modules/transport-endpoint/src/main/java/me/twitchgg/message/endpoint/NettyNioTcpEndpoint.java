package me.twitchgg.message.endpoint;

import com.google.inject.Inject;
import me.twitchgg.message.common.exception.EndpointStartException;
import me.twitchgg.message.common.exception.EndpointStopException;
import me.twitchgg.message.export.config.EndpointConfig;
import me.twitchgg.message.export.endpoint.Endpoint;
import me.twitchgg.message.export.endpoint.EndpointStatistics;
import me.twitchgg.message.export.endpoint.EndpointStatus;
import me.twitchgg.message.export.endpoint.EndpointTransportType;

import java.util.concurrent.ExecutorService;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class NettyNioTcpEndpoint implements Endpoint {
    private long uptime = 0;
    private EndpointConfig config;
    private NettyTcpNioServer server;

    @Inject
    public NettyNioTcpEndpoint(EndpointConfig config, NettyTcpNioServer server) {
        this.config = config;
        this.server = server;
    }

    @Override
    public void start() throws EndpointStartException {
        uptime = System.currentTimeMillis();
    }

    @Override
    public void stop() throws EndpointStopException {

    }

    @Override
    public EndpointStatus getStatus() {
        return null;
    }

    @Override
    public EndpointTransportType getTransportType() {
        return EndpointTransportType.TCP;
    }

    @Override
    public EndpointStatistics getStatistics() {
        return null;
    }

    @Override
    public ExecutorService getThreadPool() {
        return null;
    }

    @Override
    public long getUptime() {
        return uptime;
    }
}
