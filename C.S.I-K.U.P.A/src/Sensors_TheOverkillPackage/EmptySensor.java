package Sensors_TheOverkillPackage;

import com.google.gson.annotations.Expose;

public class EmptySensor implements Sensor{

    @Expose
    private boolean isEmpty;

    public EmptySensor(){
        isEmpty = true;
    }

    @Override
    public int getCurrentValue() {
        return -300;
    }
}
