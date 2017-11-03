package com.github.jupp.elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiFunction;

public class UserInputChannel<T> {
    private final BlockingQueue<T> queue = new LinkedBlockingQueue<>();

    public void publish(T input) {
        queue.add(input);
    }

    public <R> R reduceOnNew(R value, BiFunction<R, T, R> consumer) {
        List<T> newInputList = new ArrayList<>();
        queue.drainTo(newInputList);
        for (T input : newInputList) {
            value = consumer.apply(value, input);
        }
        return value;
    }
}
