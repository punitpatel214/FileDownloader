package com.arkaya.filedownloader.download.task;

import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.download.constant.FileDownloadKeyConstant;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;
import com.arkaya.filedownloader.response.http.HttpFileDownloadResponse;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class FileDownloadTaskHandler {
    private final ExecutorService executorService;
    private Map<String, FileDownloadInfoDetail> partialFileNameToTaskMap;

    public FileDownloadTaskHandler() {
        this.partialFileNameToTaskMap = new ConcurrentHashMap<>();
        executorService = Executors.newCachedThreadPool();
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }

    public FileDownloadInfo register(HttpFileDownloadRequest httpFileDownloadRequest) {
        String partialFileName = httpFileDownloadRequest.getAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME);
        return partialFileNameToTaskMap.computeIfAbsent(partialFileName, fileName -> new FileDownloadInfoDetail(httpFileDownloadRequest));
    }

    public Optional<FileDownloadInfo> getFileDownloadTask(String partialFile) {
        return Optional.ofNullable(partialFileNameToTaskMap.get(partialFile));
    }

    public void startDownloading(HttpFileDownloadRequest httpFileDownloadRequest, HttpFileDownloadResponse response) {
        FileDownloadInfoDetail fileDownloadInfoDetail = (FileDownloadInfoDetail) register(httpFileDownloadRequest);
        if (fileDownloadInfoDetail.getFileDownloadStatus() == FileDownloadStatus.DOWNLOADING) {
            response.setFileDownloadStatus(FileDownloadStatus.DOWNLOADING)
                    .setResponseMessage(FileDownloadStatus.DOWNLOADING.getMessage());
            return;
        }
        FileDownloadTask fileDownloadTask = new FileDownloadTask(fileDownloadInfoDetail);
        executorService.execute(fileDownloadTask);
        response.setFileDownloadStatus(FileDownloadStatus.INITIATE)
                .setResponseMessage(FileDownloadStatus.INITIATE.getMessage());
    }




}
