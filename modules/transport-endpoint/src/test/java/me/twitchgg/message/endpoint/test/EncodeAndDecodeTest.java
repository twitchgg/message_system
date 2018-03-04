package me.twitchgg.message.endpoint.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import me.twitchgg.message.endpoint.MessageDecoder;
import me.twitchgg.message.endpoint.MessageEncoder;
import me.twitchgg.message.proto.client.top.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/5
 */
public class EncodeAndDecodeTest extends Assert {
    private Message message;

    @Before
    public void before() {
        String payloadMessage = "this is test netty message";
        Header header = new Header(
                new Version(1, 0, 0),
                PayloadType.CONTROLLER,
                EncryptionAlgorithm.AES,
                CompressionAlgorithm.BZ2,
                1
        );
        message = new Message(header);
        message.setPayload(payloadMessage.getBytes());
    }

    @Test
    public void test() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(new MessageEncoder(), new MessageDecoder());
        channel.writeAndFlush(message);
        ByteBuf read = channel.readOutbound();
        channel.writeInbound(read);
        Message returned = channel.readInbound();
        assertEquals("1.0.0", returned.getHeader().getVersion().toString());
        assertEquals(EncryptionAlgorithm.AES, returned.getHeader().getEncryptionAlgorithm());
    }
}
