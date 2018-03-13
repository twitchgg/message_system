package me.twitchgg.message.proto.client.top;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/3
 */
public class Message implements Protocol {
    public static final int PAYLOAD_SIZE = 1428;
    private Header header;
    private byte[] payload = new byte[PAYLOAD_SIZE];
    private int length;

    public Message() {
        header = new Header();
    }

    public Message(Header header) {
        this.header = header;
    }

    public int getPayloadLength() {
        return payload.length;
    }

    public byte[] encode() {
        byte[] headerData = header.encode();
        length = header.getLength();
        length += payload.length;
        byte[] bytes = new byte[length];
        System.arraycopy(headerData, 0, bytes, 0, header.getLength());
        System.arraycopy(payload, 0, bytes, header.getLength(), payload.length);
        return bytes;
    }

    public Header getHeader() {
        return header;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] data) {
        if (header != null) {
            if (!header.getCompressionAlgorithm().equals(CompressionAlgorithm.NONE)) {
//                System.out.println("CompressionAlgorithm: " + header.getCompressionAlgorithm());
                //TODO compression algorithm
            }
        }
        System.arraycopy(data, 0, payload, 0, data.length);
        length = 0;
    }

    public Message decode(byte[] data) throws Exception {
        header = new Header().decode(data);
        System.arraycopy(data, Header.HEADER_LENGTH, payload, 0, data.length - Header.HEADER_LENGTH);
        return this;
    }

    @Override
    public int getLength() {
        if (length == 0)
            return encode().length;
        return length;
    }
}
