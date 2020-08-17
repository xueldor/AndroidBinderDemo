#ifndef I_AFTER_SALES_SERVICE
#define I_AFTER_SALES_SERVICE
#include <stdio.h>
#include <binder/IInterface.h>
#include <binder/Parcel.h>
#include <binder/IBinder.h>
#include <binder/Binder.h>
#include <binder/ProcessState.h>
#include <binder/IPCThreadState.h>
#include <binder/IServiceManager.h>
using namespace android;
namespace android {
class IAfterSalesService: public IInterface {
public:
	DECLARE_META_INTERFACE(AfterSalesService);
	virtual int runFileCommand(const char* filepath)=0; //定义方法
};
enum
{
   RUN_FILE_COMMAND = 1,
};
//客户端
class BpAfterSalesService: public BpInterface<IAfterSalesService> {
public:
	BpAfterSalesService(const sp<IBinder>& impl);
	virtual int runFileCommand(const char* filepath);
};

//服务端
class BnAfterSalesService: public BnInterface<IAfterSalesService> {
public:
	virtual status_t onTransact(uint32_t code, const Parcel& data,
			Parcel* reply, uint32_t flags = 0);
	virtual int runFileCommand(const char* filepath);
};
}
#endif

