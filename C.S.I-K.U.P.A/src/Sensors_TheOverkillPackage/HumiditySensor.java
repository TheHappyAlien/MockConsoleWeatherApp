package Sensors_TheOverkillPackage;

import java.util.Random;

public class HumiditySensor implements Sensor{

    public int getCurrentValue(){

        Random random = new Random();

        return random.nextInt(101);
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }else return o instanceof HumiditySensor;
    }

}
