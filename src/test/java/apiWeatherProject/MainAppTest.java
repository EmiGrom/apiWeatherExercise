package apiWeatherProject;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.io.IOException;

public class MainAppTest {
    @BeforeAll
    static void init() {
        System.out.println("Start application testing");
    }

    @Test
    @DisplayName("Main connection test")
    @Tag("dev")
    void connectionTest() {
        HTTPService httpService = new HTTPService();
        JSONObject rootObject = null;
        try {
            String respone = httpService.connect(Config.APP_URL + "?q=" + "Warsaw" + "&appid=" + Config.APP_ID);
            rootObject = new JSONObject(respone);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(rootObject.getInt("cod") == 200);
    }

    @Test
    @DisplayName("Weather test for the Warsaw")
    void connectByCityNameTest() {
        MainApp mainApp = new MainApp();
        String responseTest = mainApp.connectByCityName("Warsaw");
        JSONObject jsonObject = new JSONObject(responseTest);
        Assertions.assertEquals(200, jsonObject.getInt("cod"));
        Assertions.assertEquals("Warsaw", jsonObject.getString("name"));
    }

    @Test
    @DisplayName("Weather test for the Warsaw by zip code")
    void connectByZipCodeTest() {
        MainApp mainApp = new MainApp();
        String responseTest = mainApp.connectByZipCode("05-077");
        JSONObject jsonObject = new JSONObject(responseTest);
        Assertions.assertEquals(200, jsonObject.getInt("cod"));
        Assertions.assertEquals("Warszawa/Wesoła", jsonObject.getString("name"));

    }

    @Test
    @DisplayName("Weather test for the Warsaw by GPS coordinates")
    void connectByGPSCoordinates() {
        MainApp mainApp = new MainApp();
        String responseTest = mainApp.connectByGPSCoordinates("52,14", "21,13");
        JSONObject jsonObject = new JSONObject(responseTest);
        Assertions.assertEquals(200, jsonObject.getInt("cod"));
        Assertions.assertEquals("Kabaty", jsonObject.getString("name"));

    }

    @Test
    @Disabled("Application wasn't served")
    void parseJsonForXDaysTest() {
        //TODO
    }

    @AfterAll
    static void done() {
        System.out.println("Complete tests");
    }
}
