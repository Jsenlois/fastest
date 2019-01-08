package com.fun.fastest.service.net.impl;

import com.fun.fastest.service.net.NetService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class AioNetService implements NetService{

    private AsynchronousSocketChannel asynchronousSocketChannel = null;

    public AioNetService(String ip, int port) {
        try {
            asynchronousSocketChannel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            System.out.println("SocketChannel open 失败");
            e.printStackTrace();
        }

        SocketAddress socketAddress = new InetSocketAddress(ip, port);
        asynchronousSocketChannel.connect(socketAddress);
    }

    public void send(byte[] data) {
        send(data,0,data.length);
    }

    public void send(byte[] data, int start, int size) {
        if(asynchronousSocketChannel == null) {
            throw new RuntimeException("socket channel 为空，发不了。。。");
        }
        asynchronousSocketChannel.write(ByteBuffer.wrap(data,start,size));
    }
}
