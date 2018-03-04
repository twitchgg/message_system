package me.twitchgg.message.common.ctx;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class BackgroundContext implements Context {
    @Override
    public Context getParent() {
        return null;
    }

    @Override
    public boolean isRootContext() {
        return true;
    }

    @Override
    public boolean isDone() {
        return false;
    }
}
