package me.twitchgg.message.proto.client.top;

/**
 * 协议版本号
 * 协议分为桑格版本号，主版本、次版本、修复版本
 * 三个版本号分别用一个字节存储，所以每个版本号的大小不得超过127
 *
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/3
 */
public class Version implements Protocol {
    //协议主版本号
    private byte versionMainline = 0x01;
    //协议次版本号
    private byte versionMinor = 0x00;
    //协议修复版本号
    private byte versionFix = 0x00;

    public Version() {
    }

    public Version(int versionMainline, int versionMinor, int versionFix) {
        setVersionMainline(versionMainline);
        setVersionMinor(versionMinor);
        setVersionFix(versionFix);
    }

    public void setVersionMainline(int versionMainline) {
        if (versionMainline < 1 || versionMainline > 127)
            return;
        this.versionMainline = (byte) versionMainline;
    }

    public void setVersionMinor(int versionMinor) {
        if (versionMinor < 1 || versionMinor > 127)
            return;
        this.versionMinor = (byte) versionMinor;
    }

    public void setVersionFix(int versionFix) {
        if (versionFix < 1 || versionFix > 127)
            return;
        this.versionFix = (byte) versionFix;
    }

    @Override
    public byte[] encode() {
        return new byte[]{
                versionMainline, versionMinor, versionFix
        };
    }

    @Override
    public int getLength() {
        return 3;
    }
}
