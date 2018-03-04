package me.twitchgg.message.endpoint;

import com.google.inject.Inject;
import me.twitchgg.message.common.exception.ConfigBuildException;
import me.twitchgg.message.export.config.Config;
import me.twitchgg.message.export.config.EndpointConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class DefaultEndpointConfig implements EndpointConfig {
    private static final String KEY_NIO = "ENDPOINT_ENABLE_NIO";
    private static final String KEY_PORT = "ENDPOINT_PORT";
    private static final String KEY_BIND = "ENDPOINT_BIND";
    private static final Integer DEFAULT_PORT = 6666;
    private static final Boolean DEFAULT_ENABLE_NIO = true;
    private static final String DEFAULT_BIND_ADDRESS = "0.0.0.0";
    private Map<String, Object> environment;

    @Inject
    public DefaultEndpointConfig() {
        environment = new HashMap<>();
        environment.put(KEY_PORT, DEFAULT_PORT);
        environment.put(KEY_BIND, DEFAULT_BIND_ADDRESS);
        environment.put(KEY_NIO, DEFAULT_ENABLE_NIO);
    }

    public DefaultEndpointConfig(String bind, int port) {
        environment = new HashMap<>();
        environment.put(KEY_PORT, port);
        environment.put(KEY_BIND, bind);
        environment.put(KEY_NIO, true);
    }

    @Override
    public int getPort() {
        if (environment.containsKey(KEY_PORT))
            return (Integer) environment.get(KEY_PORT);
        return DEFAULT_PORT;
    }

    @Override
    public String getBindAddress() {
        if (environment.containsKey(KEY_BIND))
            return (String) environment.get(KEY_BIND);
        return DEFAULT_BIND_ADDRESS;
    }

    @Override
    public boolean enableNio() {
        if (environment.containsKey(KEY_NIO))
            return (Boolean) environment.get(KEY_NIO);
        return DEFAULT_ENABLE_NIO;
    }

    @Override
    public Config fromJSON(String json) throws ConfigBuildException {
        throw new ConfigBuildException(new UnsupportedOperationException());
    }

    @Override
    public Config fromProperties(Properties properties) throws ConfigBuildException {
        throw new ConfigBuildException(new UnsupportedOperationException());
    }

    @Override
    public Config fromArgs(String[] args) throws ConfigBuildException {
        throw new ConfigBuildException(new UnsupportedOperationException());
    }

    @Override
    public Config fromMap(Map<String, Object> environment) throws ConfigBuildException {
        this.environment = environment;
        return this;
    }

    @Override
    public Map<String, Object> asMap() {
        return environment;
    }

    @Override
    public String getByName(String name) {
        Object obj = environment.get(name);
        if (obj == null)
            return null;
        return obj.toString();
    }
}
