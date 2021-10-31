package com.larusx.yike;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Slf4j
public class Remember {

    private CharSink charSink;

    private ReentrantLock lock = new ReentrantLock();

    private Set<String> lines;

    private String filePath;

    public Remember(String filePath) {
        this.filePath = filePath;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                log.info(String.format("记录文件%s不存在，重新创建。", filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.info(String.format("记录文件%s已存在，直接加载。", filePath));
        }
        this.charSink = Files.asCharSink(file, Charset.defaultCharset(), FileWriteMode.APPEND);
        try {
            this.lines = Sets.newHashSet(Files.readLines(file, Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(String line) {
        runWithLock(() -> {
            try {
                charSink.writeLines(Lists.newArrayList(line));
                log.info(String.format("增加记录[%s]到记录文件[%s]", line, filePath));
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
