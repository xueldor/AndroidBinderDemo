# AndroidBinderDemo

演示了android里面使用binder机制进行跨进程通信的方法  

AIDLDemo目录下，演示了AIDL基本用法。

BinderSample克隆自 https://github.com/yuanhuihui/BinderSample.git， 是对安卓binder机制使用的进阶，里面：  
* AppBinderDemo演示APP层如何使用，知识点基本与本示例重叠  
* FrameworkBinderDemo介绍framework层使用binder通信的方法、  
* NativeBinderDemo 介绍native代码利用binder的方法。  

NativeAndJava目录下，用纯native的代码实现server端，用纯java实现客户端。实现字符串作为参数从client发送到server。  


基本上开发者只需要将这些示例拷贝到自己的项目里，然后修修改改就行了。

