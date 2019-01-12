package com.fun.fastest.service.file.impl.buffered;

import com.fun.fastest.service.file.DataReadService;
import com.fun.fastest.service.operate.OperateService;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BufferedByteFileReadImpl implements DataReadService {

    private static final int BUFFER_SIZE = 1024 * 1024;
    private static final int READ_BUFFER_SIZE = 1024 * 1024;

    public void read(String path, OperateService operateService) {
        long begin = System.currentTimeMillis();
        BufferedInputStream inputStream = null;
        try {
            System.out.println("BufferedByteFileReadImpl prepare to read");
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
                System.out.println("BufferedByteFileReadImpl read len:"+len);
                operateService.doIt(data, 0, len);
            }
            operateService.finish();
        } catch (IOException e) {
            System.out.println("出错了IOException");
            e.printStackTrace();
            return;
        } catch (Exception e) {
            System.out.println("未知错误");
            e.printStackTrace();
            return;
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("文件流关闭失败",e);
        }
        System.out.println("read and send finish"+(System.currentTimeMillis() - begin) / 1000);
    }
}
