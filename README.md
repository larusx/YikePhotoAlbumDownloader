# 一刻相册照片下载器
当前官方提供的页面选中下载只支持批量100张，如果照片很多的话下载很费劲，所以有了这个小工具。

下载流程
1. 登陆百度版的一刻相册。
2. 拿到Cookie和bdstoken，下载的时候需要这两个参数。
3. 配置好目标路径，所有图片会下载到这个路径
4. 运行Client的main方法就可以了。

```java
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
```

可调参数
1. 并发线程数，默认值可以轻松打满机器下行带宽。
2. 目标存储，目前支持本地路径，未来支持smb协议/webdav协议。

