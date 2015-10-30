package com.example.user.weather;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    MyAsyncTask mATask;
    EditText cityName;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityName = (EditText) findViewById(R.id.city_name);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        city = (cityName.getText().toString().isEmpty()) ? "Саратов" : cityName.getText().toString();
        mATask = new MyAsyncTask();
        mATask.execute(city);
    }


    private class MyAsyncTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params)  {
            API.ApiResponse ap = API.execute("weather", API.HttpMethod.GET, "q", params[0], "lang", "ru","units",
                    "metric", "APPID", "532daa806f83560bfa5978caf25db6c3");
            /*API.ApiResponse ap = API.executeGet("weather?q=Saratov,ru&APPID=532daa806f83560bfa5978caf25db6c3");*/
            JSONObject result;
            result = ap.getJson();
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            Intent intent = new Intent(MainActivity.this, WeatherInfo.class);
            intent.putExtra("city", city);
            intent.putExtra("data", result.toString());
            startActivity(intent);
        }

    }
}
