package me.twitchgg.message.export.endpoint;

import me.twitchgg.message.common.exception.EndpointStartException;
import me.twitchgg.message.common.exception.EndpointStopException;

import java.util.concurrent.ExecutorService;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public interface Endpoint {

    /**
     * @throws EndpointStartException
     */
    void start() throws EndpointStartException;

    /**
     * @throws EndpointStopException
     */
    void stop() throws EndpointStopException;

    /**
     * @return
     */
    EndpointStatus getStatus();

    /**
     * @return
     */
    EndpointTransportType getTransportType();

    /**
     * @return
     */
    EndpointStatistics getStatistics();

    /**
     * @return
     */
    ExecutorService getThreadPool();

    /**
     * @return
     */
    long getUptime();
}
