package Sensors_TheOverkillPackage.UsabilityClasses;

import Sensors_TheOverkillPackage.*;

public class SensorFactory {

    public Sensor getSensor(SensorEnum sensorEnum, int minValue, int maxValue){
        switch (sensorEnum){
            case HUMIDITY:
                return new HumiditySensor();
            case PRESSURE:
                return new PressureSensor(minValue, maxValue);
            case TEMPERATURE:
                return new TemperatureSensor(minValue, maxValue);
            default:
                return new EmptySensor();
        }
    }

}
