## 项目介绍

本项目演示aidl的使用方法，包括：  
用aidl定义IPC的接口，自动生成java代码、绑定服务、方法的调用、注册回调、RemoteCallbackList类、binder对象、调用时怎样分配线程等。

第一个示例，包含三个模块
* aidl: 是一个android library项目，我们把aidl文件、parcelable对象放到这里，然后server模块和app模块引用它。  
* server: aidl server端的实现
* app: client端的实现

第二个示例
* servicebinder，演示了binder的客户端和服务端在同一个应用里，用于同一个应用的activity和service通讯。如果是单向的，似乎用本地广播来实现更方便。不需要aidl文件，直接继承Binder类。

## AIDL基础知识

官方参考见：https://developer.android.google.cn/guide/components/aidl?hl=zh_cn

**aidl中支持的数据类型**

- 八种基本数据类型：byte、char、short、int、long、float、double、boolean

- String，CharSequence

- 实现了Parcelable接口的数据类型

- List 类型。List承载的数据必须是AIDL支持的类型（即上面几点提到的类型），或者是其它声明的AIDL接口，或 Parcelable 类。

  可以将`List` 用作“泛型”类（例如，`List<String>`）。

  **限制：**List的真实类型必须是ArrayList

- Map类型。Map承载的数据必须是AIDL支持的类型，或者是其它声明的AIDL对象，或 Parcelable 类（同List）

  **注意：**不支持泛型 Map，这点与List**不一样**；具体类必须是 `HashMap`。

  

**方向标记**

方法参数，指示数据走向的方向标记是 `in`、`out` 或 `inout`。

几种基本数据类型以及String，默认就是in，不需要标记。显式标记为in也行，但不能标记为out。

而List、Map或自定义的Parcelable，作为参数时，一定要指定 `in`、`out` 或 `inout`。



**`oneway` 关键字**

`oneway` 本意是用于远程调用的，如果用于本地调用，不会有任何影响，仍为同步调用；

用于远程调用，只是发送事务数据并立即返回。也就是说，oneway的方法，不能有任何返回数据，包含两层：1. 不能有返回值 2.参数只能是in，不能是out和inout。

服务端把oneway方法的调用，视为 `Binder` 线程池上的普通调用，不知道是远程的。方法实现里无法用Binder.getCallingPid()获取client进程号。

**需要import的类型**

|  类型       | 需要import？    |备注   |
|  ----      | ----           |----  |
| Java基本类型 | 不需要import    | 不能用作out |
| String、CharSequence | 不需要import    | 不能用作out |
| List | 不需要import    | 必须是ArrayList |
| Map  | 不需要import    | 必须是HashMap |
| 其他AIDL定义的AIDL接口 | 需要import,即使在相同路径下 | 传递的是引用 |
| 实现Parcelable的类 | 需要import,即使在相同路径下 | 值传递 |

**其它要点**

* AIDL不支持重载
* Binder.getCallingPid()用于在binder的实现端中获取是哪个client调用我提供的方法。
  Binder.getCallingPid()一定放在binder的实现里，（即XX extends xxx.Stub的方法的实现里面），必须在方法实现里直接调用，不能是里面的子线程、handler转发等。
  声明为oneway的接口，以及Messenger，本质是使用IBinder.FLAG_ONEWAY进行交易，远程应用程序发送的消息是异步的，因此getCallingPid将始终返回0。
* TestAidl文件名应与里面定义的interface名相同，文件路径应与package一致(仿java)。
* 待补充。。。



## aidl to java

当使用IDE开发时，IDE自动帮我们调用aidl指令。现在了解一下aidl命令。

`aidl.exe`位于`<android-sdk>\build-tools\xx.x.x`。源码环境下，source build/envsetup.sh&&lunch就能使用此命令。

//TestAidl.aidl代码：

```aidl
package com.xueldor;

interface TestAidl {
	List<String> getListString();
	void setList(in List list);//List做参数，需显式声明为'in'
	
	void setMap(in Map map);
	Map getMap();
//	void getMap(out Map map);//aidl不支持重载
    void inOutMap(inout Map map);
	
	String getString(String name);
	int getCount();
	boolean getBoolean();
	
	oneway void setPrice(int price);
	oneway void setName(String name);
//  oneway int testOutOneWay(out List name);//oneway参数不能是out，不能有返回值	

	void getName(in String name);
//	void getName(out String name);//String类型不能是out
}
```

把这个文件放在`com\xueldor`目录下。然后shell路径cd到com目录的同一层。

执行命令：

```shell
aidl -I. -d./dependency.txt com/xueldor/TestAidl.aidl
```

`-I`和`-d`后面没有空格。`-I`指定寻找import的路径，`-d`指定生成的dependency file。这里我们不需要，因此命令可以简化为：

