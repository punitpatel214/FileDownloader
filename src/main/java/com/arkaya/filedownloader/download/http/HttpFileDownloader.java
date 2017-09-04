package com.arkaya.filedownloader.download.http;

import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.download.FileDownloader;
import com.arkaya.filedownloader.download.constant.FileDownloadKeyConstant;
import com.arkaya.filedownloader.download.task.FileDownloadTaskHandler;
import com.arkaya.filedownloader.download.util.FileDownloadUtil;
import com.arkaya.filedownloader.request.FileDownloadRequest;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;
import com.arkaya.filedownloader.response.FileDownloadResponse;
import com.arkaya.filedownloader.response.http.HttpFileDownloadResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Path;

@Component
public class HttpFileDownloader implements FileDownloader {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpFileDownloader.class);

    @Autowired
    private HttpFileDownloaderValidator httpFileDownloaderValidator;

    @Autowired
    private FileMetadataExtractor fileMetadataExtractor;

    @Autowired
    private FileDownloadTaskHandler fileDownloadTaskHandler;

    @Override
    public boolean canDownLoadFile(FileDownloadRequest fileDownloadRequest) {
        return fileDownloadRequest.getProtocol() == FileDownloadRequest.Protocol.HTTP &&
                fileDownloadRequest instanceof HttpFileDownloadRequest;
    }

    @Override
    public FileDownloadResponse downloadFile(FileDownloadRequest fileDownloadRequest) {
        HttpFileDownloadResponse httpFileDownloadResponse = new HttpFileDownloadResponse();
        try {
            HttpFileDownloadRequest httpFileDownloadRequest = (HttpFileDownloadRequest) fileDownloadRequest;
            addPartialFileNameToRequest(httpFileDownloadRequest);

            if (!httpFileDownloaderValidator.validate(httpFileDownloadRequest, httpFileDownloadResponse)) {
                return httpFileDownloadResponse;
            }

            addFileSizeToRequest(httpFileDownloadRequest);

            fileDownloadTaskHandler.startDownloading(httpFileDownloadRequest, httpFileDownloadResponse);
            return httpFileDownloadResponse;
        } catch (Exception e) {
            LOGGER.error("Error while download File", e);
            httpFileDownloadResponse.setFileDownloadStatus(FileDownloadStatus.FAIL)
                    .setResponseMessage(e.getMessage());
        }
        return httpFileDownloadResponse;
    }

    private void addFileSizeToRequest(HttpFileDownloadRequest httpFileDownloadRequest) throws FileDownloadException, IOException {
        HttpURLConnection connection = HttpURLConnectionUtil.getConnection(httpFileDownloadRequest.getUrl());
        long fileSize = fileMetadataExtractor.getFileSize(connection);
        httpFileDownloadRequest.addAdditionalProperty(FileDownloadKeyConstant.FILE_SIZE, String.valueOf(fileSize));
    }

    private void addPartialFileNameToRequest(HttpFileDownloadRequest httpFileDownloadRequest) {
        String fileName = StringUtils.substringAfterLast(httpFileDownloadRequest.getUrl(), "/");
        String partialFileName = FileDownloadUtil.generatePartialFileName(httpFileDownloadRequest.getFileLocation(), fileName);
        httpFileDownloadRequest.addAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME, partialFileName);
    }
}
