package com.arkaya.filedownloader.constant;

public enum FileDownloadStatus {
    INITIATE("File Downloading request Received"),
    DOWNLOADING("File Downloading in Process "),
    PAUSE("File Downloading paused"),
    COMPLETE("File Downloading completed"),
    FAIL("File Downloading failed");

    private final String message;

    FileDownloadStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
