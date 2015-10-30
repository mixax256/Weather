package com.example.user.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 15.10.2015.
 */
public class WeatherInfo extends AppCompatActivity {
    static double toMmHg = 0.75006375541921;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);
        TextView tVTemp = (TextView) findViewById(R.id.temp);
        TextView tVPressure = (TextView) findViewById(R.id.pressure);
        TextView tVCity = (TextView) findViewById(R.id.city);
        TextView tVWind = (TextView) findViewById(R.id.windInfo);
        TextView tVPrecipitation = (TextView) findViewById(R.id.precipitation);

        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        String data = intent.getStringExtra("data");
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonSys = null;
        try {
            jsonSys = jsonObj.getJSONObject("sys");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String coutry = "";
        try {
            coutry = jsonSys.getString("country");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonMain = null;
        try {
            jsonMain = jsonObj.getJSONObject("main");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        double temp = -1;
        try {
            temp = jsonMain.getDouble("temp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int pressure = -1;
        try {
            pressure = jsonMain.getInt("pressure");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonWind = null;
        try {
            jsonWind = jsonObj.getJSONObject("wind");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        double windSpeed = -1;
        try {
            windSpeed = jsonWind.getDouble("speed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int windDeg = -1;
        try {
            windDeg = jsonWind.getInt("deg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String windDegree = windDegree(windDeg);
        String weather = "";
        try {
             weather = jsonObj.getString("weather");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        weather = currentWeather(weather);

        tVCity.setText(tVCity.getText() + "\t\t" + city + ", страна: " + coutry);
        tVTemp.setText(tVTemp.getText() + "\t\t" + Double.toString(temp) + "°C");
        tVPressure.setText(tVPressure.getText() + "\t\t" + Math.round(pressure * toMmHg) + " мм рт. ст.");
        tVWind.setText("Скорость ветра: " + windSpeed + " м/с, \nНаправление: " + windDegree);
        tVPrecipitation.setText(tVPrecipitation.getText() + "\t\t" + weather);
    }

    private String windDegree(int angle) {
        String windDegree;
        if (angle >= 0 && angle < 20 || (angle >= 340 && angle <= 360)) {
            windDegree = "C";
        }
        else if (angle < 70) {
            windDegree = "СВ";
        }
        else if (angle < 110) {
            windDegree = "B";
        }
        else if (angle < 160) {
            windDegree = "ЮВ";
        }
        else if (angle < 200) {
            windDegree = "Ю";
        }
        else if (angle < 250) {
            windDegree = "ЮЗ";
        }
        else if (angle < 290) {
            windDegree = "З";
        }
        else
            windDegree = "СЗ";
        return windDegree;
    }
    private String currentWeather(String s) {
        char[] c = s.substring(39).toCharArray();
        s = "";
        int i = 0;
        while (c[i] != '"') {
            s += c[i];
            i++;
        }
        return s;
    }
}