```shell
aidl  com/xueldor/TestAidl.aidl
```

然后可观察到`com/xueldor/`目录下生成了TestAidl.java文件。

把TestAidl.aidl里的注释去掉，再执行命令，可看到控制台输出错误信息，比如:

```shell
work@S111-CCS2plus:~/xue/plus_qm/aaa$ aidl -I. -ddependenct.txt com/xueldor/TestAidl.aidl 
com/xueldor/TestAidl.aidl:18 oneway method 'setName' cannot have out parameters
com/xueldor/TestAidl.aidl:18 attempt to redefine method setName,
com/xueldor/TestAidl.aidl:17    previously defined here.
```

通过报错信息，即使不了解aidl规则，也可以直观的排查出问题所在。

## aidl to C++

从安卓10开始，aidl支持C++。安卓10之前，需在源码环境用aidl-cpp命令。

进入`android-sdk\build-tools\29.0.1`目录，执行`aidl --help`,可以看到末尾有：

```shell
HEADER_DIR:
  Path to where C++ headers are generated.
```

29.0.1之前的版本，没有这个输出。29对应安卓10。

现在我在安卓11的aosp源码环境测试这个命令。

还是用前面的TestAidl.aidl文件，执行命令：

```shell
work@ubuntu-cts:~/aosp/aaa$ aidl com/xueldor/TestAidl.aidl -o . --lang=cpp --header_out=.
ERROR: com/xueldor/TestAidl.aidl:5.17-22: Currently, only the Java backend supports non-generic List.
```

提示java才支持非泛型的List类型。因此把List改成`List<String>`。

然后提示`Currently, only Java backend supports Map.`,只有java才支持Map。

从简单开始，姑且先把List、Map之类去掉,代码:

```aidl
package com.xueldor;

interface TestAidl {
        String getString(String name);
        int getCount();
        boolean getBoolean();

        oneway void setPrice(int price);
        oneway void setName(String name);
        void getName(in String name);
}
```

然后再执行`aidl com/xueldor/TestAidl.aidl -o . --lang=cpp --header_out=.`。

在com/xueldor目录下得到TestAidl.h、TestAidl.cpp、BpTestAidl.h、BnTestAidl.h四个文件。

这样就简化了C++里面写binder通信工作量。

从生成的C++代码可看出，。关于类型的对应，见文末。

另，“build-tools\29.0.1”之前, 也就是安卓10之前，aidl命令虽然不支持--lang=cpp,但是源码环境有一个aidl-cpp命令。执行`aidl-cpp -I. com/xueldor/TestAidl.aidl . ./TestAidl.cpp`,效果一样，可能aidl里面就是调了一下aidl-cpp。注意两个命令的语法差异。

**Note:** 

1. 关于不支持Map类型的报错，奇怪的是，在Android P环境，用aidl-cpp命令是没问题的，且Map转成了`const ::android::binder::Map& map`。所以，为什么低版本都支持了，高版本反而不支持？？？所以我们在设计aidl时，尽量避开Map这种复杂的类型。

2. 参数`List<String> list`转C++后，是`const ::std::vector<::android::String16>& list`

3. String转换成了`::android::String16&`类型。我们希望用UTF8而不是UTF16,加一个注解`@utf8InCpp`。比如，`void setList(in @utf8InCpp List<String> list);`，注意不能加在尖括号里面：`void setList(in List<@utf8InCpp String> list)`-->错。

   添加@utf8InCpp注解后，C++类型是std::string。如果希望整个aidl里面都用标准库的string，把这个注解加在类上面。这块官网地址是：https://source.android.google.cn/devices/architecture/aidl/aidl-annotations?hl=zh-cn
   
4. `List<String>`，因为java 的泛型，不能说基础类型，所以`List<int>`之类是不行的，`List<Integer>`也不行，因为Integer是个类，不是Parcelable。所以实际上，aidl里用List的话，只能是`List<String>`。我看官网https://source.android.google.cn/devices/architecture/aidl/aidl-backends?hl=zh_cn里面提到：

   *C++ 后端仅支持 `List<String>` 和 `List<IBinder>`。一般情况下，建议使用类似 `T[]` 的数组类型，因为它们适用于所有后端。*



## parcelable对象

前面提到，aidl中除了支持基础类型和字符串，也可以自定义复合类型，但是必须实现parcelable接口。

### java

1. 您的类实现 `Parcelable` 接口,如：`public class Book implements android.os.Parcelable`。

2. 实现 `writeToParcel`，它会获取对象的当前状态并将其写入 `Parcel`。

3. 为您的类添加 `CREATOR` 静态字段，该字段是实现 `Parcelable.Creator` 接口的对象，实现Creator里面的两个方法。

