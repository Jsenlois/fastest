package com.fun.fastest.service.operate.impl.net.bio;

import com.fun.fastest.service.operate.OperateService;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BioSendServiceImpl implements OperateService {

    private BufferedOutputStream bufferedOutputStream;
    private static final int WRITE_CACHE_SIZE = 50 * 1024 * 1024 ;
    public BioSendServiceImpl(String ip,int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip,port));
            System.out.println("BioSendServiceImpl connect ip:"+ip+",port:"+port);
        } catch (IOException e) {
            throw new RuntimeException("连接失败...",e);
        }
        try {
            bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream(),WRITE_CACHE_SIZE);
        } catch (IOException e) {
            throw new RuntimeException("无法获取输出流...",e);
        }
    }

    public void doIt(byte[] data) {
        doIt(data,0,data.length);
    }

    public void doIt(byte[] data, int start, int size) {
        try {
            System.out.println("net write size:"+size);
            bufferedOutputStream.write(data,start,size);
        } catch (IOException e) {
            throw new RuntimeException("写入数据失败了...",e);
        }
        try {
            bufferedOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Flush数据失败了...",e);
        }
    }

    public void finish() {
        try {
            if(bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
