package com.arkaya.filedownloader.download.http;

import com.arkaya.filedownloader.config.TestFileDownloaderConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestFileDownloaderConfig.class)
public class FileMetadataExtractorTest {

    @Autowired
    private FileMetadataExtractor fileMetadataExtractor;

    @Test
    public void getFileSize() throws Exception {
        URL url = new URL("http://localhost:18080");
        long fileSize = fileMetadataExtractor.getFileSize(new MockHttpUrlConnection(url));
        assertTrue(fileSize > 0);
    }

    private class MockHttpUrlConnection extends HttpURLConnection {


        protected MockHttpUrlConnection(URL u) {
            super(u);
        }

        @Override
        public void disconnect() {

        }

        @Override
        public boolean usingProxy() {
            return false;
        }

        @Override
        public void connect() throws IOException {
        }

        @Override
        public int getResponseCode() throws IOException {
            return HttpURLConnection.HTTP_PARTIAL;
        }

        @Override
        public int getContentLength() {
            return getContentLength();
        }

        @Override
        public long getContentLengthLong() {
            return (long) (Math.random() * 1000);
        }
    }
}