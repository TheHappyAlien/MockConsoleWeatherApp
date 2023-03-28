package UnitTests;

import CSI.CSIThread;
import CSI.SensorValueData;
import K.U.P.A.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserShowWeatherDataTest {

    private static User testUser = new User(" ");

    private static final List<String> usernamesMock = new ArrayList<>();

    private static final List<String> stationListMock = new ArrayList<>();

    private static final CSIThread csiThreadMock = mock(CSIThread.class);

    private static final SensorValueData sensorValueData1 = new SensorValueData(10, 990, 10);
    private static final SensorValueData sensorValueData2 = new SensorValueData(20, 1000, 20);

    private static final SensorValueData sensorValueDataEmpty = new SensorValueData(-300, -300, -300);


    private static final Map<String, SensorValueData> sensorValueDataForTesting1 = new HashMap<>();
    private static final Map<String, SensorValueData> sensorValueDataForTesting2 = new HashMap<>();

    @BeforeClass
    public static void CSISetup(){

        stationListMock.add("Location");
        stationListMock.add("Location2");
        stationListMock.add("Location3");

        sensorValueDataForTesting1.put("Location", sensorValueData1);
        sensorValueDataForTesting1.put("Location2", sensorValueDataEmpty);

        sensorValueDataForTesting2.put("Location", sensorValueData2);

        testUser = new User("username");
        usernamesMock.add("username");

        User.setCsiThread(csiThreadMock);

        when(csiThreadMock.getUsernameList()).thenReturn(usernamesMock);
        when(csiThreadMock.getStationList()).thenReturn(stationListMock);
    }

    @Before
    public void resetData(){
        testUser.getWeatherData().remove("Location");
        testUser.getWeatherData().remove("Location2");
        testUser.getWeatherData().remove("Location3");

        testUser.subscribeToLocation("Location");
        testUser.subscribeToLocation("Location2");
        testUser.subscribeToLocation("Location3");

        testUser.update(sensorValueDataForTesting1);
        testUser.update(sensorValueDataForTesting2);
    }

    @Test
    public void testShowCurrentWeather(){

        String expected ="You currently have no data from Location3\n" +
                         "-Location2 | Humidity: N/A | Pressure: N/A | Temperature: N/A\n" +
                         "-Location | Humidity: 20% | Pressure: 1000 hPa | Temperature: 20 C\n";


        assertEquals(expected, testUser.showCurrentWeather());
    }

    @Test
    public void testShowAverageWeather(){

        String expected = "Location3: No data available.\n" +
                          "-Location2 (average weather) | Humidity: N/A | Pressure: N/A | Temperature: N/A\n" +
                          "-Location (average weather) | Humidity: 15,00% | Pressure: 995,00 hPa | Temperature: 15,00 C\n";


        assertEquals(expected, testUser.showAverageWeather());
    }

    @Test
    public void testShowHighestValues(){

        String expected = "Location3: No data available.\n" +
                "-Location2 (max values) | Humidity: N/A | Pressure: N/A | Temperature: N/A\n" +
                "-Location (max values) | Humidity: 20% | Pressure: 1000 hPa | Temperature: 20 C\n";


        assertEquals(expected, testUser.showHighestValues());
    }

    @Test
    public void testShowLowestValues(){

        String expected = "Location3: No data available.\n" +
                "-Location2 (min values) | Humidity: N/A | Pressure: N/A | Temperature: N/A\n" +
                "-Location (min values) | Humidity: 10% | Pressure: 990 hPa | Temperature: 10 C\n";


        assertEquals(expected, testUser.showLowestValues());
    }
}
