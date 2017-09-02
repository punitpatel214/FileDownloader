package com.arkaya.filedownloader.download.task;


import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.download.constant.FileDownloadKeyConstant;
import com.arkaya.filedownloader.download.http.HttpURLConnectionUtil;
import com.arkaya.filedownloader.download.util.FileDownloadUtil;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileDownloadTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileDownloadTask.class);
    private static final int BUFFER_SIZE = 4896;
    private final String partialFileName;
    private final long fileSize;
    private final String url;
    private FileDownloadInfoDetail fileDownloadInfo;

    public FileDownloadTask(FileDownloadInfoDetail fileDownloadInfo) {
        this.fileDownloadInfo = fileDownloadInfo;
        HttpFileDownloadRequest fileDownloadRequest = fileDownloadInfo.getFileDownloadRequest();
        this.url = fileDownloadRequest.getUrl();
        this.partialFileName = fileDownloadRequest.getAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME);
        this.fileSize = NumberUtils.toLong(fileDownloadRequest.getAdditionalProperty(FileDownloadKeyConstant.FILE_SIZE));
    }

    @Override
    public void run() {
        LOGGER.debug("start downloading {} size of file {}", fileSize, partialFileName);
        try {
            changeStatus(FileDownloadStatus.DOWNLOADING);
            Path partialFilePath = Paths.get(this.partialFileName);
            if (Files.notExists(partialFilePath)) {
                Files.createFile(partialFilePath);
            }
            long partFileSize = Files.size(partialFilePath);
            while (fileDownloadInfo.getFileDownloadStatus() == FileDownloadStatus.DOWNLOADING) {
                if (partFileSize >= fileSize) {
                    Path downloadFilePath = FileDownloadUtil.fileNameFromPartialFile(partialFileName);
                    Files.move(partialFilePath, downloadFilePath);
                    LOGGER.debug("File {} download completed", downloadFilePath);
                    changeStatus(FileDownloadStatus.COMPLETE);
                    break;
                }
                long startByteRange = partFileSize;
                long endByteRange = partFileSize + BUFFER_SIZE;
                if (endByteRange > fileSize) {
                    endByteRange = fileSize;
                }
                partFileSize = endByteRange;
                downloadFile(startByteRange, endByteRange);
            }
        } catch (Exception e) {
            LOGGER.error("Error while download File",e);
            changeStatus(FileDownloadStatus.FAIL);
        }
    }

    private void changeStatus(FileDownloadStatus fileDownloadStatus) {
        fileDownloadInfo.changeFileDownloadStatus(fileDownloadStatus);
    }

    private void downloadFile(long startByteRange, long endByteRange) throws IOException {
        LOGGER.debug("Download file from {} to {}", startByteRange, endByteRange);
        HttpURLConnection connection = null;
        try {
            connection = HttpURLConnectionUtil.getConnection(url);
            connection.setRequestProperty("Range", "bytes=" + startByteRange + "-" + endByteRange);
            connection.connect();

            if (connection.getResponseCode() / 100 != 2) {
                changeStatus(FileDownloadStatus.FAIL);
                return;
            }

            try (RandomAccessFile randomAccessFile = new RandomAccessFile(partialFileName, "rw");
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream())) {
                randomAccessFile.seek(startByteRange);
                byte data[] = new byte[BUFFER_SIZE];
                while (bufferedInputStream.read(data, 0, BUFFER_SIZE) != -1) {
                    randomAccessFile.write(data);
                }
            }
        } finally {
            HttpURLConnectionUtil.releaseConnection(connection);
        }

    }

}
