package com.arkaya.filedownloader.response.http;

import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.response.FileDownloadResponse;

public class HttpFileDownloadResponse implements FileDownloadResponse {
    private FileDownloadStatus fileDownloadStatus;
    private String responseMessage;

    @Override
    public FileDownloadStatus getFileDownloadStatus() {
        return fileDownloadStatus;
    }

    @Override
    public String getResponseMessage() {
        return responseMessage;
    }

    public HttpFileDownloadResponse setFileDownloadStatus(FileDownloadStatus fileDownloadStatus) {
        this.fileDownloadStatus = fileDownloadStatus;
        return this;
    }

    public HttpFileDownloadResponse setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
        return this;
    }

    @Override
    public String toString() {
        return "HttpFileDownloadResponse{" +
                "fileDownloadStatus=" + fileDownloadStatus +
                ", responseMessage='" + responseMessage + '\'' +
                '}';
    }
}
