package ua.social.network.messageservice.listener;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author Mykola Yashchenko
 */
public interface QueueListener extends InitializingBean {
    void listen();

    default void afterPropertiesSet() {
        final Thread thread = new Thread(this::listen);
        thread.setDaemon(true);
        thread.start();
    }
}
