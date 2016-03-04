//
// Created by Keerthana Gunasekaran on 3/2/16.
//
#include "unit_conversion.h"

jdouble  fahrenheit_to_celsius(jdouble f){
    return (f-32) / 1.8;
}

jdouble celsius_to_fahrenheit(jdouble c){
    return c * 1.8 + 32;
}


//Native function for unit conversion
JNIEXPORT jdoubleArray Java_com_example_keerguna_temperaturetracker_MainActivity_conversion(JNIEnv *env, jobject instance,jint unit, jdoubleArray array_) {


    jdouble *array ;

    //The array elemenst(temperature values) are copied to a variable "array"
    array = (*env)->GetDoubleArrayElements(env, array_, 0);
    jdoubleArray result = (*env)->NewDoubleArray(env, 5);

    int i;
    jdouble tempArray[5];

    //The value unit denotes whether the conversion is from celsius to fahrenheit or from fahrenheit to celsius
    if(unit == 1 ){
        for(i=0; i<5; i++){
            tempArray[i] = fahrenheit_to_celsius(array[i]);
        }
    }
    else{
       for(i=0; i<5; i++){
            tempArray[i] = celsius_to_fahrenheit(array[i]);
        }
    }

    //The array elements are released
    (*env)->ReleaseDoubleArrayElements(env, array_, array, 0);

    //A new array is initialised and the value is returned to Java through JNI
    (*env)->SetDoubleArrayRegion(env, result, 0, 5, tempArray);
    return result;
}
