package me.twitchgg.message.export.config;

import me.twitchgg.message.common.exception.ConfigBuildException;

import java.util.Map;
import java.util.Properties;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public interface Config {
    Config fromJSON(String json) throws ConfigBuildException;

    Config fromProperties(Properties properties) throws ConfigBuildException;

    Config fromArgs(String[] args) throws ConfigBuildException;

    Config fromMap(Map<String, Object> environments) throws ConfigBuildException;

    Map<String, Object> asMap();

    String getByName(String name);
}
