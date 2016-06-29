LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Jni
LOCAL_SRC_FILES := DoodleJni.c
LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog 

include $(BUILD_SHARED_LIBRARY)

