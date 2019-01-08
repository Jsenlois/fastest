package com.fun.fastest.service.file;

import com.fun.fastest.service.net.NetService;

public interface FileService {
    void readAndSend(String path, NetService netService);
}
