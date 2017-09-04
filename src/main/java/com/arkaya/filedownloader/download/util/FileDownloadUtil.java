package com.arkaya.filedownloader.download.util;

import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by punit.patel on 29/8/17.
 */
public class FileDownloadUtil {

    private static final String PARTIAL_FILE_EXTENSION = ".partial";

    public static String generatePartialFileName(Path path, String fileName) {
        return path.resolve(fileName + PARTIAL_FILE_EXTENSION).toString();
    }

    public static Path fileNameFromPartialFile(String partialFileName) {
        return Paths.get(StringUtils.substringBeforeLast(partialFileName, PARTIAL_FILE_EXTENSION));
    }
}
