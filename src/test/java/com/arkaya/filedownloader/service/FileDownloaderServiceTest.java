package com.arkaya.filedownloader.service;

import com.arkaya.filedownloader.config.TestFileDownloaderConfig;
import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.download.MockFileDownloader;
import com.arkaya.filedownloader.request.FileDownloadRequest;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;
import com.arkaya.filedownloader.response.FileDownloadResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestFileDownloaderConfig.class)
public class FileDownloaderServiceTest {

    private static final String FILE_LOCATION = "test_start.db";

    @Autowired
    private FileDownloaderService fileDownloaderService;

    @Test(expected = IllegalArgumentException.class)
    public void startDownloadFileShouldThrowIllegalArgumentException() {
        assertNotNull(fileDownloaderService);
        fileDownloaderService.startDownLoadFile(() -> FileDownloadRequest.Protocol.FTP);
    }

    @Test
    public void testStartDownloadFile() throws Exception {
        testStartDownload(MockFileDownloader.START_URL, FileDownloadStatus.INITIATE);
        testStartDownload(MockFileDownloader.DOWNLOADING_URL, FileDownloadStatus.DOWNLOADING);
        testStartDownload(MockFileDownloader.COMPLETE_URL, FileDownloadStatus.COMPLETE);
        testStartDownload(MockFileDownloader.FAIL_URL, FileDownloadStatus.FAIL);
    }

    private void testStartDownload(String httpUrl, FileDownloadStatus status) {
        FileDownloadResponse fileDownloadResponse = fileDownloaderService.startDownLoadFile(
                new HttpFileDownloadRequest(httpUrl, FILE_LOCATION));
        assertNotNull(fileDownloadResponse);
        assertEquals(status, fileDownloadResponse.getFileDownloadStatus());
    }
}