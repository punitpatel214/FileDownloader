package com.arkaya.filedownloader.download.task;

import com.arkaya.filedownloader.config.TestFileDownloaderConfig;
import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.download.constant.FileDownloadKeyConstant;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;
import com.arkaya.filedownloader.response.http.HttpFileDownloadResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestFileDownloaderConfig.class)
public class FileDownloadTaskHandlerTest {

    @Autowired
    private FileDownloadTaskHandler fileDownloadTaskHandler;



    @Test
    public void startDownloadingShouldReturnDownloadingStatus() throws Exception {
        assertNotNull(fileDownloadTaskHandler);
        HttpFileDownloadRequest httpFileDownloadRequest = new HttpFileDownloadRequest("http://localhost:18080/abc.txt", String.join(File.separator, "tmp"));
        httpFileDownloadRequest.addAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME, String.join(File.separator, "tmp", "abc.txt.partial"));
        HttpFileDownloadResponse httpFileDownloadResponse = new HttpFileDownloadResponse();

        fileDownloadTaskHandler.register(httpFileDownloadRequest);
        fileDownloadTaskHandler.getFileDownloadTask(httpFileDownloadRequest.getAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME))
                .ifPresent(fileDownloadInfo ->  ((FileDownloadInfoDetail)fileDownloadInfo).changeFileDownloadStatus(FileDownloadStatus.DOWNLOADING));
        fileDownloadTaskHandler.startDownloading(httpFileDownloadRequest, httpFileDownloadResponse);
        assertEquals(FileDownloadStatus.DOWNLOADING, httpFileDownloadResponse.getFileDownloadStatus());
    }

    @Test
    public void startDownloadingShouldReturnInitiateStatus() throws Exception {
        assertNotNull(fileDownloadTaskHandler);
        HttpFileDownloadRequest httpFileDownloadRequest = new HttpFileDownloadRequest("http://localhost:18080/abc.txt", String.join(File.separator, "tmp"));
        httpFileDownloadRequest.addAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME, String.join(File.separator, "tmp", "abc.txt.partial"));
        HttpFileDownloadResponse httpFileDownloadResponse = new HttpFileDownloadResponse();
        fileDownloadTaskHandler.startDownloading(httpFileDownloadRequest, httpFileDownloadResponse);
        assertEquals(FileDownloadStatus.INITIATE, httpFileDownloadResponse.getFileDownloadStatus());
    }
}