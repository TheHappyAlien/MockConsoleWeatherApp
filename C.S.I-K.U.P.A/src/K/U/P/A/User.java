package K.U.P.A;

import CSI.CSIThread;
import CSI.SensorValueData;
import CSI.Station;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class User {

    private final Scanner input = new Scanner(System.in);

    private final String username;

    private final Map<String, List<SensorValueData>> weatherData = new HashMap<>();

    public User(String username){
        this.username = username;
    }

    private static CSIThread csiThread;

    public void UIStart(){
        inputMessage();
        UIInput();
    }

    public void update(Map<String, SensorValueData> currentData){
        for (String location : currentData.keySet()){
            weatherData.get(location).add(currentData.get(location));
        }
    }


    private void UIInput(){
        switch (input.nextLine()){
            case "1":
               inputMessage();
                break;
            case "2":
                System.out.println(showCurrentWeather());
                break;
            case "3":
                showWeatherStatistics();
                break;
            case "4":
                printAllLocations();
                System.out.println("\nType in the location you wish to subscribe to.");
                System.out.println(subscribeToLocation(input.nextLine()));
                break;
            case "5":
                printSubscribedLocations();
                System.out.println("\nType in the location you wish to unsubscribe from.\n" +
                        "You will no longer have access to any data regarding that location.\n");
                System.out.println(unsubscribeFromLocation(input.nextLine()));
                break;
            case "6":
                printSubscribedLocations();
                break;
            case "7":
                UserLoginScreen.goToLoginScreen();
                return;
            case "8":
                System.out.println("Type in the name of the file: ");
                JSONFileWriter(JSON(), input.nextLine());
                break;
            case "9":
                csiThread.stopRunning(); //Obviously would not work this way in a real app.
                return;
            default:
                System.out.println("That's not one of the available inputs.");
                System.out.println("To see all menu options type \"1\".\n");
                break;
        }
        UIInput();
    }

    public void inputMessage(){
        System.out.println("1: Show this list.\n" +
                "2: Show current weather.\n" +
                "3: Show weather statistics.\n" +
                "4: Subscribe to a location.\n" +
                "5: Unsubscribe from a location\n" +
                "6: Show list of subscribed locations.\n" +
                "7: Login screen.\n" +
                "8: Write collected weather data into a JSON file.\n" +
                "9: Exit.\n");
    }

    public String JSON(){

        Gson gson = new Gson();

       return gson.toJson(weatherData);

    }

    private void JSONFileWriter(String content, String fileName){

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            String[] tab = content.split("(?<=[{}\\[\\]])");
            for (String s : tab) {
                bw.write(s + "\n");
            }

            System.out.println("\nFile created successfully.\n");

        } catch (IOException e){
            System.out.println("\nSorry, an error has occurred whist trying to create a file.\n");
            e.printStackTrace();
        }

    }

    public String showCurrentWeather(){

        StringBuilder stringBuilder = new StringBuilder();

        for (String location : weatherData.keySet()) {

            if (weatherData.get(location).size() != 0) {

                stringBuilder.append("-").append(location).append(" | Humidity: ");

                int humidityData = weatherData.get(location).get(weatherData.get(location).size() - 1).getHumidity();

                if (humidityData == -300) {
                    stringBuilder.append("N/A | Pressure: ");
                } else {
                    stringBuilder.append(humidityData).append("% | Pressure: ");
                }

                int pressureData = weatherData.get(location).get(weatherData.get(location).size() - 1).getPressure();

                if (pressureData == -300) {
                    stringBuilder.append("N/A | Temperature: ");
                } else {
                    stringBuilder.append(pressureData).append(" hPa | Temperature: ");
                }

                int temperatureData = weatherData.get(location).get(weatherData.get(location).size() - 1).getTemperature();

                if (temperatureData == -300) {
                    stringBuilder.append("N/A\n");
                } else {
                    stringBuilder.append(temperatureData).append(" C\n");
                }
            }else stringBuilder.append("You currently have no data from ").append(location).append("\n");
        }

        return stringBuilder.toString();

    }

    private void showWeatherStatistics(){

        System.out.println("1: Show average weather.\n" +
                           "2: Show highest values.\n" +
                           "3: Show lowest values.\n");

        switch (input.nextLine()){
            case "1":
                System.out.println(showAverageWeather());
                break;
            case "2":
                System.out.println(showHighestValues());
                break;
            case "3":
                System.out.println(showLowestValues());
                break;
            default:
                System.out.println("Sorry, wrong input.\n");
                break;
        }
    }

    public String showAverageWeather(){

        StringBuilder stringBuilder = new StringBuilder();

        for (String location : weatherData.keySet()) {

            if (weatherData.get(location).size() != 0) {

                float averageHumidity = 0;
                float averagePressure = 0;
                float averageTemperature = 0;

                int sumH = 0;
                int sumP = 0;
                int sumT = 0;

                int counter = 0;

                for (SensorValueData data : weatherData.get(location)) {

                    sumH += data.getHumidity();
                    sumP += data.getPressure();
                    sumT += data.getTemperature();

                    counter++;
                }

                averageHumidity = (float) sumH / counter;
                averagePressure = (float) sumP / counter;
                averageTemperature = (float) sumT / counter;

                stringBuilder.append("-").append(location).append(" (average weather) | Humidity: ");

                if (averageHumidity == -300) {
                    stringBuilder.append("N/A | Pressure: ");
                } else {
                    stringBuilder.append(String.format("%3.2f", averageHumidity));
                    stringBuilder.append("% | Pressure: ");
                }

                if (averagePressure == -300) {
                    stringBuilder.append("N/A | Temperature: ");
                } else {
                    stringBuilder.append(String.format("%4.2f", averagePressure));
                    stringBuilder.append(" hPa | Temperature: ");
                }

                if (averageTemperature == -300) {
                    stringBuilder.append("N/A\n");
                } else {
                    stringBuilder.append(String.format("%2.2f", averageTemperature));
                    stringBuilder.append(" C\n");
                }
            }else{
                stringBuilder.append(location).append(": No data available.\n");
            }

        }
        return stringBuilder.toString();
    }

    public String showHighestValues(){

        StringBuilder stringBuilder = new StringBuilder();

        for (String location : weatherData.keySet()) {

            if (weatherData.get(location).size() != 0) {

                int maxH = -20000;
                int maxP = -20000;
                int maxT = -20000;

                for (SensorValueData data : weatherData.get(location)) {

                    if (data.getHumidity() > maxH) {
                        maxH = data.getHumidity();
                    }

                    if (data.getPressure() > maxP) {
                        maxP = data.getPressure();
                    }

                    if (data.getTemperature() > maxT) {
                        maxT = data.getTemperature();
                    }
                }

                stringBuilder.append("-").append(location).append(" (max values) | Humidity: ");

                if (maxH == -300) {
                    stringBuilder.append("N/A | Pressure: ");
                } else {
                    stringBuilder.append(maxH).append("% | Pressure: ");
                }

                if (maxP == -300) {
                    stringBuilder.append("N/A | Temperature: ");
                } else {
                    stringBuilder.append(maxP).append(" hPa | Temperature: ");
                }

                if (maxT == -300) {
                    stringBuilder.append("N/A\n");
                } else {
                    stringBuilder.append(maxT).append(" C\n");
                }
            }else{
                stringBuilder.append(location).append(": No data available.\n");
            }
        }

        return stringBuilder.toString();

    }

    public String showLowestValues(){

        StringBuilder stringBuilder = new StringBuilder();

        for (String location : weatherData.keySet()) {

            if (weatherData.get(location).size() != 0) {

                int minH = 20000;
                int minP = 20000;
                int minT = 20000;

                for (SensorValueData data : weatherData.get(location)) {

                    if (data.getHumidity() < minH) {
                        minH = data.getHumidity();
                    }

                    if (data.getPressure() < minP) {
                        minP = data.getPressure();
                    }

                    if (data.getTemperature() < minT) {
                        minT = data.getTemperature();
                    }

                }

                stringBuilder.append("-").append(location).append(" (min values) | Humidity: ");

                if (minH == -300) {
                    stringBuilder.append("N/A | Pressure: ");
                } else {
                    stringBuilder.append(minH).append("% | Pressure: ");
                }

                if (minP == -300) {
                    stringBuilder.append("N/A | Temperature: ");
                } else {
                    stringBuilder.append(minP).append(" hPa | Temperature: ");
                }

                if (minT == -300) {
                    stringBuilder.append("N/A\n");
                } else {
                    stringBuilder.append(minT).append(" C\n");
                }
            } else {
                stringBuilder.append(location).append(": No data available.\n");
            }
        }

        return stringBuilder.toString();
    }

    private void printSubscribedLocations(){
        System.out.println("Locations you are currently subscribed to:");

            for (String str : this.weatherData.keySet()){
                for (String location : csiThread.getStationList()){
                    if (location.equals(str)){
                        System.out.println(location + " | " + csiThread.getStationInfo(location).getSensorAvailability());
                    }
                }
            }
    }

    private void printAllLocations(){
        System.out.println("List of available locations to subscribe to.");
        for (String location : csiThread.getStationList()){
            System.out.println("-" +  location);
        }
    }

    public String subscribeToLocation(String locationName){

        boolean subscriptionSuccessful = false;

        for (String location : csiThread.getStationList()){
            if (locationName.equals(location)){
                weatherData.put(location, new ArrayList<>());
                subscriptionSuccessful = true;
            }
        }
        if (subscriptionSuccessful) {
            return "\nYou will from now on receive weather information form: " + locationName + "\n";
        } else {
            return "Sorry, that location is not available.\n";
        }

    }

    public String unsubscribeFromLocation(String locationName){
        boolean unsubscribingSuccessful = false;

        String dataToRemove = null;

        for (String location : this.weatherData.keySet()){
            if (locationName.equals(location)){
                dataToRemove = location;
                unsubscribingSuccessful = true;
                break;
            }
        }

        if (unsubscribingSuccessful) {
            weatherData.remove(dataToRemove);
            return  "You successfully unsubscribed from: " + locationName + "\n";
        }else {
            return  "Sorry, you are not subscribed to that location.\n";
        }


    }

    public String getUsername() {
        return username;
    }

    public Map<String, List<SensorValueData>> getWeatherData() {
        return weatherData;
    }

    public static void setCsiThread(CSIThread csiRunnable){
        csiThread = csiRunnable;
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        } else if (!(o instanceof User)){
            return false;
        } else return ((User) o).getUsername().equals(this.username);
    }
}
