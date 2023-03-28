package CSI;

import Sensors_TheOverkillPackage.*;
import com.google.gson.annotations.Expose;

import java.util.*;

public class StationInfo {

    @Expose
    private String location;
    @Expose
    private String sensorAvailability;

    @Expose(serialize = false)
    StringBuilder sensorAvailabilityBuilder;

    @Expose(serialize = false)
    private TreeSet<String> forSorting;

    /* Creating StationInfo instance through deserialization will yield one without 'sensorAvailabilityBuilder' and 'forSorting' fields, which is fine
       since we're only using them to sort the sensor codes in 'sensorAvailability'.
       The issue that arises when we try to add a new sensor later to an object without those fields declared, is solved in 'addAThing' method.
     */

    public StationInfo(String location, Sensor humiditySensor, Sensor pressureSensor, Sensor temperatureSensor){

        sensorAvailabilityBuilder = new StringBuilder();
        forSorting = new TreeSet<>();

        this.location = location;
        if (humiditySensor.getCurrentValue() != -300){
            forSorting.add("H");
        }
        if (pressureSensor.getCurrentValue() != -300){
            forSorting.add("P");
        }
        if (temperatureSensor.getCurrentValue() != -300){
            forSorting.add("T");
        }

        for (String s : forSorting) {
            sensorAvailabilityBuilder.append(s);
        }

        sensorAvailability = sensorAvailabilityBuilder.toString();
    }

    public String getSensorAvailability() {
        return sensorAvailability;
    }

    public void addHumiditySensorInfo(Sensor humiditySensor){
        if (humiditySensor.getCurrentValue() != -300){
            addAThing("H");
        }

    }

    public void addPressureSensorInfo(Sensor pressureSensor){
        if (pressureSensor.getCurrentValue() != -300){
            addAThing("P");
        }

    }

    public void addTemperatureSensorInfo(Sensor temperatureSensor){
        if (temperatureSensor.getCurrentValue() != -300){
            addAThing("T");
        }

    }

    public void changeLocationInfo(String location){
        this.location = location;
    }

    private void addAThing(String sensorCode){

        sensorAvailabilityBuilder = new StringBuilder(); /* Ensures that it's not null during deserialization */
        forSorting = new TreeSet<>();                    /* -||- */

        List<String> sensorsAlreadyAvailable = Arrays.asList(sensorAvailability.split("")); /* in here so you don't lose info about already available sensors, whilst
                                                                                                     adding a new one if you created a 'Station' object through
                                                                                                     deserialization. */


        forSorting.add(sensorCode);
        forSorting.addAll(sensorsAlreadyAvailable); /* Sets it to correct value, that was lost during serialization, as we don't serialize this field.*/

        for (String s : forSorting) {
            sensorAvailabilityBuilder.append(s); /* We've already added all pre-serialization sensor codes to 'forSorting' list,
                                                    so we'll also have them in the StringBuilder after this line, thus returning to pre-serialization state */
        }

        sensorAvailability = sensorAvailabilityBuilder.toString();
    }
}
