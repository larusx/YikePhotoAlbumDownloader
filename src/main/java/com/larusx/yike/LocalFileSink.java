package com.larusx.yike;

import cn.hutool.core.io.IoUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LocalFileSink extends AbstractFileSink {

    public LocalFileSink(String targetRootPath) {
        super.targetRootPath = targetRootPath;
    }

    @Override
    protected void internalWrite(InputStream content, String filePath) throws IOException {
        IoUtil.write(new FileOutputStream(filePath), true, IoUtil.readBytes(content));
    }
}
