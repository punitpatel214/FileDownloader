package com.arkaya.filedownloader.response;

import com.arkaya.filedownloader.constant.FileDownloadStatus;

public interface FileDownloadResponse {
    FileDownloadStatus getFileDownloadStatus();
    String getResponseMessage();
}
