package com.mine.castile.common.events;

/**
 * https://docs.spring.io/spring/docs/5.3.0-SNAPSHOT/spring-framework-reference/core.html#context-functionality-events
 */
public interface ApplicationEvent {

    default String getId() {
        return getClass().getName();
    }

}
