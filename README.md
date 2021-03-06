# 一刻相册照片下载器
当前官方提供的页面选中下载只支持批量100张，如果照片很多的话下载很费劲，所以有了这个小工具。

下载流程
1. 登陆网页版的[一刻相册](https://photo.baidu.com/photo/web/home)。
2. 拿到Cookie和bdstoken，下载的时候需要这两个参数。![img.png](img.png)
3. 配置好目标路径targetRootPath，所有图片会自动下载到这个路径。
4. 以上三个参数配置完，运行Client的main方法就可以了，日志会输出当前运行到几个文件了。 
5. 在目标路径会创建两个文件，__doneFsids.txt里面记录的是下载成功的fsid，__errorFsids.txt里面记录的是失败的fsid。

```java
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
```

可调参数
1. 并发线程数，默认值可以轻松打满机器下行带宽。
2. 目标存储，目前只支持本地路径，未来支持smb协议/webdav协议。

