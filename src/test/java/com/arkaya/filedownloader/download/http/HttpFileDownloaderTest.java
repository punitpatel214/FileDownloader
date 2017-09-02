package com.arkaya.filedownloader.download.http;

import com.arkaya.filedownloader.config.TestFileDownloaderConfig;
import com.arkaya.filedownloader.download.constant.FileDownloadKeyConstant;
import com.arkaya.filedownloader.request.FileDownloadRequest;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestFileDownloaderConfig.class)
public class HttpFileDownloaderTest {

    @Autowired
    private HttpFileDownloader httpFileDownloader;

    @Value("${partialFileExtension:.partial}")
    private String partialFileExtension;

    @Test
    public void canDownLoadFile() throws Exception {
        assertNotNull(httpFileDownloader);
        assertTrue(httpFileDownloader.canDownLoadFile(new HttpFileDownloadRequest("http://localhost:8080/abc.txt", "1.txt")));
        assertFalse(httpFileDownloader.canDownLoadFile(() -> FileDownloadRequest.Protocol.FTP));
    }

    @Test
    public void startDownloadFile() throws Exception {
        Path path = Paths.get("Downloads", "abc.txt");
        HttpFileDownloadRequest httpFileDownloadRequest = new HttpFileDownloadRequest("http://localhost:8080/abc.txt", path.toAbsolutePath().toString());
        httpFileDownloader.downloadFile(httpFileDownloadRequest);
        String additionalProperty = httpFileDownloadRequest.getAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME);
        assertNotNull(additionalProperty);


    }

}