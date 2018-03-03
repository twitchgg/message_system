package me.twitchgg.message.proto.client.top;

/**
 * 消息使用的加密算法类型枚举
 *
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/3
 */
public enum EncryptionAlgorithm {
    NONE(0x0), DES(0x1), DES3(0x2), AES(0x3), BLOWFISH(0x4);
    private int algorithm;

    EncryptionAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }

    public String hex() {
        return "0x" + Integer.toHexString(algorithm);
    }

    byte value() {
        return (byte) algorithm;
    }
}