4. 最后，创建声明 Parcelable 类的 `.aidl` 文件，例如：

   ```aidl
   //Book.aidl
   package com.xueldor.aidl;
   
   parcelable Book;
   ```

如果用AndroidStudio开发，Book.aidl放到aidl目录，实现`Parcelable` 接口的Book.java文件放到java目录。

如果用Android.mk源码编译，Book.aidl不需要添加到mk文件，否则报错，因为aidl命令作用于Parcelable对象的aidl文件的话，是会报错的（安卓10引入的新特性后面说）。也就是说，Book.aidl仅仅起一个声明的作用，别的aidl文件里面用到Book对象的话，一定要能找到Book.aidl，才能转成java。

(Book.java的示范代码就不贴了，因为后面会介绍简单的得到java代码的方法)

### C++

假如aidl to C++时，aidl文件中用到了Parcelable对象，那么对应的Parcelable实现就必须是C++的实现，而不是java实现android.os.Parcelable接口的方式。但是原理是一样的。

1. 头文件 #include <binder/Parcel.h>

2. 继承android::Parcelable，例如：`class Book : public android::Parcelable`

3. 实现readFromParcel以及writeToParcel。

java比较简单，因为aidl一开始就是仿java的。C++的话，要注意数据类型的映射关系

### android 10新特性

Android 10 及更高版本支持 parcelable 里面声明成员，从而带来的好处是，可以不要手动编写Parcelable的java实现。仍以Book.aidl举例：

```aidl
package com.xueldor.aidl;

parcelable Book{
	@utf8InCpp String name;
	int price;
}//末尾没有分号
```

确保aidl是build-tools 29或更高。然后执行：`aidl com/xueldor/aidl/Book.aidl`,即得到Book.java文件。

执行：`aidl -o . --lang=cpp --header_out=. com\xueldor\aidl\Book.aidl`,得到BnBook.h、BpBook.h、Book.h、Book.cpp四个文件，其中，BnBook.h、BpBook.h两个文件是空的，因为这是Parcelable，相当于java bean。删掉。把Book.h、Book.cpp拷到项目里即可。

### 更多新特性

Android 11 及更高版本支持枚举声明

Android 12 及更高版本支持联合声明

Android T（AOSP 实验版）及更高版本支持嵌套类型声明

本文编写时，Android T尚未发布，后续如有新功能更新请自行参阅https://source.android.google.cn/devices/architecture/aidl/overview?hl=zh_cn

## 类型映射

表格来自`<aosp_src_dir>/system/tools/aidl/docs/aidl-cpp.md`

| Java Type             | C++ Type             | inout | Notes                                                 |
| --------------------- | -------------------- | ----- | ----------------------------------------------------- |
| boolean               | bool                 | in    | "These 8 types are all considered primitives.         |
| byte                  | int8\_t              | in    |                                                       |
| char                  | char16\_t            | in    |                                                       |
| int                   | int32\_t             | in    |                                                       |
| long                  | int64\_t             | in    |                                                       |
| float                 | float                | in    |                                                       |
| double                | double               | in    |                                                       |
| String                | String16             | in    | Supports null references.                             |
| @utf8InCpp String     | std::string          | in    | @utf8InCpp causes UTF16 to UTF8 conversion in C++.    |
| android.os.Parcelable | android::Parcelable  | inout |                                                       |
| java.util.Map         | android::binder::Map | inout | `std::map<std::string,android::binder::Value>`        |
| T extends IBinder     | sp<T>                | in    |                                                       |
| Arrays (T[])          | vector<T>            | inout | May contain only primitives, Strings and parcelables. |
| List<String>          | vector<String16>     | inout |                                                       |
| PersistableBundle     | PersistableBundle    | inout | binder/PersistableBundle.h                            |
| List<IBinder>         | vector<sp<IBinder>>  | inout |                                                       |
| FileDescriptor        | unique_fd            | inout | android-base/unique_fd.h from libbase                 |



## 参考

https://developer.android.google.cn/guide/components/aidl?hl=zh_cn

https://source.android.google.cn/devices/architecture/aidl/overview?hl=zh_cn

https://source.android.google.cn/devices/architecture/aidl/aidl-backends?hl=zh_cn

https://source.android.google.cn/devices/architecture/aidl/aidl-annotations

以及源码目录下的文件：`<aosp_src_dir>/system/tools/aidl/docs/aidl-cpp.md`

参考源码: frameworks/base/libs/services/src/os/DropBoxManager.cpp、frameworks/base/libs/services/include/android/os/DropBoxManager.h、frameworks/base/core/java/android/os/DropBoxManager.java、frameworks/base/core/java/android/os/DropBoxManager.aidl