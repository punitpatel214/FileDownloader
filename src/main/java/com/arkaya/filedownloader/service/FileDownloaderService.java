package com.arkaya.filedownloader.service;

import com.arkaya.filedownloader.download.FileDownloader;
import com.arkaya.filedownloader.request.FileDownloadRequest;
import com.arkaya.filedownloader.response.FileDownloadResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileDownloaderService {

    @Resource(name = "fileDownloaderList")
    private List<FileDownloader> fileDownloaderList;

    public FileDownloadResponse startDownLoadFile(FileDownloadRequest fileDownloadRequest) {
       return fileDownloaderList.stream()
                .filter(fileDownloader -> fileDownloader.canDownLoadFile(fileDownloadRequest))
                .findAny()
                .map(fileDownloader -> fileDownloader.downloadFile(fileDownloadRequest))
                .orElseThrow(() -> new IllegalArgumentException("Cannot find File Downloader for Request"));
    }
}
