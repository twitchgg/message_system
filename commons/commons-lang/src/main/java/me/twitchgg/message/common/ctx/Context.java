package me.twitchgg.message.common.ctx;

import me.twitchgg.message.common.exception.ContextBuildException;

/**
 * @author TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2018/3/4
 */
public interface Context {

    /**
     * 获取父上下文
     *
     * @return 父上下文
     */
    Context getParent();

    boolean isRootContext();

    boolean isDone();

    /**
     * 上下文构建器
     */
    interface Builder {

        static BackgroundContext buildBackgroudContext() {
            return new BackgroundContext();
        }

        static CancelContext buildCancelContext(Context parent) {
            return new CancelContext(parent);
        }

        /**
         * 父上下文
         *
         * @param parent 父上下文
         * @return 上下文构建器
         */
        Builder withParent(Context parent);

        /**
         * 根据条件构建上下文
         *
         * @return 根据条件构建的上下文
         * @throws ContextBuildException 构建失败异常信息
         */
        Context build() throws ContextBuildException;
    }
}
