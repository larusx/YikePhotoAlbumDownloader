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

        new CursorTraverser(
                40,
                bdstoken,
                cookie,
                targetRootPath
        ).traverse();
    }
}
