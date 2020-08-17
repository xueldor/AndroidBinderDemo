#include <string.h>
#include "IAfterSalesService.h"

namespace android
{
    IMPLEMENT_META_INTERFACE(AfterSalesService, "android.hsae.aftersale");

    //客户端
    BpAfterSalesService::BpAfterSalesService(const sp<IBinder>& impl) :
			BpInterface<IAfterSalesService>(impl) {
	}

    int BpAfterSalesService::runFileCommand(const char* filepath) {
		printf("BpAfterSalesService::runFileCommand %s\n",filepath);
		fflush(stdout);
		Parcel data, reply;
		data.writeInterfaceToken(IAfterSalesService::getInterfaceDescriptor());
		remote()->transact(RUN_FILE_COMMAND, data, &reply);
		int retVal = reply.readInt32();
		printf("get return from BnAfterSalesService: %d\n", retVal);
		fflush(stdout);
		return retVal;
	}

	//服务端，接收远程消息，处理onTransact方法
	status_t BnAfterSalesService::onTransact(uint_t code, const Parcel& data,
			Parcel* reply, uint32_t flags) {
		switch (code) {
		case RUN_FILE_COMMAND: {
			printf("BnAfterSalesService:: onTransact RUN_FILE_COMMAND\n");
			fflush(stdout);
			CHECK_INTERFACE(IAfterSalesService, data, reply);
			int len = data.readInt32();
			char* str = (char *)data.readInplace(len);
			int val = runFileCommand(str);
			reply->writeInt32(val);
			return NO_ERROR;
		}
		break;
		default:
			break;
		}
		return NO_ERROR;
	}

	// 实现服务端runFileCommand方法
	int BnAfterSalesService::runFileCommand(const char* filepath) {
		char pCmd[100] = "sh ";
		strcat(pCmd,filepath);
		printf("command: %s \n",pCmd);
		int val = system(pCmd);
		return val;
	};

	//Make sure it is a c-styled string
//	char* makeSureCString(char *str, int len) {
//		if (str[len] == '\0') {
//			return str;
//		}
//		for (int i = 0; i < len; ++i) {
//			if (str[i] == '\0') {
//				return str;
//			}
//		}
//		printf("allocate space for str\n");
//		char* cstr = new char[len + 1];
//		for (int i = 0; i < len; ++i) {
//			cstr[i] = str[i];
//		}
//		cstr[len] = '\0';
//		return cstr;
//	}
}
