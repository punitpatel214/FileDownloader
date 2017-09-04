package com.arkaya.filedownloader.download;

import com.arkaya.filedownloader.request.FileDownloadRequest;
import com.arkaya.filedownloader.response.FileDownloadResponse;

public interface FileDownloader {
    boolean canDownLoadFile(FileDownloadRequest fileDownloadRequest);

    FileDownloadResponse downloadFile(FileDownloadRequest fileDownloadRequest);
}
