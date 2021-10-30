package com.larusx.yike;

import java.io.IOException;
import java.io.InputStream;

public class SMBFileSink extends AbstractFileSink {

    public SMBFileSink(String targetRootPath) {
        super.targetRootPath = targetRootPath;
    }

    @Override
    protected void internalWrite(InputStream content, String filePath) throws IOException {

    }
}
