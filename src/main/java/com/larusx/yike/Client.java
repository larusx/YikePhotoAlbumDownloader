package com.larusx.yike;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


/**
 * 下载器
 */
@Slf4j
public class Client {

    public static void main(String[] args) throws IOException {
        // Cookie
        String cookie = "";
        // bdstoken
        String bdstoken = "";
        // 目标路径
        String targetRootPath = "";
        // 并发下载线程数
        int threadCount = 40;

        log.info("开始下载, 请关注日志。");
        new CursorTraverser(
                threadCount,
                bdstoken,
                cookie,
                targetRootPath
        ).traverse();
    }
}
