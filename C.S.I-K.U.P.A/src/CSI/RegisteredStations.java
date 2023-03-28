package CSI;

import Sensors_TheOverkillPackage.EmptySensor;
import Sensors_TheOverkillPackage.HumiditySensor;
import Sensors_TheOverkillPackage.PressureSensor;
import Sensors_TheOverkillPackage.TemperatureSensor;

import java.util.ArrayList;
import java.util.List;

public class RegisteredStations {

    public static List<Station> getRegisteredStations() {

        List<Station> registeredStations = new ArrayList<>();

            registeredStations.add(new Station("Wrocław", new HumiditySensor(), new PressureSensor(980, 1030), new TemperatureSensor(-10, 25)));
            registeredStations.add(new Station("Wałbrzych", new EmptySensor(), new PressureSensor(970, 1025), new TemperatureSensor(-15, 15)));
            registeredStations.add(new Station("Jelenia Góra", new EmptySensor(), new EmptySensor(), new TemperatureSensor(-10, 20)));
            registeredStations.add(new Station("Świdnica", new HumiditySensor(), new PressureSensor(985, 1035), new EmptySensor()));

        return registeredStations;
    }
}
