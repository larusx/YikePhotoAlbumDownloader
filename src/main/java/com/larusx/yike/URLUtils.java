package com.larusx.yike;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import java.io.File;

public abstract class URLUtils {

    public static String addBstoken(String url, String bstoken) {
        return addParam(url, "bstoken", bstoken);
    }

    public static String addCursor(String url, String cursor) {
        return addParam(url, "cursor", cursor);
    }

    public static String addParam(String url, String key, String value) {
        return url + "&" + key + "=" + value;
    }

    public static String mergePathAndFileName(String path, String fileName) {
        return path + File.separator + fileName;
    }

    public static String getFileName(HttpResponse response) {
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    try {
                        filename = param.getValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return filename;
    }
}
