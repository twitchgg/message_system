package me.twitchgg.message.sdk.endpoint;

import me.twitchgg.message.common.exception.ConnectException;
import me.twitchgg.message.common.exception.ConnectionCloseException;
import me.twitchgg.message.common.exception.PermissionException;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/5
 */
public interface Client {
    static Builder newBuilder() {
        return new Builder();
    }

    /**
     * @return 是否已经连接
     */
    boolean isConnected();

    /**
     * @return 连接是否已经关闭
     */
    boolean isClosed();

    /**
     * @throws ConnectionCloseException
     */
    void close() throws ConnectionCloseException;

    /**
     * 连接服务器
     *
     * @param isBackground
     * @throws ConnectException 服务器连接异常
     */
    void connect(boolean isBackground) throws ConnectException;

    /**
     * 获取服务器管理
     *
     * @throws PermissionException 没有管理权限异常
     */
    void getAdmin() throws PermissionException;

    class Builder {
        private String host = "127.0.0.1";
        private int port = 6666;
        private boolean isAutoReconnect = true;
        private boolean isEnableNio = true;

        /**
         * @param isAutoReconnect
         * @return
         */
        public Builder withIsAutoReconnect(boolean isAutoReconnect) {
            this.isAutoReconnect = isAutoReconnect;
            return this;
        }

        /**
         * @param isEnableNio
         * @return
         */
        public Builder withIsEnableNio(boolean isEnableNio) {
            this.isEnableNio = isEnableNio;
            return this;
        }

        /**
         * @param host
         * @return
         */
        public Builder withHost(String host) {
            this.host = host;
            return this;
        }

        /**
         * @param port
         * @return
         */
        public Builder withPort(int port) {
            this.port = port;
            return this;
        }

        /**
         * @return
         */
        public Client build() {
            return new DefaultClient(host, port, isEnableNio, isAutoReconnect);
        }
    }
}
