package CSI;

import K.U.P.A.User;
import K.U.P.A.UserData;
import Sensors_TheOverkillPackage.EmptySensor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSIThread extends Thread {

    private final List<Station> stations = new ArrayList<>();

    private final List<User> users = new ArrayList<>();

    private boolean keepGoing = true;

    private final Object synchronizer = new Object();

    private final Map<String, SensorValueData> currentData = new HashMap<>();

    public void setup(){
        stations.addAll(RegisteredStations.getRegisteredStations());
        users.addAll(UserData.getUsers());
    }

    @Override
    public void run() {

        if (keepGoing) {

            synchronized (synchronizer) {
                for (User user : users) {

                    currentData.clear();

                    for (Station station : stations) {
                        if (user.getWeatherData().containsKey(station.getLocation())) {
                            currentData.put(station.getLocation(), station.getSensorData());
                        }
                    }

                    user.update(currentData);

                }
            }

            try {
                sleep(5000);
            } catch (InterruptedException ignored) {
            }

            run();

        }

    }

    public List<String> getStationList() {

        List<String> locations = new ArrayList<>();

        for (Station station : stations){
            locations.add(station.getLocation());
        }

        return locations;
    }

    public StationInfo getStationInfo(String location){
        for (Station station : stations){
            if (station.getLocation().equals(location)){
                return station.getStationInfo();
            }
        }
        return new StationInfo("LocationUnavailable", new EmptySensor(), new EmptySensor(), new EmptySensor());
    }

    public List<String> getUsernameList() {
        List<String> usernames = new ArrayList<>();

        for (User user : users){
            usernames.add(user.getUsername());
        }

        return usernames;
    }

    public void startUI (String username){
        for (User user : users){
            if (username.equals(user.getUsername())){
                user.UIStart();
                break;
            }
        }
    }

    public void addStation(Station station) {
        synchronized (synchronizer) {
            stations.add(station);
        }
    }

    public void addUser(User user){
        synchronized (synchronizer) {
            users.add(user);
        }
    }

    public void stopRunning(){
        keepGoing = false;
        this.interrupt();
    }
}


