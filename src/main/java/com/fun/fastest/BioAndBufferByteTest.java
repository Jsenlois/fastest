package com.fun.fastest;

import com.fun.fastest.service.file.impl.buffered.BufferedByteFileReadImpl;
import com.fun.fastest.service.net.impl.net.bio.BioDataServerImpl;
import com.fun.fastest.service.operate.impl.file.buffered.BufferedByteFileWriteImpl;
import com.fun.fastest.service.operate.impl.net.bio.BioSendServiceImpl;

public class BioAndBufferByteTest {

    public static void main(String[] args) {
        final String ip ="127.0.0.1";
        final int port = 12345;
        new Thread() {
            @Override
            public void run() {
                new BioDataServerImpl(ip,port).doAccept().doIt(new BufferedByteFileWriteImpl("jvm_copy.pdf"));
            }
        }.start();

        new BufferedByteFileReadImpl().read("jvm.pdf",new BioSendServiceImpl(ip,port));
    }
}
