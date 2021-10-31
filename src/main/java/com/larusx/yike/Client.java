package com.larusx.yike;

import java.io.IOException;

/**
 * 下载器
 */
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

        new CursorTraverser(
                threadCount,
                bdstoken,
                cookie,
                targetRootPath
        ).traverse();
    }
}
