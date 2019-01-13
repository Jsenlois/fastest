# fastest
# Description    
explore the fastest file transport program
---------------------------
## 抽象流程
 1. 数据读取
    - DataReadService
 2. 数据操作（发送，写入，过滤，或者其他）
    - OperateService
 3. 数据接收
    - DataServerService
 4. 数据操作（写入，发送，过滤，或者其他） 
    - OperateService
    
 参考下BioAndBufferByteTest，可以自己写读取，发送，接收，写入的具体实现
 


测试记录  

读取（BufferedByteFileReadImpl），读取缓存50M  
发送（BioSendServiceImpl），发送缓存50M  
接收（BioDataServerServiceImpl），接收缓存50M  
写入（BufferedByteFileWriteImpl），写缓存50M 
文件大小：4.6G  
花费时间：113s

读取（BufferedByteFileReadImpl）
发送（NioSendServiceImpl）
接收（BioDataServerServiceImpl）
写入（AsyncFileWriteImpl）
读取并发送：46s
接收：46s
写入：有点久

HD -> os.core -> os.thread -> jvm.thread [cache]  
jvm.read -> jvm.cache -> native -> os.thread -> os.core -> HD

