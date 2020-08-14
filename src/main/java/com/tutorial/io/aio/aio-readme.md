# AIO 编程

NIO2.0的异步套接字通道是真正的异步非阻塞I/O，对应于unix网络编程中的事件驱动I/O（AIO）。这不需要通过多路复用器（Selector）对注册的通道进行轮询操作即可实现异步读写，从而简化了NIO的编程模型。

CompletionHandler接口的实现类作为操作完成的回调。