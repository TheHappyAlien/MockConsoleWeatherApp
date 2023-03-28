package UnitTests;

import CSI.CSIThread;
import K.U.P.A.User;
import Sensors_TheOverkillPackage.HumiditySensor;
import Sensors_TheOverkillPackage.PressureSensor;
import Sensors_TheOverkillPackage.TemperatureSensor;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserSubscriptionTest {

    static User testUser = new User(" ");

    static List<String> usersnamesMock = new ArrayList<>();

    static List<String> stationLocationMock = new ArrayList<>();

    static CSIThread csiThreadMock = mock(CSIThread.class);

    @BeforeClass
    public static void CSISetup(){

        HumiditySensor humiditySensorMock = mock(HumiditySensor.class);
        PressureSensor pressureSensorMock = mock(PressureSensor.class);
        TemperatureSensor temperatureSensorMock = mock(TemperatureSensor.class);

        stationLocationMock.add("Location");

        testUser = new User("username");
        usersnamesMock.add("username");

        User.setCsiThread(csiThreadMock);

        when(csiThreadMock.getUsernameList()).thenReturn(usersnamesMock);
        when(csiThreadMock.getStationList()).thenReturn(stationLocationMock);
    }

    //region testSubscribeToLocation

    @Test
    public void testSubscribeToLocationSuccessful(){
        testUser.subscribeToLocation("Location");
        assertTrue(testUser.getWeatherData().containsKey("Location"));
    }

    @Test
    public void testSubscribeToLocationUnsuccessful(){

        testUser.subscribeToLocation("ThereIsNoSuchLocation");
        assertFalse(testUser.getWeatherData().containsKey("ThereIsNoSuchLocation"));

    }

    //endregion

    //region testUnsubscribingFromLocation

    @Test
    public void testUnsubscribeFromLocationSuccessful(){
        testUser.subscribeToLocation("Location");

        testUser.unsubscribeFromLocation("Location");
        assertFalse(testUser.getWeatherData().containsKey("Location"));
    }

    @Test
    public void testUnsubscribeFromLocationUnsuccessful(){
        testUser.subscribeToLocation("Location");

        testUser.unsubscribeFromLocation("ThereIsNoSuchLocation");
        assertTrue(testUser.getWeatherData().containsKey("Location"));
    }

    //endregion



}
