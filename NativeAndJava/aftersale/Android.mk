LOCAL_PATH := $(call my-dir)
 

include $(CLEAR_VARS)
LOCAL_SHARED_LIBRARIES := \
    libcutils \
    libutils \
    libbinder       
LOCAL_MODULE    := aftersale
LOCAL_SRC_FILES := \
    IAfterSalesService.cpp \
    aftersale.cpp


LOCAL_CFLAGS += -Wunused-parameter -Wno-unused-variable 
LOCAL_CPPFLAGS += -fexceptions 

LOCAL_MODULE_TAGS := debug
LOCAL_MODULE_TAGS := optional
include $(BUILD_EXECUTABLE)
