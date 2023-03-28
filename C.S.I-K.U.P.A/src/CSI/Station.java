package CSI;

import Sensors_TheOverkillPackage.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Station{

    @Expose
    private String location;

    @Expose
    private Sensor humiditySensor;
    @Expose
    private Sensor pressureSensor;
    @Expose
    private Sensor temperatureSensor;

    @Expose
    private final StationInfo stationInfo;

    @Expose(serialize = false)
    private SensorValueData currentData;

    public Station(String location, Sensor humiditySensor, Sensor pressureSensor, Sensor temperatureSensor){

        this.location = location;
        this.humiditySensor = humiditySensor;
        this.pressureSensor = pressureSensor;
        this.temperatureSensor = temperatureSensor;

        stationInfo = new StationInfo(location, humiditySensor, pressureSensor, temperatureSensor);

    }

    //region Getters

    public String getLocation() {
        return location;
    }

    public StationInfo getStationInfo(){
        return stationInfo;
    }

    public String getStationInfoJSON(){

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        return gson.toJson(stationInfo);

    }

    public Sensor getHumiditySensor() {
        return humiditySensor;
    }

    public Sensor getPressureSensor() {
        return pressureSensor;
    }

    public Sensor getTemperatureSensor() {
        return temperatureSensor;
    }

    public SensorValueData getSensorData(){
        currentData = new SensorValueData (humiditySensor.getCurrentValue(), pressureSensor.getCurrentValue(), temperatureSensor.getCurrentValue());
        return currentData;
    }

    //endregion

    public void addHumiditySensor(Sensor humiditySensor){
        if (this.humiditySensor.getCurrentValue() == -300){
            this.humiditySensor = humiditySensor;
            stationInfo.addHumiditySensorInfo(humiditySensor);
        }
    }

    public void addPressureSensor(Sensor pressureSensor){
        if (this.pressureSensor.getCurrentValue() == -300){
            this.pressureSensor = pressureSensor;
            stationInfo.addPressureSensorInfo(pressureSensor);
        }
    }

    public void addTemperatureSensor(Sensor temperatureSensor){
        if (this.temperatureSensor.getCurrentValue() == -300){
            this.temperatureSensor = temperatureSensor;
            stationInfo.addTemperatureSensorInfo(temperatureSensor);
        }
    }


    //region Setters

    public void setLocation(String location){
        this.location = location;
    }

    public void setHumiditySensor(HumiditySensor humiditySensor) {
        this.humiditySensor = humiditySensor;
        stationInfo.addHumiditySensorInfo(humiditySensor);
    }

    public void setPressureSensor(PressureSensor pressureSensor) {
        this.pressureSensor = pressureSensor;
        stationInfo.addPressureSensorInfo(pressureSensor);
    }

    public void setTemperatureSensor(TemperatureSensor temperatureSensor) {
        this.temperatureSensor = temperatureSensor;
        stationInfo.addTemperatureSensorInfo(temperatureSensor);
    }

    //endregion

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        } else if (!(o instanceof Station)){
            return false;
        } else return ((Station) o).getLocation().equals(this.location);
    }


}

