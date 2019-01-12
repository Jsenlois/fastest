package com.fun.fastest;

import com.fun.fastest.service.file.DataReadService;
import com.fun.fastest.service.file.impl.EmptyDataReadServiceImpl;
import com.fun.fastest.service.operate.impl.EmptyOperateImpl;

public class MainTest {
    public static void main(String[] args) {
        DataReadService fileReadService = new EmptyDataReadServiceImpl();
        fileReadService.read("empty path",new EmptyOperateImpl());
    }
}
