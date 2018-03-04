package me.twitchgg.message.proto.client.top;

/**
 * 消息使用的压缩算法类型枚举
 *
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/3
 */
public enum CompressionAlgorithm {
    NONE(0x0), BZ2(0x1);
    private int algorithm;

    CompressionAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }

    public static CompressionAlgorithm valueOf(byte byteCode) {
        if (byteCode == NONE.value())
            return CompressionAlgorithm.NONE;
        if (byteCode == BZ2.value())
            return CompressionAlgorithm.BZ2;
        return null;
    }

    public String hex() {
        return "0x" + Integer.toHexString(algorithm);
    }

    byte value() {
        return (byte) algorithm;
    }
}
