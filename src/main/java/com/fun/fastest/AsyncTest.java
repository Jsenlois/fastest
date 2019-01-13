package com.fun.fastest;

import com.fun.fastest.service.file.impl.buffered.BufferedByteFileReadImpl;
import com.fun.fastest.service.net.impl.net.bio.BioDataServerServiceImpl;
import com.fun.fastest.service.operate.impl.file.async.AsyncFileWriteImpl;
import com.fun.fastest.service.operate.impl.net.nio.NioSendServiceImpl;

public class AsyncTest {

    public static void main(String[] args) {
        final String ip ="127.0.0.1";
        final int port = 12345;
        new Thread() {
            @Override
            public void run() {
                new BioDataServerServiceImpl(ip,port).doAccept().doIt(new AsyncFileWriteImpl("test_copy.zip"));
            }
        }.start();

        new BufferedByteFileReadImpl().read("test.zip",new NioSendServiceImpl(ip,port));
    }

}
