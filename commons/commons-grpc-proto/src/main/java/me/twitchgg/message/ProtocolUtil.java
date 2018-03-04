package me.twitchgg.message;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/3
 */
public class ProtocolUtil {
    public static byte[] hexStringToByteArray(String s) {
        s = s.replace(" ", "");
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String integerToHexString(int number, int byteSize) {
        int hexSize = byteSize * 2;
        String hex = Integer.toHexString(number);
        if (hex.length() >= hexSize)
            return hex;
        StringBuilder fillHex = new StringBuilder();
        for (int i = 0; i < hexSize - hex.length(); i++) {
            fillHex.append("0");
        }
        return fillHex + hex;
    }
}
