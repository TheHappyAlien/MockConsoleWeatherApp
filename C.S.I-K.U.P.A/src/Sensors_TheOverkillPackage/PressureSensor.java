package Sensors_TheOverkillPackage;

import com.google.gson.annotations.Expose;

import java.util.Random;

public class PressureSensor implements Sensor {

    @Expose
    private int minPressure;
    @Expose
    private int maxPressure;

    public PressureSensor(int minPressure, int maxPressure){
            this.minPressure = minPressure;
            this.maxPressure = maxPressure;
    }

    public int getCurrentValue(){

        Random random = new Random();

        return random.nextInt((maxPressure - minPressure) + 1) + minPressure;
    }

    public int getMinPressure() {
        return minPressure;
    }

    public int getMaxPressure() {
        return maxPressure;
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }else if (o instanceof PressureSensor){
            PressureSensor pressureSensor = (PressureSensor) o;
           return pressureSensor.getMinPressure() == minPressure && pressureSensor.getMaxPressure() == maxPressure;
        }else return false;
    }

}
