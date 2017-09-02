package com.arkaya.filedownloader.download.http;

import com.arkaya.filedownloader.config.TestFileDownloaderConfig;
import com.arkaya.filedownloader.constant.FileDownloadStatus;
import com.arkaya.filedownloader.download.constant.FileDownloadKeyConstant;
import com.arkaya.filedownloader.download.task.FileDownloadTaskHandler;
import com.arkaya.filedownloader.download.util.FileDownloadUtil;
import com.arkaya.filedownloader.request.http.HttpFileDownloadRequest;
import com.arkaya.filedownloader.response.http.HttpFileDownloadResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestFileDownloaderConfig.class)
public class HttpFileDownloaderValidatorTest {

    @Autowired
    private HttpFileDownloaderValidator httpFileDownloaderValidator;

    @Autowired
    private FileDownloadTaskHandler fileDownloadTaskHandler;


    @Test
    public void whenFileAlreadyExitsThenValidationShouldFail() throws Exception {
        Path tempFile = Files.createTempFile("abc", ".txt");
        HttpFileDownloadRequest httpFileDownloadRequest = new HttpFileDownloadRequest("http://localhost:8080/" + tempFile.getFileName(),
                tempFile.getParent().toString());
        HttpFileDownloadResponse httpFileDownloadResponse = new HttpFileDownloadResponse();
        boolean validate = httpFileDownloaderValidator.validate(httpFileDownloadRequest, httpFileDownloadResponse);
        assertFalse(validate);
        assertEquals(FileDownloadStatus.FAIL, httpFileDownloadResponse.getFileDownloadStatus());
    }

    @Test
    public void whenFileNotExitsThenValidationShouldPass() throws Exception {
        Path fileLocation = Paths.get("abc.txt").toAbsolutePath();
        if (Files.exists(fileLocation)) {
            Files.delete(fileLocation);
        }
        HttpFileDownloadRequest httpFileDownloadRequest = new HttpFileDownloadRequest("http://localhost:8080/abc.txt",
                Paths.get("").toAbsolutePath().toString());
        HttpFileDownloadResponse httpFileDownloadResponse = new HttpFileDownloadResponse();
        boolean validate = httpFileDownloaderValidator.validate(httpFileDownloadRequest, httpFileDownloadResponse);
        assertTrue(validate);
    }

    @Test
    public void whenPartialFileAlreadyExitsAndDifferentHttpUrlReceivedThenValidationShouldFail() throws Exception {
        Path fileLocation = Paths.get("abc.txt").toAbsolutePath();
        if (Files.exists(fileLocation)) {
            Files.delete(fileLocation);
        }
        String partialFile = fileLocation.toString();
        registerHttpRequest(fileLocation, partialFile);

        HttpFileDownloadRequest httpFileDownloadRequest = new HttpFileDownloadRequest("http://localhost:18080/abc.txt",
                fileLocation.toString());
        httpFileDownloadRequest.addAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME, partialFile);
        HttpFileDownloadResponse httpFileDownloadResponse = new HttpFileDownloadResponse();
        assertFalse(httpFileDownloaderValidator.validate(httpFileDownloadRequest, httpFileDownloadResponse));
        assertEquals(FileDownloadStatus.FAIL, httpFileDownloadResponse.getFileDownloadStatus());
    }

    @Test
    public void whenPartialFileAlreadyExitsAndSameHttpUrlReceivedThenValidationShouldPass() throws Exception {
        Path fileLocation = Paths.get("abc.txt").toAbsolutePath();
        if (Files.exists(fileLocation)) {
            Files.delete(fileLocation);
        }
        String partialFile = fileLocation.toString();
        registerHttpRequest(fileLocation, partialFile);

        HttpFileDownloadRequest httpFileDownloadRequest = new HttpFileDownloadRequest("http://localhost:8080/abc.txt",
                fileLocation.toString());
        httpFileDownloadRequest.addAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME, partialFile);
        HttpFileDownloadResponse httpFileDownloadResponse = new HttpFileDownloadResponse();
        assertTrue(httpFileDownloaderValidator.validate(httpFileDownloadRequest, httpFileDownloadResponse));
    }

    private void registerHttpRequest(Path fileLocation, String partialFile) throws IOException {
        Path path = Paths.get(partialFile);
        if (Files.notExists(path)) {
            Files.createFile(Paths.get(partialFile));
        }

        HttpFileDownloadRequest httpFileDownloadRequest = new HttpFileDownloadRequest("http://localhost:8080/abc.txt",
                fileLocation.toString());
        httpFileDownloadRequest.addAdditionalProperty(FileDownloadKeyConstant.PARTIAL_FILE_NAME, partialFile);
        fileDownloadTaskHandler.register(httpFileDownloadRequest);
    }


}