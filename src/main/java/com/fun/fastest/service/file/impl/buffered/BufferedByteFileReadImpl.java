package com.fun.fastest.service.file.impl.buffered;

import com.fun.fastest.service.file.DataReadService;
import com.fun.fastest.service.operate.OperateService;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BufferedByteFileReadImpl implements DataReadService {

    private static final int BUFFER_SIZE = 50 * 1024 * 1024;
    private static final int READ_BUFFER_SIZE = 50 * 1024 * 1024;

    public void read(String path, OperateService operateService) {
        long begin = System.currentTimeMillis();
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(path), BUFFER_SIZE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件找不到...",e);
        }
        byte[] data = new byte[READ_BUFFER_SIZE];
        int len = 0 ;
        try {
            while ((len = inputStream.read(data)) != -1) {
                operateService.doIt(data, 0, len);
            }
            operateService.finish();
        } catch (IOException e) {
            throw new RuntimeException("数据读取出错...",e);
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("文件流关闭失败",e);
        }
        System.out.println("read and send finish:"+(System.currentTimeMillis() - begin) / 1000);
    }
}
