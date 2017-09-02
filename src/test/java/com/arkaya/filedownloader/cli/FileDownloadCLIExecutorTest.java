package com.arkaya.filedownloader.cli;

import com.arkaya.filedownloader.config.TestFileDownloaderConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestFileDownloaderConfig.class)
public class FileDownloadCLIExecutorTest {

    @Autowired
    private FileDownloadCLIExecutor cliExecutor;

    @Test
    public void testExecute() throws Exception {
        assertNotNull(cliExecutor);
        String urlsAndFileLocations = String.join(System.lineSeparator(), "http//abc.txt", "http://127.0.0.1:1234/abc.txt",
                "tmp", File.separator + "tmp");
        InputStream inputStream = new ByteArrayInputStream(urlsAndFileLocations.getBytes());
        System.setIn(inputStream);
        cliExecutor.execute();
    }
}