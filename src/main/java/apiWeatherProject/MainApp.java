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

    private void startApp() {
        scanner = new Scanner(System.in);
        System.out.println("Wybierz po czym chcesz znaleźć miejsce dla którego wyświetlisz pogodę \n0 - Zakończ działanie \n1 - Nazwa Miasta \n2 - Kod pocztowy");
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

        }
    }

    private void connectByZipCode() {


    private void parseJson(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonWeather = jsonObject.getJSONArray("weather1");
        List<Weather> weatherList = new ArrayList<>();

        for (int i = 0; i < jsonWeather.length(); i++) {
            JSONObject one = (JSONObject) jsonWeather.get(i);
            Weather weather = new Weather();
            weather.setTemp(Integer.parseInt(one.get("temp").toString()));
            weather.setTemp_max(Integer.parseInt(one.get("temp_max").toString()));
            weather.setAverageTemperature(Integer.parseInt(one.get("average temperature").toString()));
            weather.setClouds(one.get("clouds").toString());
            weather.setWind(one.get("wind").toString());
            weather.setPressure(one.get("pressure").toString());
            weather.setVisibility(one.get("visibility").toString());
            weather.setWeatherDescription(one.get("weather description").toString());
        }

        System.out.println("Logs: ");
    }

    @Override
    public void run() {
        startApp();
    }
}

