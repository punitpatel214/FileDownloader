package com.arkaya.filedownloader.config;

import com.arkaya.filedownloader.download.FileDownloader;
import com.arkaya.filedownloader.download.MockFileDownloader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class TestFileDownloaderConfig extends FileDownloaderConfig {


    @Override
    @Bean
    public List<FileDownloader> fileDownloaderList() {
        return Arrays.asList(new MockFileDownloader());
    }
}