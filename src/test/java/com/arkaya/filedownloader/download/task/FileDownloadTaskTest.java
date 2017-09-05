package com.arkaya.filedownloader.download.task;

import com.arkaya.filedownloader.download.constant.FileDownloadKeyConstant;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class FileDownloadTaskTest {

    private FileDownloadTask fileDownloadTask;
    private String fileLocation;

    @Before
    public void setUp() throws Exception {
        this.fileLocation = File.separator + "tmp";
        HttpFileDownloadRequest httpFileDownloadRequest = new HttpFileDownloadRequest("http://localhost:8080/abc.txt", fileLocation);
        httpFileDownloadRequest.addAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME, fileLocation + File.separator + "abc.txt.partial");
        FileDownloadInfoDetail fileDownloadInfo = new FileDownloadInfoDetail(httpFileDownloadRequest);
        this.fileDownloadTask = new FileDownloadTask(fileDownloadInfo);
    }

    @Test
    public void run() throws Exception {
        Path filePath = Paths.get(fileLocation).resolve("abc.txt");
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
        fileDownloadTask.run();
        assertTrue(Files.exists(filePath));
    }

}