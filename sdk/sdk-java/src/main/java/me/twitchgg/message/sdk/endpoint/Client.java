package me.twitchgg.message.sdk.endpoint;

import me.twitchgg.message.common.exception.ConnectException;
import me.twitchgg.message.common.exception.ConnectionCloseException;
import me.twitchgg.message.common.exception.MessageSendException;
import me.twitchgg.message.common.exception.PermissionException;
import me.twitchgg.message.proto.client.top.Message;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/5
 */
public interface Client {
    static Builder newBuilder() {
        return new Builder();
    }

    /**
     * 发送消息
     *
     * @param message 发送的消息
     * @throws MessageSendException 发送失败异常
     */
    void send(Message message) throws MessageSendException;

    /**
     * 检查是否已连接
     *
     * @return 是否已经连接
     */
    boolean isConnected();

    /**
     * @return 连接是否已经关闭
     */
    boolean isClosed();

    /**
     * 关闭连接
     *
     * @throws ConnectionCloseException 连接关闭异常
     */
    void close() throws ConnectionCloseException;

    /**
     * 连接服务器
     *
     * @param isBackground 是否在新的后台进程运行
     * @throws ConnectException 服务器连接异常
     */
    void connect(boolean isBackground) throws ConnectException;

    /**
     * 获取消息系统管理客户端
     *
     * @throws PermissionException 权限异常
     */
    void getAdmin() throws PermissionException;

    class Builder {
        private String host = "127.0.0.1";
        private int port = 6666;
        private boolean isAutoReconnect = true;
        private boolean isEnableNio = true;

        /**
         * 连接异常断开后是否重尝试连接
         *
         * @param isAutoReconnect 是否重连
         * @return
         */
        public Builder withIsAutoReconnect(boolean isAutoReconnect) {
            this.isAutoReconnect = isAutoReconnect;
            return this;
        }

        /**
         * 连接是否使用NIO
         *
         * @param isEnableNio 是否使用NIO
         * @return 客户端构建器
         */
        public Builder withIsEnableNio(boolean isEnableNio) {
            this.isEnableNio = isEnableNio;
            return this;
        }

        /**
         * 连接的服务器地址
         *
         * @param host 服务器地址
         * @return 客户端构建器
         */
        public Builder withHost(String host) {
            this.host = host;
            return this;
        }

        /**
         * 连接的端口
         *
         * @param port 端口
         * @return 客户端构建器
         */
        public Builder withPort(int port) {
            this.port = port;
            return this;
        }

        /**
         * 构建消息系统客户端
         *
         * @return 消息系统客户端
         */
        public Client build() {
            return new DefaultClient(host, port, isEnableNio, isAutoReconnect);
        }
    }
}
