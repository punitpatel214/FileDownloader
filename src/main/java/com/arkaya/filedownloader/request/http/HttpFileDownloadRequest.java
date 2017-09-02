package com.arkaya.filedownloader.request.http;

import com.arkaya.filedownloader.download.constant.FileDownloadKeyConstant;
import com.arkaya.filedownloader.request.FileDownloadRequest;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HttpFileDownloadRequest implements FileDownloadRequest {
    private String url;
    private Path fileLocation;
    private Map<FileDownloadKeyConstant, String> additionalDetailMap;

    public HttpFileDownloadRequest(String url, String fileLocation) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url must be not empty");
        }
        if (StringUtils.isEmpty(fileLocation)) {
            throw new IllegalArgumentException("fileLocation must be not empty");
        }
        this.url = url;
        this.fileLocation = Paths.get(fileLocation);
        this.additionalDetailMap = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public Path getFileLocation() {
        return fileLocation;
    }

    public String getAdditionalProperty(FileDownloadKeyConstant property) {
        return additionalDetailMap.get(property);
    }

    public HttpFileDownloadRequest addAdditionalProperty(FileDownloadKeyConstant property, String value) {
        additionalDetailMap.put(property, value);
        return this;
    }

    public Protocol getProtocol() {
        return Protocol.HTTP;
    }

}
