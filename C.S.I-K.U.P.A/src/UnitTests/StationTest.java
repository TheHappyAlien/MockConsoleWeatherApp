package UnitTests;

import CSI.Station;
import Sensors_TheOverkillPackage.*;
import Sensors_TheOverkillPackage.UsabilityClasses.InterfaceAdapter;
import Sensors_TheOverkillPackage.UsabilityClasses.SensorEnum;
import Sensors_TheOverkillPackage.UsabilityClasses.SensorFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StationTest {

    static Station station = new Station("Location", new EmptySensor(), new EmptySensor(), new EmptySensor());
    static SensorFactory sensorFactory = new SensorFactory();

    @BeforeClass
    public static void setup(){
        station.addHumiditySensor(sensorFactory.getSensor(SensorEnum.HUMIDITY, 0, 0));
        station.addPressureSensor(sensorFactory.getSensor(SensorEnum.PRESSURE, 950, 1050));
        station.addTemperatureSensor(sensorFactory.getSensor(SensorEnum.TEMPERATURE, -10, 10));
    }

    @Test
    public void testAddSensorWhenNotPreviouslyUnavailable(){
        assertEquals(sensorFactory.getSensor(SensorEnum.HUMIDITY, 0, 0), station.getHumiditySensor());
        assertEquals(sensorFactory.getSensor(SensorEnum.PRESSURE, 950, 1050), station.getPressureSensor());
        assertEquals(sensorFactory.getSensor(SensorEnum.TEMPERATURE, -10, 10), station.getTemperatureSensor());
    }

    @Test
    public void testAddSensorWhenPreviouslyAvailable(){
        station.addPressureSensor(sensorFactory.getSensor(SensorEnum.PRESSURE, 900, 1000));
        station.addTemperatureSensor(sensorFactory.getSensor(SensorEnum.TEMPERATURE, 0, 25));

        assertEquals(sensorFactory.getSensor(SensorEnum.PRESSURE, 950, 1050), station.getPressureSensor());
        assertEquals(sensorFactory.getSensor(SensorEnum.TEMPERATURE, -10, 10), station.getTemperatureSensor());

    }

    @Test
    public void testStationInfoJSON(){
        String expected = "{\"location\":\"Location\",\"sensorAvailability\":\"HPT\"}";
        assertEquals(expected, station.getStationInfoJSON());
    }

    @Test
    public void testStationInfoJSONAfterDeserializationAndAddingSensors(){

        Station station1 = new Station("Location1", sensorFactory.getSensor(SensorEnum.HUMIDITY, 0, 0), sensorFactory.getSensor(SensorEnum.PRESSURE, 900, 1000), sensorFactory.getSensor(SensorEnum.EMPTY, 0, 0));

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Sensor.class, new InterfaceAdapter());
        Gson gson = builder.create();

        station1.addTemperatureSensor(sensorFactory.getSensor(SensorEnum.TEMPERATURE, 0, 20));
        station1.addHumiditySensor(sensorFactory.getSensor(SensorEnum.HUMIDITY, 0, 0));

        String expected = "{\"location\":\"Location1\",\"sensorAvailability\":\"HPT\"}";
        assertEquals(expected, station1.getStationInfoJSON());

    }
}
