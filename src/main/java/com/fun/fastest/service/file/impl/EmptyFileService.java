package com.fun.fastest.service.file.impl;

import com.fun.fastest.service.file.FileService;
import com.fun.fastest.service.net.NetService;

/**
 * 搭个架子没用的
 */
public class EmptyFileService implements FileService {
    public void readAndSend(String path, NetService netService) {
        System.out.println("read "+path);
        netService.send(null);
    }
}
