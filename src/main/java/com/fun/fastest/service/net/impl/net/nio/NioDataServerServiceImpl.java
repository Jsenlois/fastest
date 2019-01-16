package com.fun.fastest.service.net.impl.net.nio;

import com.fun.fastest.service.net.DataServerService;
import com.fun.fastest.service.operate.OperateService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioDataServerServiceImpl implements DataServerService {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private long sum = 0;

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024*1024);

    public NioDataServerServiceImpl(String ip,int port) {
        try {
            this.selector = Selector.open();
            this.serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(ip, port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException("NIO Server 初始化失败",e);
        }
    }

    public void doIt(OperateService operateService)  {
        while (true) {
            try {
                selector.select(1000);
            } catch (IOException e) {
                throw new RuntimeException("select error...",e);
            }
            Set<SelectionKey> set = selector.selectedKeys();
            Iterator<SelectionKey> it = set.iterator();
            SelectionKey key = null;
            while (it.hasNext()) {
                key = it.next();
                it.remove();
                handlerInput(key,operateService);
            }
        }
    }

    private void handlerInput(SelectionKey key,OperateService operateService) {
        if(!key.isValid()) {
            return;
        }
        if(key.isAcceptable()) {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = null;
            try {
                socketChannel = ssc.accept();
            } catch (IOException e) {
                throw new RuntimeException("ssc accept exception...",e);
            }
            try {
                socketChannel.configureBlocking(false);
            } catch (IOException e) {
                throw new RuntimeException("config blocking error...",e);
            }
            try {
                socketChannel.register(selector,SelectionKey.OP_READ);
            } catch (ClosedChannelException e) {
                throw new RuntimeException("SC register exception...",e);
            }
        }
        if(key.isReadable()) {
            SocketChannel sc = (SocketChannel) key.channel();
            int len = 0;
            try {
                len = sc.read(readBuffer);
            } catch (IOException e) {
                throw new RuntimeException("sc read exception...",e);
            }
            if(len > 0) {
                operateService.doIt(readBuffer.array(),0,len);
                readBuffer.clear();
            }
        }
    }
}
