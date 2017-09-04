package com.arkaya.filedownloader.download.http;

/**
 * Created by punit.patel on 1/9/17.
 */
public class FileDownloadException extends Exception {
    public FileDownloadException(String message) {
        super(message);
    }

    public FileDownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}
