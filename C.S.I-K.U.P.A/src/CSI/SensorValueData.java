package CSI;

public class SensorValueData {

    private int humidity;
    private int pressure;
    private int temperature;

    public SensorValueData(int humidity, int pressure, int temperature) {
        this.humidity = humidity;
        this.pressure = pressure;
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public int getTemperature() {
        return temperature;
    }

}
