package com.fun.fastest.service.operate.impl.net.nio;

import com.fun.fastest.service.operate.OperateService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NioSendServiceImpl implements OperateService {

    private SocketChannel socketChannel;

    private long sum = 0;

    private boolean stop = false;

    private BlockingQueue<ByteBuffer> queue = new ArrayBlockingQueue<ByteBuffer>(1024);

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

        new Thread(new SendThread(),"SendThread").start();
    }

    public void doIt(byte[] data) {
        doIt(data, 0, data.length);
    }

    public void doIt(byte[] data, int start, int size) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data,start,size);
        queue.add(byteBuffer);
    }

    public void finish() {

    }

    private class SendThread implements Runnable {
        public void run() {
            while (!stop) {
                ByteBuffer data = queue.peek();
                if(data == null) {
                    continue;
                }
                int len = 0;
                int total = data.remaining();
                try {
                    len = socketChannel.write(data);
                    sum += len;
                } catch (IOException e) {
                    throw new RuntimeException("写失败了。。。",e);
                }
                if(len >= total) {
                    queue.remove();
                }
            }
        }
    }
}
