# FileDownloader [![Build Status](https://api.travis-ci.org/punitpatel214/FileDownloader.svg?branch=master)](https://api.travis-ci.org/punitpatel214/FileDownloader)
Java File Downloader, download File using HttpURLConnection of Java

### Features
1. Download file from HttpUrl
2. If File already partially downloaded, download resume from there.
3. Provide CLI interface for Download File
4. CLI display progressbar on console for monitor downloading process.

### How to use
1. Execute **gradle** command from home directory
```bash
FileDownloader git:(sprint) ✗ gradle
:clean
:compileJava
:processResources
:classes
:jar
:assemble
:compileTestJava
:processTestResources UP-TO-DATE
:testClasses
:test
:check
:build
:createExecutableJar
BUILD SUCCESSFUL
Total time: 8.269 secs
```
2. gradle command generate executable **FileDownloader-exe.jar** in **build/libs** folder
3. execute **FileDownloader-exe.jar** to download file from HttpUrl.
FileDownloader git:(sprint) ✗ java -jar build/libs/FileDownloader-exe.jar
```bash
**********************************
**  File Downloader Started     **
**********************************
Please enter Download File URL:http://speedtest.ftp.otenet.gr/files/test100k.db
Please enter File Location : /tmp
100 % [...................................................]
```
