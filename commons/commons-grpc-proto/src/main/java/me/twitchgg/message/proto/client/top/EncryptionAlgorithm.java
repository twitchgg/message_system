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

    public static EncryptionAlgorithm valueOf(byte byteCode) {
        if (byteCode == NONE.value())
            return EncryptionAlgorithm.NONE;
        if (byteCode == DES.value())
            return EncryptionAlgorithm.DES;
        if (byteCode == DES3.value())
            return EncryptionAlgorithm.DES3;
        if (byteCode == AES.value())
            return EncryptionAlgorithm.AES;
        if (byteCode == BLOWFISH.value())
            return EncryptionAlgorithm.BLOWFISH;
        return null;
    }

    public String hex() {
        return "0x" + Integer.toHexString(algorithm);
    }

    byte value() {
        return (byte) algorithm;
    }
}
