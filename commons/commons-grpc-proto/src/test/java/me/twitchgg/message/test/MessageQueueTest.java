package me.twitchgg.message.test;

import me.twitchgg.message.proto.client.MessageQueue;
import me.twitchgg.message.proto.client.top.CompressionAlgorithm;
import me.twitchgg.message.proto.client.top.EncryptionAlgorithm;
import me.twitchgg.message.proto.client.top.Header;
import me.twitchgg.message.proto.client.top.Version;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/8
 */
public class MessageQueueTest extends Assert {
    @Test
    public void testEncode() throws Exception {
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
        messageQueue.getMessages().forEach(message -> {
            System.out.print(new String(message.getPayload()));
        });
        System.out.println();
    }
}
