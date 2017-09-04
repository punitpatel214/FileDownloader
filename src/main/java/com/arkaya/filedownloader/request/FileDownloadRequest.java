package com.arkaya.filedownloader.request;

public interface FileDownloadRequest {
    enum Protocol {
        HTTP,
        FTP;
    }

    Protocol getProtocol();
}
