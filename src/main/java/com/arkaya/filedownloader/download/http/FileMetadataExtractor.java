package com.arkaya.filedownloader.download.http;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;

@Component
public class FileMetadataExtractor {

    public long getFileSize(HttpURLConnection connection) throws FileDownloadException {
        try {
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode / 100 != 2) {
                throw new FileDownloadException("Invalid http response code " + responseCode);
            }
            return connection.getContentLengthLong();
        } catch (IOException e) {
            throw new FileDownloadException(e.getMessage(), e);
        } finally {
            HttpURLConnectionUtil.releaseConnection(connection);
        }

    }
}
