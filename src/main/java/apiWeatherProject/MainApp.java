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
        try {
            System.out.println("Enter the city name: ");
            scanner.nextLine();
            String cityToCheck = scanner.nextLine();
            String weather = weatherService.connect(Config.APP_URL + "?q=" + cityToCheck + "&appid=" + Config.APP_ID);
            System.out.println(weather);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectByZipCode() {
        System.out.println("Podaj kod pocztowy miasta: ");
        String zipcode = scanner.next();
        try {
            String response = new HTTPService().connect(Config.APP_URL + "?zip=" + zipcode + ",pl" + "&appid=" + Config.APP_ID);
            parseJson(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            System.out.println("Error");
        }
    }

    @Override
    public void run() {
        startApp();
    }
}

