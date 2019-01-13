package com.fun.fastest.service.operate.impl.file.async;

import com.fun.fastest.service.operate.OperateService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AsyncFileWriteImpl implements OperateService {

    private BlockingQueue<ReceiveData> blockingQueue = new ArrayBlockingQueue<ReceiveData>(1024,true);

    private Boolean isContinue = true;

    private FileOutputStream fileOutputStream;

    private static final int BUFFER_SIZE = 1024 * 1024;

    private byte[] dataCache = new byte[1024*1024];

    public AsyncFileWriteImpl(String path) {
        try {
            fileOutputStream = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件找不到...",e);
        }
        new Thread(new DataHandler(),"DataHandlerThread").start();
    }

    public void doIt(byte[] data) {
        doIt(data,0,data.length);
    }

    public void doIt(byte[] data, int start, int size) {
        try {
            blockingQueue.put(new ReceiveData(data,start,size));
        } catch (InterruptedException e) {
            throw new RuntimeException("blocking queue put error...",e);
        }
    }

    public void finish() {
        //this.isContinue = false;
    }

    private class DataHandler implements Runnable {

        public void run() {
            while (isContinue) {
                ReceiveData data = null;
                try {
                    data = blockingQueue.take();
                } catch (InterruptedException e) {
                     throw new RuntimeException("获取队列元素失败...",e);
                }
                try {
                    fileOutputStream.write(data.data,data.start,data.size);
                } catch (IOException e) {
                    throw new RuntimeException("写入失败...",e);
                }
//                try {
//                    bufferedOutputStream.flush();
//                } catch (IOException e) {
//                    throw new RuntimeException("flush失败...",e);
//                }
            }
            System.out.println("receive and write finish...total:");
        }
    }

    private class ReceiveData {
        byte[] data;
        int start;
        int size;

        private ReceiveData(byte[] data, int start, int size) {
            this.data = data;
            this.start = start;
            this.size = size;
        }
    }
}
