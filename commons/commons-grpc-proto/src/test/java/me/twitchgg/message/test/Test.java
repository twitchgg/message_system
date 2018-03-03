package me.twitchgg.message.test;

import me.twitchgg.message.proto.client.top.*;
import org.apache.commons.io.HexDump;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/3
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Version version = new Version(1, 1, 1);
        Header header = new Header(version, EncryptionAlgorithm.NONE, CompressionAlgorithm.NONE, 0);
        Message message = new Message(header);
//        message.setPayload(new byte[]{0x05, 0x02, 0x00, 0x01, 0x03, 0x01, 0x04});
        HexDump.dump(message.encode(), message.getLength(), System.out, 0);
        System.out.println(message.getLength());
    }
}
