package apiWeatherProject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainApp implements Runnable {

    private Scanner scanner;
    private HTTPService weatherService = new HTTPService();

    private void startApp() {
        scanner = new Scanner(System.in);
        System.out.println("Get weather information by: \n0 - Finish the action \n1 - City name \n2 - Zip code");
        Integer name = scanner.nextInt();
        chooseTypeSearching(name);
    }

    private void chooseTypeSearching(Integer typeNumber) {
        switch (typeNumber) {
            case 0:
                break;
            case 1:
                connectByCityName();
                startApp();
                break;
            case 2:
                connectByZipCode();
                startApp();
                break;
        }
    }

    private void connectByCityName() {
        System.out.println("City name: ");
        String cityName = scanner.next();
        String response = connectByCityName(cityName);
        parseJson(response);
    }

    public String connectByCityName(String cityName) {
        String response = null;
        try {
            response = new HTTPService().connect(Config.APP_URL + "?q=" + cityName + "&appid=" + Config.APP_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void connectByZipCode() {
        System.out.println("Zip code: ");
        String zipCode = scanner.next();
        String response = connectByZipCode(zipCode);
        parseJson(response);
    }

    public String connectByZipCode(String zipCode) {
        String response = null;
        try {
            response = new HTTPService().connect(Config.APP_URL + "?q=" + zipCode + "&appid=" + Config.APP_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void parseJson(String json) {
        //do przeanalizowania
        double temp;
        int pressure;
        int humidity;
        int clouds;

        JSONObject rootObject = new JSONObject(json);
        if (rootObject.getInt("cod") == 200) {
            JSONObject mainObject = rootObject.getJSONObject("main");
            DecimalFormat df = new DecimalFormat("#.##");
            temp = mainObject.getDouble("temp");
            temp = temp - 273;

            pressure = mainObject.getInt("pressure");
            humidity = mainObject.getInt("humidity");
            JSONObject cloudsObject = rootObject.getJSONObject("clouds");
            clouds = cloudsObject.getInt("all");

            System.out.println("Temperature: " + df.format(temp) + " \u00b0C");
            System.out.println("Humidity: " + humidity + " %");
            System.out.println("Pressure: " + pressure + " hPa");
            System.out.println("Cloud: " + clouds + "%");


        } else {
            System.out.println("Operation error");
        }
    }
    //JSONObject jsonObject = new JSONObject(json);
    //JSONObject jsonArrayWeather = jsonObject.getJSONObject("main");
    //
    //String temp = jsonObject.getJSONObject("main").get("temp").toString();
    //String temp_max = jsonObject.getJSONObject("main").get("temp_max").toString(); (...)

    @Override
    public void run() {
        startApp();
    }
}

