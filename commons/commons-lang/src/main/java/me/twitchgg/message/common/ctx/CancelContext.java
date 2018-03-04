package me.twitchgg.message.common.ctx;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class CancelContext implements Context {
    private Context parent;
    private AtomicBoolean isCancel = new AtomicBoolean(false);
    private AtomicBoolean isDone = new AtomicBoolean(false);

    public CancelContext(Context parent) {
        this.parent = parent;
    }

    @Override
    public Context getParent() {
        return parent;
    }

    @Override
    public boolean isRootContext() {
        return false;
    }

    @Override
    public boolean isDone() {
        return isDone.get();
    }

    public void cancel() {
        isCancel.set(true);
        isDone.set(true);
        if (parent != null) {
            if (parent instanceof CancelContext) {
                ((CancelContext) parent).cancel();
            }
        }
    }
}
