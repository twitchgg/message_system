package me.twitchgg.message.export.endpoint;

import me.twitchgg.message.common.exception.EndpointStatisticsClearException;

import java.math.BigDecimal;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public interface EndpointStatistics {

    /**
     *
     * @return
     */
    int getConnectionsCount();

    /**
     *
     * @return
     */
    long getlastAccessTimestemp();

    /**
     *
     * @throws EndpointStatisticsClearException
     */
    void clear() throws EndpointStatisticsClearException;

    /**
     *
     * @return
     */
    long getDropedCount();

    /**
     *
     * @return
     */
    BigDecimal getTotalReceivedCount();
}
