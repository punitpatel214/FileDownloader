package com.arkaya.filedownloader.download.http;

import org.junit.Test;

import java.net.HttpURLConnection;

import static org.junit.Assert.assertNotNull;

public class HttpURLConnectionUtilTest {

    @Test
    public void getConnection() throws Exception {
        HttpURLConnection connection = HttpURLConnectionUtil.getConnection("http://localhost:8080");
        assertNotNull(connection);
    }

    @Test
    public void releaseConnection() throws Exception {
        HttpURLConnection connection = HttpURLConnectionUtil.getConnection("http://localhost:8080");
        HttpURLConnectionUtil.releaseConnection(connection);
    }

}