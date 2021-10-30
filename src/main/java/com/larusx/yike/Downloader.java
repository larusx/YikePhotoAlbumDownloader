package com.larusx.yike;

import cn.hutool.json.JSONObject;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;

public class Downloader {

    private HttpAgent httpAgent;

    private FileSink fileSink;

    private String bdstoken;

    private static String url = "https://photo.baidu.com/youai/file/v2/download?clienttype=70&fsid=";

    public Downloader(HttpAgent httpAgent, String bdstoken, String targetRootPath) {
        this.httpAgent = httpAgent;
        this.bdstoken = bdstoken;
        if (targetRootPath.startsWith("smb://")) {
            this.fileSink = new SMBFileSink(targetRootPath);
        } else {
            this.fileSink = new LocalFileSink(targetRootPath);
        }
    }

    public String fetchDownloadUrl(String fsid) throws IOException {
        JSONObject result = httpAgent.doRequestAndParse(URLUtils.addBstoken(url + fsid, bdstoken));
        return result.getStr("dlink");
    }

    public void downloadFile(String dlink) throws IOException {
        HttpResponse httpResponse = httpAgent.doRequest(dlink);
        String fileName = URLUtils.getFileName(httpResponse);
        InputStream content = httpResponse.getEntity().getContent();
        fileSink.write(content, fileName);
    }
}
