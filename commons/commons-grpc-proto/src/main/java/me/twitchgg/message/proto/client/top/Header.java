package me.twitchgg.message.proto.client.top;

import me.twitchgg.message.ProtocolUtil;

import java.math.BigInteger;

/**
 * 消息头
 * 消息投包含了版本、压缩算法、加密算法、消息类型、消息序列号等必要的消息描述信息
 * 消息使用15字节大小的字节数组存储，每个消息使用MAGIC_HEADER来确定是否是一个有效的消息
 *
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/3
 */
public class Header implements Protocol {
    static final int HEADER_LENGTH = 12;
    private static final int LENGTH_SEQ = 2;
    private static final byte[] MAGIC_HEADER = new byte[]{
            'e', 'm', 'm', 'a'
    };
    private Version version = new Version();
    private EncryptionAlgorithm encryptionAlgorithm = EncryptionAlgorithm.NONE;
    private CompressionAlgorithm compressionAlgorithm = CompressionAlgorithm.NONE;
    private PayloadType payloadType = PayloadType.TEST;
    private byte[] sequence = ProtocolUtil.hexStringToByteArray("0000");
    private int length = 0;

    public Header() {

    }

    public Header(Version version,
                  PayloadType payloadType,
                  EncryptionAlgorithm encryptionAlgorithm,
                  CompressionAlgorithm compressionAlgorithm, int sequence) {
        this.version = version;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.compressionAlgorithm = compressionAlgorithm;
        this.payloadType = payloadType;
        setSequence(sequence);
    }

    public Header copied() {
        Version finalVersion = new Version(
                this.getVersion().getMainline(),
                this.getVersion().getMinor(),
                this.getVersion().getFix()
        );
        return new Header(
                finalVersion,
                this.getPayloadType(),
                this.getEncryptionAlgorithm(),
                this.getCompressionAlgorithm(), getSequence()
        );
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

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public EncryptionAlgorithm getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(EncryptionAlgorithm algorithm) {
        this.encryptionAlgorithm = algorithm;
    }

    public CompressionAlgorithm getCompressionAlgorithm() {
        return compressionAlgorithm;
    }

    public void setCompressionAlgorithm(CompressionAlgorithm algorithm) {
        this.compressionAlgorithm = algorithm;
    }

    public PayloadType getPayloadType() {
        return payloadType;
    }

    public int getSequence() {
        StringBuilder sb = new StringBuilder();
        for (byte b : sequence) {
            sb.append(String.format("%02X", b));
        }
        return new BigInteger(sb.toString(), 16).intValue();
    }

    public void setSequence(int sequence) {
        if (sequence < 0 || sequence > 65535)
            return;
        String hex = ProtocolUtil.integerToHexString(sequence, LENGTH_SEQ);
        this.sequence = ProtocolUtil.hexStringToByteArray(hex);
        length = 0;
    }

    public Header decode(byte[] data) throws Exception {
        byte[] header = new byte[HEADER_LENGTH];
        System.arraycopy(data, 0, header, 0, header.length);
        byte[] magicCode = new byte[MAGIC_HEADER.length];
        System.arraycopy(header, 0, magicCode, 0, MAGIC_HEADER.length);
        if (!new String(magicCode).equals(new String(MAGIC_HEADER)))
            throw new Exception("parse magic code error");
        version = new Version().decode(header, MAGIC_HEADER.length);
        int index = MAGIC_HEADER.length + version.getLength();
        encryptionAlgorithm = EncryptionAlgorithm.valueOf(header[index]);
        if (encryptionAlgorithm == null)
            throw new Exception("parse encryption algorithm error: " + header[index]);
        index++;
        compressionAlgorithm = CompressionAlgorithm.valueOf(header[index]);
        if (compressionAlgorithm == null)
            throw new Exception("parse compression algorithm error: " + header[index]);
        index++;
        payloadType = PayloadType.valueOf(header[index]);
        if (payloadType == null)
            throw new Exception("parse payload type error: " + header[index]);
        index++;
        sequence = new byte[LENGTH_SEQ];
        System.arraycopy(header, index, sequence, 0, LENGTH_SEQ);
        return this;
    }
}


