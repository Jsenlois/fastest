package com.fun.fastest.service.operate.impl;

import com.fun.fastest.service.operate.OperateService;

public class EmptyOperateImpl implements OperateService {
    public void doIt(byte[] data) {
        System.out.println("do nothing ....");
    }

    public void doIt(byte[] data, int start, int size) {
        System.out.println("do nothing ....");
    }

    public void finish() {
        System.out.println("finish");
    }
}
