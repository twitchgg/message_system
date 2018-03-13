package me.twitchgg.message.sdk.test;

import me.twitchgg.message.proto.client.MessageQueue;
import me.twitchgg.message.proto.client.top.*;
import me.twitchgg.message.sdk.endpoint.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/6
 */
public class MessageSendTest extends Assert {
    private Client client;

    @Before
    public void before() {
        client = Client.newBuilder()
                .withHost("127.0.0.1")
                .withPort(6666)
                .withIsEnableNio(true)
                .withIsAutoReconnect(true)
                .build();
        client.connect(true);
    }

    @Test
    public void testSendOnce() {
        Message message = buildTestMessage("test once message");
        client.send(message);
        client.send(message);
        client.close();
    }


    @Test
    public void testSendMessageQueue() throws Exception {
        Header header = new Header();
        header.setVersion(new Version(0, 0, 1));
        header.setEncryptionAlgorithm(EncryptionAlgorithm.AES);
        header.setCompressionAlgorithm(CompressionAlgorithm.BZ2);
        MessageQueue messageQueue = new MessageQueue(header);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 1000; i++) {
            String payload = "message[" + i + "] " + UUID.randomUUID().toString() + "\n";
            sb.append(payload);
            messageQueue.write(payload.getBytes());
        }
        byte[] data = sb.toString().getBytes();
        System.out.println("string bytes length: " + data.length);
        System.out.println("byte buf length: " + messageQueue.getPayLoadLength());
        messageQueue.flush();
        final AtomicBoolean flag = new AtomicBoolean(false);
        messageQueue.getMessages().forEach(message -> {
            if (!flag.get()) {
                System.out.println(System.currentTimeMillis());
                flag.set(true);
            }
            client.send(message);
        });
        client.close();
    }

    private Message buildTestMessage(String payloadMessage) {
        Header header = new Header(
                new Version(1, 0, 0),
                PayloadType.TEST,
                EncryptionAlgorithm.NONE,
                CompressionAlgorithm.NONE,
                1
        );
        return new Message(header);
    }
}
