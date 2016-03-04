//
// Created by Keerthana Gunasekaran on 3/4/16.
//
#include <jni.h>
#ifndef TEMPERATURE_TRACKER_UNIT_CONVERSION_H
#define TEMPERATURE_TRACKER_UNIT_CONVERSION_H

#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jdoubleArray Java_com_example_keerguna_temperaturetracker_MainActivity_conversion(JNIEnv *env, jobject instance,jint unit, jdoubleArray array_);
jdouble fahrenheit_to_celsius(jdouble f);
jdouble celsius_to_fahrenheit(jdouble c);
#ifdef __cplusplus
}
#endif

#endif //TEMPERATURE_TRACKER_UNIT_CONVERSION_H


