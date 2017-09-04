package com.arkaya.filedownloader.config;

import com.arkaya.filedownloader.download.FileDownloader;
import com.arkaya.filedownloader.download.http.HttpFileDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;

/**
 * Created by punit.patel on 28/8/17.
 */
@ComponentScan(basePackages = "com.arkaya.filedownloader")
public class FileDownloaderConfig {

    @Autowired
    private HttpFileDownloader httpFileDownloader;

    @Bean
    public List<FileDownloader> fileDownloaderList() {
        return Arrays.asList(httpFileDownloader);
    }
}
