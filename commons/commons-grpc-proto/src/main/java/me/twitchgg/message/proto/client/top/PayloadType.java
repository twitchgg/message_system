package me.twitchgg.message.proto.client.top;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/3
 */
public enum PayloadType {
    PING(0x0), CONTROLLER(0x1), MESSAGE(0x2);
    private int payloadType;

    PayloadType(int payloadType) {
        this.payloadType = payloadType;
    }

    public static PayloadType valueOf(byte byteCode) {
        if (byteCode == PING.value())
            return PayloadType.PING;
        if (byteCode == CONTROLLER.value())
            return PayloadType.CONTROLLER;
        if (byteCode == MESSAGE.value())
            return PayloadType.MESSAGE;
        return null;
    }

    public String hex() {
        return "0x" + Integer.toHexString(payloadType);
    }

    byte value() {
        return (byte) payloadType;
    }
}
