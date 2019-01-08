package com.fun.fastest.service.net;

public interface NetService {
    void send(byte[] data);
    void send(byte[] data,int start,int size);
}
