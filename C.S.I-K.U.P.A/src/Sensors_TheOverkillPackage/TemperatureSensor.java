package Sensors_TheOverkillPackage;

import com.google.gson.annotations.Expose;

import java.util.Random;

public class TemperatureSensor implements Sensor{

    @Expose
    private int minTemp;
    @Expose
    private int maxTemp;

    public TemperatureSensor(int minTemp, int maxTemp){
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public int getCurrentValue(){

        Random random = new Random();


        return random.nextInt(maxTemp - minTemp + 1) + minTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }else if (o instanceof TemperatureSensor){
            TemperatureSensor temperatureSensor = (TemperatureSensor) o;
            return temperatureSensor.getMinTemp() == minTemp && temperatureSensor.getMaxTemp() == maxTemp;
        }else return false;
    }
}
