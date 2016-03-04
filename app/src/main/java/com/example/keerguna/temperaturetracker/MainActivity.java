package com.example.keerguna.temperaturetracker;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements SensorEventListener{

    //Temperature array with random values for five days of the week
    public static double[] temperatureList = {34.6,45.7,57.6,34.5,22.4};

    //Sensor Manager for temperature measurement
    private SensorManager senseManage;
    private Sensor envSense;

    //TextView where the temperature read from the sensor is displayed
    public TextView ambTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tempText[] = new TextView[5];

        //Getting the references to the text view elements
        ambTemp = (TextView) findViewById(R.id.textView2);
        tempText[0] =(TextView) findViewById(R.id.temp1);
        tempText[1] =(TextView) findViewById(R.id.temp2);
        tempText[2] =(TextView) findViewById(R.id.temp3);
        tempText[3] =(TextView) findViewById(R.id.temp4);
        tempText[4] =(TextView) findViewById(R.id.temp5);
        for(int i=0; i<5; i++){
            tempText[i].setText(String.valueOf(temperatureList[i])+"°C");
        }

        //Sensor Manager instance is instantiated
        senseManage = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //The temperature sensor is initialized
        envSense = senseManage.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        if(envSense==null)
            ambTemp.setText("Warning ! \n No Sensor!");
        else{
            senseManage.registerListener(this, envSense, SensorManager.SENSOR_DELAY_UI);
            ambTemp.setText("" + envSense.getPower());
        }


        /*
            Method to convert from fahrenheit to celsius and vice versa
            When the button is pressed the values are converted from one unit to another using C program
            C functions are called using JNI
         */
        final ToggleButton convert = (ToggleButton) findViewById(R.id.toggleButton);

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int choice;
                if(convert.isChecked()){
                    choice = 0;

                    temperatureList = conversion(choice, temperatureList);
                    //The results of the C function is set to the text view
                    for(int i=0; i<5; i++){
                        tempText[i].setText(String.format("%.1f",temperatureList[i])+"°F");
                    }

                }else{
                    choice = 1;

                    temperatureList = conversion(choice, temperatureList);
                    //The results of the C function is set to the text view
                    for(int i=0; i<5; i++){
                        tempText[i].setText(String.format("%.1f",temperatureList[i])+"°C");
                    }
                }
            }
        });

    }

    //Native function which does the unit conversion operation
    public static native double[] conversion(int n,double[] array);

    static{
        System.loadLibrary("unit-conversion");
    }

    //Function for change in temperature sensor values
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] sensorValue = event.values;
        ambTemp.setText(String.format("%.1f", sensorValue[0]) + "°C");
    }

    //Functions for change in accuracy
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
