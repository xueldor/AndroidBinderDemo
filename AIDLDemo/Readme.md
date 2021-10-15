本项目演示aidl的使用方法，包括：  
用aidl定义IPC的接口，自动生成java代码、绑定服务、方法的调用、注册回调、RemoteCallbackList类、binder对象、调用时怎样分配线程等。

包含四个模块  
* aidl: 是一个android library项目，我们把aidl文件、parcelable对象放到这里，然后server模块和app模块引用它。  
* server: aidl server端的实现
* app: client端的实现
* 独立模块，binder的客户端和服务端在一个应用里，用于同一个应用的activity和service通讯。如果是单向的，似乎用本地广播更方便。没有aidl文件，直接继承Binder类。

