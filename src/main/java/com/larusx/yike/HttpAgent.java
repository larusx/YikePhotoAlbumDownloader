package com.larusx.yike;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * 下载器
 */
public class HttpAgent {

    private String cookie;

    private HttpClient httpClient = HttpClientBuilder.create()
            .setMaxConnTotal(100)
            .setMaxConnPerRoute(100)
            .build();

    public HttpAgent(String cookie) {
        this.cookie = cookie;
    }

    public HttpResponse doRequest(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Cookie", cookie);
        return httpClient.execute(httpGet);
    }

    public JSONObject doRequestAndParse(String url) throws IOException {
        HttpResponse httpResponse = doRequest(url);
        String read = IoUtil.read(IoUtil.getUtf8Reader(httpResponse.getEntity().getContent()));
        ((CloseableHttpResponse) httpResponse).close();
        return JSONUtil.parseObj(read);
    }
}
