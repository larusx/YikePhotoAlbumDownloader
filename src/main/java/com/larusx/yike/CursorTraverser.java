package com.larusx.yike;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 获取游标
 */
public class CursorTraverser {

    private static String traverseUrl = "https://photo.baidu.com/youai/file/v1/list?clienttype=70&need_thumbnail=1&need_filter_hidden=0";

    private HttpAgent httpAgent;

    private static ExecutorService downloadExecutorService;

    private static ExecutorService fetchDownloadUrlExecutorService;

    private Downloader downloader;

    private Remember remember;

    private Remember errorRemember;

    private boolean hasMore = true;

    private String bdstoken;

    private String cookie;

    private String cursor;

    public CursorTraverser(int threadCount,
                           String bdstoken,
                           String cookie,
                           String targetRootPath) {
        this.bdstoken = bdstoken;
        this.cookie = cookie;
        this.httpAgent = new HttpAgent(cookie);
        this.downloader = new Downloader(httpAgent, bdstoken, targetRootPath);
        this.remember = new Remember(URLUtils.mergePathAndFileName(targetRootPath, "__doneFsids.txt"));
        this.errorRemember = new Remember(URLUtils.mergePathAndFileName(targetRootPath, "__errorFsids.txt"));
        this.downloadExecutorService = Executors.newFixedThreadPool(threadCount);
        this.fetchDownloadUrlExecutorService = Executors.newFixedThreadPool(threadCount);
    }

    public void traverse() throws IOException {
        JSONObject scrollResult = httpAgent.doRequestAndParse(URLUtils.addBstoken(traverseUrl, bdstoken));
        int count = 0;
        while (hasMore) {
            cursor = scrollResult.getStr("cursor");
            hasMore = scrollResult.getInt("has_more") == 1;
            JSONArray scrollList = scrollResult.getJSONArray("list");
            for (Object item : scrollList) {
                count = process(count, item);
            }
        }
    }

    private int process(int count, Object item) {
        JSONObject file = JSONUtil.parseObj(item);
        String fsid = file.getStr("fsid");
        if (!remember.hasRemember(fsid)) {
            fetchDownloadUrlExecutorService.execute(() -> {
                fetchAndDownload(fsid);
            });
        }
        System.out.println(count++);
        return count;
    }

    private void fetchAndDownload(String fsid) {
        try {
            String downloadUrl = downloader.fetchDownloadUrl(fsid);
            if(downloadUrl == null) {
                errorRemember.add(fsid);
            } else {
                downloadExecutorService.execute(() ->{
                   download(fsid, downloadUrl);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void download(String fsid, String downloadUrl) {
        try {
            downloader.downloadFile(downloadUrl);
            remember.add(fsid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
