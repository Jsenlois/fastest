package com.fun.fastest.service.net.impl;

import com.fun.fastest.service.net.NetService;

/**
 * 搭个架子没用的
 */
public class EmptyNetServvice implements NetService {
    public void send(byte[] data) {
        System.out.println("empty send");
    }
}
