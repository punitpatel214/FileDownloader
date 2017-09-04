package com.arkaya.filedownloader.download.task;

import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;

public class FileDownloadInfoDetail implements FileDownloadInfo {
    private HttpFileDownloadRequest fileDownloadRequest;
    private volatile FileDownloadStatus fileDownloadStatus;

    public FileDownloadInfoDetail(HttpFileDownloadRequest fileDownloadRequest) {
        this.fileDownloadRequest = fileDownloadRequest;
        this.fileDownloadStatus = FileDownloadStatus.INITIATE;
    }

    public HttpFileDownloadRequest getFileDownloadRequest() {
        return fileDownloadRequest;
    }

    public FileDownloadStatus getFileDownloadStatus() {
        return fileDownloadStatus;
    }

    public void changeFileDownloadStatus(FileDownloadStatus fileDownloadStatus) {
        this.fileDownloadStatus = fileDownloadStatus;
    }

}
