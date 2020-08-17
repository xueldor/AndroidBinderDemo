# AndroidBinderDemo

演示了android里面使用binder机制进行跨进程通信的方法  

NativeAndJava目录下，用纯native的代码实现server端，用纯java实现客户端。实现字符串作为参数从client发送到server。  

AIDL目录下，演示了android代码里用aidl定义IPC的接口，自动生成java代码。  

