package com.larusx.yike;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.CharSink;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class Remember {

    private CharSink charSink;

    private ReentrantLock lock;

    private Set<String> lines;

    private String filePath;

    public Remember(String filePath) {
        this.filePath = filePath;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.lines = Sets.newHashSet(Files.readLines(new File(filePath), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(String line) {
        runWithLock(() -> {
            try {
                charSink.writeLines(Lists.newArrayList(line));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public boolean hasRemember(String line) {
        return lines.contains(line);
    }

    private <T> T runWithLock(Supplier<T> supplier) {
        try {
            lock.lock();
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }
}
