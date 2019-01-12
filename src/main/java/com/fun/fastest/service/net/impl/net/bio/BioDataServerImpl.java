package com.fun.fastest.service.net.impl.net.bio;

import com.fun.fastest.service.net.DataServer;
import com.fun.fastest.service.operate.OperateService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BioDataServerImpl implements DataServer {

    private Socket socket;
    private ServerSocket serverSocket = null;
    private static final int READ_SIZE = 1024 * 1024;

    public BioDataServerImpl(String ip, int port) {
        try {
            serverSocket = new ServerSocket();
            System.out.println("Server init ip:"+ip+",port:"+port);
        } catch (IOException e) {
            throw new RuntimeException("Server初始化失败...", e);
        }
        try {
            serverSocket.bind(new InetSocketAddress(ip, port));
            System.out.println("Server bind ip:"+ip+",port:"+port);
        } catch (IOException e) {
            throw new RuntimeException("Server监听失败...", e);
        }
    }

    public DataServer doAccept() {
        try {
            System.out.println("Server wait accept");
            this.socket = serverSocket.accept();
            System.out.println("Server accept client,address:"+socket.getInetAddress().getHostAddress());
            return this;
        } catch (IOException e) {
            throw new RuntimeException("Server无法等待接入...", e);
        }
    }

    public void doIt(OperateService operateService) {
        long begin = System.currentTimeMillis();
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            System.out.println("Server prepare to read");
        } catch (IOException e) {
            throw new RuntimeException("Server无法读取数据....", e);
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        int len;
        byte[] data = new byte[READ_SIZE];
        try {
            while ((len = bufferedInputStream.read(data)) != -1) {
                System.out.println("Server read len:"+len);
                operateService.doIt(data, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException("Server读取数据失败了....", e);
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Server 流关闭失败",e);
        }
        System.out.println("receive and write finish:"+(System.currentTimeMillis()-begin)/1000);
    }
}
