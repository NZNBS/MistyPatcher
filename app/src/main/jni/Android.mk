LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := MRZH4CKER

LOCAL_SRC_FILES := MRZH4X.cpp \ src/main.cpp \ src/KittyMemory/KittyMemory.cpp \ src/KittyMemory/MemoryPatch.cpp
include $(BUILD_SHARED_LIBRARY)