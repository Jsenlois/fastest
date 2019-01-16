package com.fun.fastest;

import com.fun.fastest.service.file.impl.buffered.BufferedByteFileReadImpl;
import com.fun.fastest.service.net.impl.net.nio.NioDataServerServiceImpl;
import com.fun.fastest.service.operate.impl.file.buffered.BufferedByteFileWriteImpl;
import com.fun.fastest.service.operate.impl.net.nio.NioSendServiceImpl;

public class NioTest {

    public static void main(String[] args) {
        final String ip ="127.0.0.1";
        final int port = 12345;
        new Thread() {
            @Override
            public void run() {
                new NioDataServerServiceImpl(ip,port).doIt(new BufferedByteFileWriteImpl("test_copy.zip"));
            }
        }.start();

        new BufferedByteFileReadImpl().read("test.zip",new NioSendServiceImpl(ip,port));
    }
}
