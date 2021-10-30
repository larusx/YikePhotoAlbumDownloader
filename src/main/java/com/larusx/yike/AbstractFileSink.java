package com.larusx.yike;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractFileSink implements FileSink {

    protected String targetRootPath;

    protected abstract void internalWrite(InputStream content, String filePath) throws IOException;

    @Override
    public void write(InputStream content, String fileName) throws IOException {
        String filePath = URLUtils.mergePathAndFileName(targetRootPath, fileName);

        filePath = resolveRepeatName(filePath);

        internalWrite(content, filePath);
    }

    private String resolveRepeatName(String filePath) {
        File file = new File(filePath);
        int offset = 1;
        while (file.exists()) {
            filePath = modifyFilePath(filePath, offset++);
            file = new File(filePath);
        }
        return filePath;
    }

    private static String modifyFilePath(String filePath, int postfix) {
        int dotIndex = filePath.lastIndexOf(".");
        return filePath.substring(0, dotIndex) + "(" + postfix + ")" + filePath.substring(dotIndex);
    }
}
