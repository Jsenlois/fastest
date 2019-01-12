package com.fun.fastest.service.operate;

public interface OperateService {
    void doIt(byte[] data);
    void doIt(byte[] data,int start,int size);
    void finish();
}
