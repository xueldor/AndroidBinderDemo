本项目演示aidl的使用方法，包括：  
用aidl定义IPC的接口，自动生成java代码、绑定服务、方法的调用、注册回调、RemoteCallbackList类、binder对象、调用时怎样分配线程等。

包含三个模块  
* aidl: 是一个android library项目，我们把aidl文件、parcelable对象放到这里，然后另外连个APP项目引用它。  
* server: aidl server端的实现
* app: client端的实现

