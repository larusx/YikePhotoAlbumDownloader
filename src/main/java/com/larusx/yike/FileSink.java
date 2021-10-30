package com.larusx.yike;

import java.io.IOException;
import java.io.InputStream;

public interface FileSink {

    void write(InputStream content, String fileName) throws IOException;
}
