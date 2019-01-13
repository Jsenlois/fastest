package com.fun.fastest.service.operate.impl.net.nio;

import com.fun.fastest.service.operate.OperateService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioSendServiceImpl implements OperateService {

    private SocketChannel socketChannel;

    public NioSendServiceImpl(String ip, int port) {
        try {
            this.socketChannel = SocketChannel.open(new InetSocketAddress(ip, port));
        } catch (IOException e) {
            throw new RuntimeException("SocketChannel 初始化失败", e);
        }
        try {
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new RuntimeException("配置非阻塞失败", e);
        }
    }

    public void doIt(byte[] data) {
        doIt(data, 0, data.length);
    }

    public void doIt(byte[] data, int start, int size) {
        try {
            socketChannel.write(ByteBuffer.wrap(data, start, size));
        } catch (IOException e) {
            throw new RuntimeException("SocketChannel 写数据失败",e);
        }
    }

    public void finish() {
        if (socketChannel == null) {
            return;
        }
        try {
            socketChannel.close();
        } catch (IOException e) {
            throw new RuntimeException("SocketChannel关闭失败", e);
        }
    }
}
