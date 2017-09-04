package com.arkaya.filedownloader.cli;

import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.download.constant.FileDownloadKeyConstant;
import com.arkaya.filedownloader.download.task.FileDownloadInfo;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadProgressbar {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileDownloadProgressbar.class);
    private FileDownloadInfo fileDownloadInfo;
    private final Path partialFileName;
    private final long fileSize;

    public FileDownloadProgressbar(FileDownloadInfo fileDownloadInfo) {
        HttpFileDownloadRequest fileDownloadRequest = fileDownloadInfo.getFileDownloadRequest();
        this.partialFileName = Paths.get(fileDownloadRequest.getAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME));
        this.fileSize = NumberUtils.toLong(fileDownloadRequest.getAdditionalProperty(FileDownloadKeyConstant.FILE_SIZE));
        this.fileDownloadInfo = fileDownloadInfo;
    }

    public void startProgressBar() {

        try {
            while (true) {

                if (fileDownloadInfo.getFileDownloadStatus() == FileDownloadStatus.COMPLETE) {
                    updateProgress(100);
                    break;
                }

                while (fileDownloadInfo.getFileDownloadStatus() == FileDownloadStatus.DOWNLOADING) {
                    updateProgress(calculateProcessPercentage());
                    Thread.sleep(500);
                }

                if (fileDownloadInfo.getFileDownloadStatus() == FileDownloadStatus.FAIL) {
                    System.err.println("File Downloading Fail, Please contact system admin");
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error while downloading file, reason " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int calculateProcessPercentage() throws IOException {
        long partialFileSize = Files.size(partialFileName);
        LOGGER.debug("calculate percentage for partial file size {} and file size {}", partialFileSize, fileSize);
        return (int) Math.floor(100 * partialFileSize/fileSize);
    }

    static void updateProgress(int progressPercentage) {
        StringBuilder builder = new StringBuilder("\r");
        builder.append(progressPercentage +" % [");
        int i = 0;
        for (; i <= progressPercentage/2; i++) {
            builder.append(".");
        }
        for (; i < 50; i++) {
            builder.append(" ");
        }
        builder.append("]");
        System.out.print(builder);
    }
}
