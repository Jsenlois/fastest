package com.fun.fastest.service.file.impl;

import com.fun.fastest.service.file.DataReadService;
import com.fun.fastest.service.operate.OperateService;

/**
 * 搭个架子没用的
 */
public class EmptyDataReadServiceImpl implements DataReadService {
    public void read(String path, OperateService operateService) {
        System.out.println("read "+path);
        operateService.doIt(null);
    }
}
