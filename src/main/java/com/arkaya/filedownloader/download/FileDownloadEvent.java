package com.arkaya.filedownloader.download;

import com.arkaya.filedownloader.constant.FileDownloadStatus;

/**
 * Created by punit.patel on 1/9/17.
 */
public class FileDownloadEvent {
    private long fileSize;
    private long partialFileSize;
    private FileDownloadStatus fileDownloadStatus;

    public FileDownloadEvent(FileDownloadStatus fileDownloadStatus, long fileSize, long partialFileSize) {
        this.fileDownloadStatus = fileDownloadStatus;
        this.fileSize = fileSize;
        this.partialFileSize = partialFileSize;
    }

    public long getFileSize() {
        return fileSize;
    }

    public long getPartialFileSize() {
        return partialFileSize;
    }

    public FileDownloadStatus getFileDownloadStatus() {
        return fileDownloadStatus;
    }
}
