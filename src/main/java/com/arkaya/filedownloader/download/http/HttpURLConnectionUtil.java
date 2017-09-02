package com.arkaya.filedownloader.download.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HttpURLConnectionUtil {

    public static HttpURLConnection getConnection(String httpUrl) throws IOException {
        URL url = new URL(httpUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout((int) TimeUnit.MINUTES.toMillis(1));
        return httpURLConnection;
    }

    public static void releaseConnection(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }

}
