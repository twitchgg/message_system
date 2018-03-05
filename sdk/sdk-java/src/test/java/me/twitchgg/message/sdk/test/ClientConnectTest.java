package me.twitchgg.message.sdk.test;

import me.twitchgg.message.common.exception.MessageSystemException;
import me.twitchgg.message.sdk.endpoint.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/5
 */
public class ClientConnectTest extends Assert {
    private Client client;

    @Before
    public void before() {
        client = Client.newBuilder()
                .withHost("127.0.0.1")
                .withPort(6666)
                .withIsEnableNio(true)
                .withIsAutoReconnect(true)
                .build();
    }

    @Test
    public void testClientBuilder() {
        assertNotNull(client);
    }

    @Test
    public void testConnection() throws MessageSystemException {
        client.connect(true);
        assertTrue(true);
        assertFalse(client.isClosed());
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.close();
        assertTrue(client.isClosed());
    }

    @Test
    public void testReConnection() throws MessageSystemException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        client.connect(false);
        countDownLatch.await(1, TimeUnit.MINUTES);
    }
}
