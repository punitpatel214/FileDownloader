package com.arkaya.filedownloader.cli;

import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.download.FileDownloadEvent;
import com.arkaya.filedownloader.download.FileDownloadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Callable;

public class FileDownloadProgressbar implements FileDownloadListener, Callable<FileDownloadStatus> {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileDownloadProgressbar.class);
    private FileDownloadStatus fileDownloadStatus;
    private long fileSize;
    private long partialFileSize;

    public FileDownloadProgressbar() {
        this.fileDownloadStatus = FileDownloadStatus.INITIATE;
    }

    @Override
    public void onDownloadingFile(FileDownloadEvent fileDownloadEvent) {
        this.fileDownloadStatus = fileDownloadEvent.getFileDownloadStatus();
        this.fileSize = fileDownloadEvent.getFileSize();
        this.partialFileSize = fileDownloadEvent.getPartialFileSize();
    }

    @Override
    public FileDownloadStatus call() throws Exception {
        try {
            updateProgress(0);
            while (true) {
                Thread.sleep(500);
                switch (fileDownloadStatus) {
                    case INITIATE:
                        break;
                    case DOWNLOADING:
                        updateProgress(calculateProcessPercentage());
                        break;
                    case COMPLETE:
                        updateProgress(calculateProcessPercentage());
                        return fileDownloadStatus;
                    case FAIL:
                        System.err.println("File Downloading Fail, Please contact system admin");
                        return fileDownloadStatus;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error while downloading file", e);
            e.printStackTrace();
            System.err.println("Error while downloading file, Reason :" + e.getMessage());
        }
        return FileDownloadStatus.FAIL;
    }

    private int calculateProcessPercentage() throws IOException {
        LOGGER.debug("calculate percentage for partial file size {} and file size {}", partialFileSize, fileSize);
        return (int) Math.floor(100 * partialFileSize / fileSize);
    }


    static void updateProgress(int progressPercentage) {
        StringBuilder builder = new StringBuilder("\r");
        builder.append(progressPercentage + " % [");
        int i = 0;
        for (; i <= progressPercentage / 2; i++) {
            builder.append(".");
        }
        for (; i < 50; i++) {
            builder.append(" ");
        }
        builder.append("]");
        System.out.print(builder);
    }
}
