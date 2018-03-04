package me.twitchgg.message.common.ctx.test;

import me.twitchgg.message.common.ctx.CancelContext;
import me.twitchgg.message.common.ctx.Context;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public class CancelContextTest extends Assert {
    private Context rootContext;

    @Before
    public void befor() {
        rootContext = Context.Builder.buildBackgroudContext();
    }

    @Test
    public void testCancelContext1() throws Exception {
        CancelContext c1 = Context.Builder.buildCancelContext(rootContext);
        TestClass1 t1 = new TestClass1("t1");
        t1.testCancelMethod(c1, 0);
        TestClass1 t2 = new TestClass1("t2");
        CancelContext c2 = Context.Builder.buildCancelContext(c1);
        t2.testCancelMethod(c2, 10);
        Thread.sleep(5000L);
        c2.cancel();
        Thread.sleep(1000L);
    }

    private static class TestClass1 {
        private String name;

        TestClass1(String name) {
            this.name = name;
        }

        void testCancelMethod(CancelContext context, int number) {
            AtomicInteger value = new AtomicInteger(number);
            new Thread(() -> {
                while (!context.isDone()) {
                    System.out.println(value.incrementAndGet());
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("[" + name + "]context is done");
            }).start();
        }
    }
}
