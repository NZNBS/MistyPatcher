#include <pthread.h>
#include "KittyMemory/MemoryPatch.h"
#include "KittyMemory/Logger.h"
#include <fstream>
#include <iostream>
#include <string>
using namespace std;

struct My_Patches {
    MemoryPatch anttena;
} MRZPatchs;

void *Anttena(void *) {
    ProcMap il2cppMap;
    do {
        il2cppMap = KittyMemory::getLibraryMap("libunity.so");
        sleep(1);
    } while (!il2cppMap.isValid());
    MRZPatchs.anttena = MemoryPatch("libunity.so", 0x36B82C,
                                                   "\xDC\x0F\x00\xE3\x1E\xFF\x2F\xE1", 8);
    if (MRZPatchs.anttena.Modify()) {
        LOGD("Anttena True");
    }
    return NULL;
}
extern "C"
JNIEXPORT void JNICALL Java_com_mreoz_mistypatcher_Adaptadores_MRZH4CK3R_createConfig(JNIEnv *env, jobject instance) {
	string mods;
	ifstream file1("/data/user/0/com.mreoz.mistypatcher/virtual/data/app/com.dts.freefireth/lib/MRZTEAM.txt", ios::in);
	if (!file1.is_open()){
	ofstream file2("/data/user/0/com.mreoz.mistypatcher/virtual/data/app/com.dts.freefireth/lib/MRZTEAM.txt");
       file2.close();
    }
}
