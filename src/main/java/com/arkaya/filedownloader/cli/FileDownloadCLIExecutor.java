package com.arkaya.filedownloader.cli;

import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.download.constant.FileDownloadKeyConstant;
import com.arkaya.filedownloader.download.task.FileDownloadTaskHandler;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;
import com.arkaya.filedownloader.response.FileDownloadResponse;
import com.arkaya.filedownloader.service.FileDownloaderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

@Component
public class FileDownloadCLIExecutor {

    @Autowired
    private FileDownloaderService fileDownloaderService;

    @Autowired
    private FileDownloadTaskHandler fileDownloadTaskHandler;

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter Download File URL:");
        String url = scanner.nextLine();
        while (!ResourceUtils.isUrl(url)) {
            System.err.println(String.format("Invalid url %s", url));
            System.out.print("Please enter valid URL :");
            url = scanner.nextLine();
        }

        System.out.print("Please enter File Location : ");
        String fileLocation = scanner.nextLine();
        while (!isValidFileLocation(fileLocation)) {
            System.err.println(String.format("Invalid File Location %s", fileLocation));
            System.out.print("Please provide valid File Location:");
            fileLocation = scanner.nextLine();
        }

        HttpFileDownloadRequest httpFileDownloadRequest = new HttpFileDownloadRequest(url, fileLocation);
        FileDownloadResponse fileDownloadResponse = fileDownloaderService.startDownLoadFile(httpFileDownloadRequest);
        if (fileDownloadResponse.getFileDownloadStatus() == FileDownloadStatus.FAIL) {
            System.err.println(fileDownloadResponse.getResponseMessage());
            return;
        }

        startProgressBar(httpFileDownloadRequest);
    }

    private void startProgressBar(HttpFileDownloadRequest httpFileDownloadRequest) {
        String partialFileName = httpFileDownloadRequest.getAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME);
        fileDownloadTaskHandler.getFileDownloadTask(partialFileName)
                .ifPresent(fileDownloadInfo -> new FileDownloadProgressbar(fileDownloadInfo).startProgressBar());
    }

    private boolean isValidFileLocation(String fileLocation) {
        return StringUtils.isNotBlank(fileLocation) && Files.exists(Paths.get(fileLocation));
    }

}
