package com.fun.fastest.service.operate.impl.file.nio;

import com.fun.fastest.service.operate.OperateService;

import java.io.File;

public class NioFileWriteImpl implements OperateService {
    public void doIt(byte[] data) {
        doIt(data,0,data.length);
    }

    public void doIt(byte[] data, int start, int size) {
        File file = new File("");

    }

    public void finish() {

    }
}
