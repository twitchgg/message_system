package me.twitchgg.message.test;

import me.twitchgg.message.proto.client.top.*;
import org.apache.commons.io.HexDump;
import org.junit.Before;
import org.junit.Test;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class MessageEncodeTest extends BaseTest {
    private byte[] data;

    @Before
    public void befor0() {
        String payloadMessage = "this is test message";
        Header header = new Header(
                new Version(1, 0, 0),
                PayloadType.CONTROLLER,
                EncryptionAlgorithm.AES,
                CompressionAlgorithm.BZ2,
                1
        );
        Message message = new Message(header);
        message.setPayload(payloadMessage.getBytes());
        data = message.encode();
        System.out.println("data length: " + data.length);
    }

    @Test
    public void testDecode1() throws Exception {
        Message message = new Message().decode(data);
        Header header = message.getHeader();
        assertEquals("1.0.0", header.getVersion().toString());
        assertEquals(EncryptionAlgorithm.AES, header.getEncryptionAlgorithm());
        assertEquals(CompressionAlgorithm.BZ2, header.getCompressionAlgorithm());
        assertEquals(PayloadType.CONTROLLER, header.getPayloadType());
        assertEquals(1, header.getSequence());
        HexDump.dump(message.getPayload(), message.getLength(), System.out, 0);
    }
}


