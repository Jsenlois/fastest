package com.fun.fastest;

import com.fun.fastest.service.file.FileService;
import com.fun.fastest.service.file.impl.EmptyFileService;
import com.fun.fastest.service.net.impl.EmptyNetServvice;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("Hello World");
        FileService fileService = new EmptyFileService();
        fileService.readAndSend("empty path",new EmptyNetServvice());
    }
}
