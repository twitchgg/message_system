package me.twitchgg.message.common.ctx.test;

import me.twitchgg.message.common.ctx.CancelContext;
import me.twitchgg.message.common.ctx.Context;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class BaseContextTest extends Assert {
    @Test
    public void testBackgroudContext() {
        Context context = Context.Builder.buildBackgroudContext();
        assertTrue(context.isRootContext());
    }

    @Test
    public void testChildCancelContext() {
        Context root = Context.Builder.buildBackgroudContext();
        CancelContext c1 = Context.Builder.buildCancelContext(root);
        assertFalse(c1.isRootContext());
        CancelContext c2 = Context.Builder.buildCancelContext(root);
        assertFalse(c2.isRootContext());
        CancelContext c3 = Context.Builder.buildCancelContext(root);
        assertFalse(c3.isRootContext());
        CancelContext c4 = Context.Builder.buildCancelContext(c3);
        assertFalse(c4.isRootContext());
    }
}
