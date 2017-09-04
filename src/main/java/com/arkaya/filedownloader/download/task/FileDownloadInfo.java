package com.arkaya.filedownloader.download.task;

import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;

public interface FileDownloadInfo {
    HttpFileDownloadRequest getFileDownloadRequest();

    FileDownloadStatus getFileDownloadStatus();

}
