package me.twitchgg.message.proto.client.top;

import me.twitchgg.message.ProtocolUtil;

/**
 * 消息头
 * 消息投包含了版本、压缩算法、加密算法、消息类型、消息序列号等必要的消息描述信息
 * 消息使用15字节大小的字节数组存储，每个消息使用MAGIC_HEADER来确定是否是一个有效的消息
 *
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/3
 */
public class Header implements Protocol {
    private static final int LENGTH_SEQ = 2;
    private static final byte[] MAGIC_HEADER = new byte[]{
            'E', 'm', 'm', 'a'
    };

    private Version version = new Version();
    private EncryptionAlgorithm encryptionAlgorithm = EncryptionAlgorithm.NONE;
    private CompressionAlgorithm compressionAlgorithm = CompressionAlgorithm.NONE;
    private PayloadType payloadType = PayloadType.PING;
    private byte[] sequence = ProtocolUtil.hexStringToByteArray("0000");
    private int length = 0;

    public Header() {

    }

    public Header(Version version,
                  EncryptionAlgorithm encryptionAlgorithm,
                  CompressionAlgorithm compressionAlgorithm, int sequence) {
        this.version = version;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.compressionAlgorithm = compressionAlgorithm;
        setSequence(sequence);
    }

    public void setSequence(int sequence) {
        if (sequence < 0 || sequence > 65535)
            return;
        String hex = ProtocolUtil.integerToHexString(sequence, LENGTH_SEQ);
        this.sequence = ProtocolUtil.hexStringToByteArray(hex);
        length = 0;
    }

    public byte[] encode() {
        int versionLength = version.getLength();
        int encryptionAlgorithmLength = 1;
        int compressionAlgorithmLength = 1;
        int payloadTypeLength = 1;

        length = MAGIC_HEADER.length + versionLength + encryptionAlgorithmLength + compressionAlgorithmLength + payloadTypeLength + LENGTH_SEQ;
        byte[] bytes = new byte[length];
        System.arraycopy(MAGIC_HEADER, 0, bytes, 0, MAGIC_HEADER.length);
        System.arraycopy(version.encode(), 0, bytes, MAGIC_HEADER.length, version.getLength());
        int index = MAGIC_HEADER.length + versionLength;
        bytes[index] = encryptionAlgorithm.value();
        index++;
        bytes[index] = compressionAlgorithm.value();
        index++;
        bytes[index] = payloadType.value();
        index++;
        System.arraycopy(sequence, 0, bytes, index, sequence.length);
        return bytes;
    }

    @Override
    public int getLength() {
        if (length == 0)
            return encode().length;
        return length;
    }
}
