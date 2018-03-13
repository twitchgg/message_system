package me.twitchgg.message.proto.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.twitchgg.message.proto.client.top.Header;
import me.twitchgg.message.proto.client.top.Message;

import java.util.LinkedList;
import java.util.List;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/8
 */
public class MessageQueue {
    private static final int PAYLOAD_MAX_SIZE = Message.PAYLOAD_SIZE * 1024;
    private List<Message> messages = new LinkedList<>();
    private Header header;
    private ByteBuf byteBuf;

    public MessageQueue(Header header) {
        this.header = header;
        byteBuf = Unpooled.buffer(PAYLOAD_MAX_SIZE);
    }

    public int getPayLoadLength() {
        return byteBuf.readableBytes();
    }

    public void flush() {
        byte[] bytes = new byte[Message.PAYLOAD_SIZE];
        int length = getPayLoadLength();
        int count = length / Message.PAYLOAD_SIZE;
        int seq = 0;
        for (int i = 0; i < count; i++) {
            byteBuf.readBytes(bytes, 0, Message.PAYLOAD_SIZE);
            seq++;
            messages.add(generateMessage(seq, bytes));
        }
        if (length % Message.PAYLOAD_SIZE != 0) {
            bytes = new byte[length - Message.PAYLOAD_SIZE * count];
            byteBuf.readBytes(bytes, 0, bytes.length);
            seq++;
            messages.add(generateMessage(seq, bytes));
        }
        byteBuf.clear();
    }

    private Message generateMessage(int seq, byte[] bytes) {
        Header finalHeader = header.copied();
        finalHeader.setSequence(seq);
        Message message = new Message(finalHeader);
        message.setPayload(bytes);
        return message;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public MessageQueue write(byte[] data) throws Exception {
        if (byteBuf.readableBytes() + data.length >= PAYLOAD_MAX_SIZE)
            throw new Exception("payload limited");
        byteBuf.writeBytes(data);
        return this;
    }
}
