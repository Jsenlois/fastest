package com.fun.fastest;

import com.fun.fastest.service.file.impl.buffered.BufferedByteFileReadImpl;
import com.fun.fastest.service.net.impl.net.bio.BioDataServerServiceImpl;
import com.fun.fastest.service.operate.impl.file.buffered.BufferedByteFileWriteImpl;
import com.fun.fastest.service.operate.impl.net.bio.BioSendServiceImpl;

public class BioAndBufferByteTest {

    public static void main(String[] args) {
        final String ip ="127.0.0.1";
        final int port = 12345;
        new Thread() {
            @Override
            public void run() {
                new BioDataServerServiceImpl(ip,port).doAccept().doIt(new BufferedByteFileWriteImpl("test_copy.zip"));
            }
        }.start();

        new BufferedByteFileReadImpl().read("test.zip",new BioSendServiceImpl(ip,port));
    }
}
