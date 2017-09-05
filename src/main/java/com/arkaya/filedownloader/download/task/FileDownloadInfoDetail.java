package com.arkaya.filedownloader.download.task;

import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.download.FileDownloadListener;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileDownloadInfoDetail implements FileDownloadInfo {
    private HttpFileDownloadRequest fileDownloadRequest;
    private volatile FileDownloadStatus fileDownloadStatus;
    private List<FileDownloadListener> fileDownloadListenerList;

    public FileDownloadInfoDetail(HttpFileDownloadRequest fileDownloadRequest) {
        this.fileDownloadRequest = fileDownloadRequest;
        this.fileDownloadStatus = FileDownloadStatus.INITIATE;
        this.fileDownloadListenerList = new CopyOnWriteArrayList<>();
    }

    @Override
    public HttpFileDownloadRequest getFileDownloadRequest() {
        return fileDownloadRequest;
    }

    @Override
    public FileDownloadStatus getFileDownloadStatus() {
        return fileDownloadStatus;
    }

    @Override
    public void registerFileDownloadListener(FileDownloadListener fileDownloadListener) {
        fileDownloadListenerList.add(fileDownloadListener);
    }

    public void changeFileDownloadStatus(FileDownloadStatus fileDownloadStatus) {
        this.fileDownloadStatus = fileDownloadStatus;
    }

    public List<FileDownloadListener> getFileDownloadListenerList() {
        return fileDownloadListenerList;
    }
}
