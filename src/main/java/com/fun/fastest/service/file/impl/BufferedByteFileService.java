package com.fun.fastest.service.file.impl;

import com.fun.fastest.service.file.FileService;
import com.fun.fastest.service.net.NetService;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BufferedByteFileService implements FileService{

    private static final int BUFFER_SIZE = 1024 * 1024;
    private static final int READ_BUFFER_SIZE = 1024 * 1024;

    public void readAndSend(String path, NetService netService) {
        long begin = System.currentTimeMillis();
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(path), BUFFER_SIZE);
        } catch (FileNotFoundException e) {
            System.out.println("文件找不到，484傻啊");
            e.printStackTrace();
            return;
        }
        byte[] data = new byte[READ_BUFFER_SIZE];
        int len = 0 ;
        try {
            while ((len = inputStream.read(data)) != -1) {
                netService.send(data, 0, len);
            }
        } catch (IOException e) {
            System.out.println("出错了IOException");
            e.printStackTrace();
            return;
        } catch (Exception e) {
            System.out.println("未知错误");
            e.printStackTrace();
            return;
        }
        System.out.println((System.currentTimeMillis() - begin) / 1000);
    }
}
