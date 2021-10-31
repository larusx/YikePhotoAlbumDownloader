package com.larusx.yike;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Setter
@Slf4j
public class Counter<T> {

    private int count = 0;

    private String template;

    private Consumer<T> consumer;

    public static <T> Counter<T> wrap(Consumer<T> consumer) {
        Counter<T> counter = new Counter<>();
        counter.consumer = consumer;
        return counter;
    }

    public Counter<T> messageTemplate(String template) {
        this.template = template;
        return this;
    }

    public void execute(T t) {
        consumer.accept(t);
        log.info(String.format(template, count));
    }
}
