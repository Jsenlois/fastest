package com.fun.fastest.service.operate.impl.file.buffered;

import com.fun.fastest.service.operate.OperateService;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferedByteFileWriteImpl implements OperateService {

    private BufferedOutputStream bufferedOutputStream;

    public BufferedByteFileWriteImpl(String filePath) {
        try {
            bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream(filePath)
            );
            System.out.println("BufferedByteFileWriteImpl prepare to write");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件找不到...",e);
        }
    }

    public void doIt(byte[] data) {
        doIt(data,0,data.length);
    }

    public void doIt(byte[] data, int start, int size) {
        try {
            System.out.println("BufferedByteFileWriteImpl write start:"+start+",size:"+size);
            bufferedOutputStream.write(data,start,size);
        } catch (IOException e) {
            throw new RuntimeException("文件写入失败...",e);
        }
        try {
            System.out.println("BufferedByteFileWriteImpl flush");
            bufferedOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("文件数据Flush失败...",e);
        }
    }

    public void finish() {
        if(bufferedOutputStream != null) {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
