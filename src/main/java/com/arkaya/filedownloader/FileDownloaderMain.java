package com.arkaya.filedownloader;

import com.arkaya.filedownloader.cli.FileDownloadCLIExecutor;
import com.arkaya.filedownloader.config.FileDownloaderConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class FileDownloaderMain {

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(FileDownloaderConfig.class);
        System.out.println("**********************************");
        System.out.println("**  File Downloader Started     **");
        System.out.println("**********************************");
        System.out.println();
        FileDownloadCLIExecutor fileDownloadCLIExecutor = context.getBean(FileDownloadCLIExecutor.class);
        fileDownloadCLIExecutor.execute();
        context.close();
    }
}
