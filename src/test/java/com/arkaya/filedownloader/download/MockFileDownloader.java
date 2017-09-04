package com.arkaya.filedownloader.download;

import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.download.http.HttpFileDownloader;
import com.arkaya.filedownloader.request.FileDownloadRequest;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;
import com.arkaya.filedownloader.response.FileDownloadResponse;

public class MockFileDownloader extends HttpFileDownloader {
    public static final String START_URL = "http://localhost:18080/downloadFile/testStart.db";
    public static final String DOWNLOADING_URL = "http://localhost:18080/downloadFile/testResume.db";
    public static final String PAUSE_URL = "http://localhost:18080/downloadFile/testPause.db";
    public static final String COMPLETE_URL = "http://localhost:18080/downloadFile/testComplete.db";
    public static final String FAIL_URL = "http://localhost:18080/downloadFile/testFail.db";

    public MockFileDownloader() {
    }

    @Override
    public FileDownloadResponse downloadFile(FileDownloadRequest fileDownloadRequest) {
        HttpFileDownloadRequest httpFileDownloadRequest = (HttpFileDownloadRequest) fileDownloadRequest;
        FileDownloadStatus fileDownloadStatus = getFileDownloadStatus(httpFileDownloadRequest);
        return new FileDownloadResponse() {
            @Override
            public FileDownloadStatus getFileDownloadStatus() {
                return fileDownloadStatus;
            }

            @Override
            public String getResponseMessage() {
                return fileDownloadStatus.getMessage();
            }
        };
    }

    private FileDownloadStatus getFileDownloadStatus(HttpFileDownloadRequest httpFileDownloadRequest) {
        switch (httpFileDownloadRequest.getUrl()) {
            case START_URL :
                return FileDownloadStatus.INITIATE;
            case DOWNLOADING_URL:
                return FileDownloadStatus.DOWNLOADING;
            case PAUSE_URL:
                return FileDownloadStatus.PAUSE;
            case COMPLETE_URL:
                return FileDownloadStatus.COMPLETE;
            default:
                return FileDownloadStatus.FAIL;

        }
    }
}