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
    private String latitude;
    private String longitude;


    private void startApp() {
        scanner = new Scanner(System.in);
        System.out.println("What do you want to do \n0 - Finish the action \n1 - Get current weather information \n2 - Get 5 day forecast");
        Integer action = scanner.nextInt();
        chooseAction(action);
    }

    private void chooseAction(Integer typeNumber) {
        switch (typeNumber) {
            case 0:
                break;
            case 1:
                System.out.println("Get weather information by: \n0 - Finish the action \n1 - City name \n2 - Zip code \n3 - GPS coordinates");
                Integer name = scanner.nextInt();
                chooseTypeSearchingCurrent(name);
            case 2:
                System.out.println("Get weather information by: \n0 - Finish the action \n1 - City name \n2 - Zip code \n3 - GPS coordinates");
                name = scanner.nextInt();
                chooseTypeSearching5day(name);
        }

    }

    private void chooseTypeSearchingCurrent(Integer typeNumber) {
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
            case 3:
                connectByGPSCoordinates();
                startApp();
                break;
        }
    }
    private void chooseTypeSearching5day(Integer typeNumber) {
        switch (typeNumber) {
            case 0:
                break;
            case 1:
                connectByCityName5day();
                startApp();
                break;
            case 2:
                connectByZipCode5day();
                startApp();
                break;
            case 3:
                connectByGPSCoordinates5day();
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
            response = new HTTPService().connect(Config.APP_URL + "?zip=" + zipCode + ",pl" + "&appid=" + Config.APP_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void connectByGPSCoordinates() {
        System.out.println("GPS Coordinates: ");
        String longitude = scanner.next();
        String latitude = scanner.next();
        String response = connectByGPSCoordinates(latitude, longitude);
        parseJson(response);
    }

    public String connectByGPSCoordinates(String latitude, String longitude) {
        String response = null;
        try {
            response = new HTTPService().connect(Config.APP_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + Config.APP_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void connectByCityName5day() {
        System.out.println("City name: ");
        String cityName = scanner.next();
        String response = connectByCityName5day(cityName);
        parseJson5day(response);
    }

    public String connectByCityName5day(String cityName) {
        String response = null;
        try {
            response = new HTTPService().connect(Config.APP_ID5DAY + "?q=" + cityName + "&appid=" + Config.APP_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void connectByZipCode5day() {
        System.out.println("Zip code: ");
        String zipCode = scanner.next();
        String response = connectByZipCode5day(zipCode);
        parseJson5day(response);
    }

    public String connectByZipCode5day(String zipCode) {
        String response = null;
        try {
            response = new HTTPService().connect(Config.APP_ID5DAY + "?zip=" + zipCode + ",pl" + "&appid=" + Config.APP_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void connectByGPSCoordinates5day() {
        System.out.println("GPS Coordinates: ");
        String longitude = scanner.next();
        String latitude = scanner.next();
        String response = connectByGPSCoordinates5day(latitude, longitude);
        parseJson5day(response);
    }

    public String connectByGPSCoordinates5day(String latitude, String longitude) {
        String response = null;
        try {
            response = new HTTPService().connect(Config.APP_ID5DAY + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + Config.APP_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    //api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={your api key}

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

    private void parseJson5day(String json) {
        //TODO
        double temp;
        int pressure;
        int humidity;
        int clouds;
        String dt_txt;

        JSONObject rootObject = new JSONObject(json);
        JSONArray listArray = rootObject.getJSONArray("list");
        if (rootObject.getInt("cod") == 200) {



            for (int i=0; i<listArray.length();i++) {
                JSONObject dayObject = listArray.getJSONObject(i);
                JSONObject mainObject = dayObject.getJSONObject("main");
                DecimalFormat df = new DecimalFormat("#.##");
                temp = mainObject.getDouble("temp");
                temp = temp - 273;

                pressure = mainObject.getInt("pressure");
                humidity = mainObject.getInt("humidity");
                JSONObject cloudsObject = dayObject.getJSONObject("clouds");
                clouds = cloudsObject.getInt("all");
                dt_txt = dayObject.getString("dt_txt");

                System.out.println("Time "+dt_txt+ "   Temperature: " + df.format(temp) +
                        " \u00b0C"+"   Humidity: " + humidity + " %" +
                        "   Pressure: " + pressure + " hPa"+"   Cloud: " + clouds + "%");

               }
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

