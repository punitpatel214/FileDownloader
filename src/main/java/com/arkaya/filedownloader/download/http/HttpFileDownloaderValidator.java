package com.arkaya.filedownloader.download.http;

import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.download.constant.FileDownloadKeyConstant;
import com.arkaya.filedownloader.download.task.FileDownloadTaskHandler;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;
import com.arkaya.filedownloader.response.http.HttpFileDownloadResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class HttpFileDownloaderValidator {

    @Autowired
    private FileDownloadTaskHandler fileDownloadTaskHandler;

    public boolean validate(HttpFileDownloadRequest fileDownloadRequest, HttpFileDownloadResponse fileDownloadResponse) {
        String fileName = StringUtils.substringAfterLast(fileDownloadRequest.getUrl(), "/");
        Path filePath = fileDownloadRequest.getFileLocation().resolve(fileName);
        boolean fileExits = Files.exists(filePath);
        if (fileExits) {
            fileDownloadResponse.setFileDownloadStatus(FileDownloadStatus.FAIL)
                    .setResponseMessage(String.format("File %s already exits, change download file name", filePath));
            return false;
        }
        String partialFile = fileDownloadRequest.getAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME);
        if (partialFile != null) {
            Path partialFilePath = Paths.get(partialFile);
            if (Files.exists(partialFilePath) && !isValidPartialFile(fileDownloadRequest, partialFile)) {
                fileDownloadResponse.setFileDownloadStatus(FileDownloadStatus.FAIL)
                        .setResponseMessage(String.format("Downloading for File %s already in Process with different request, Change download file location"
                                , filePath));
                return false;
            }
        }

        return true;
    }

    private boolean isValidPartialFile(HttpFileDownloadRequest fileDownloadRequest, String partialFile) {
        return fileDownloadTaskHandler.getFileDownloadTask(partialFile)
                .map(fileDownloadTaskDetail -> fileDownloadTaskDetail.getFileDownloadRequest()
                        .getUrl().equals(fileDownloadRequest.getUrl()))
                .orElse(true);
    }

}
