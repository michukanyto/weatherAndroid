package Controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.appsmontreal.weather.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String,Void,String> {

    Context context;

    public Double temperature;
    public Double maximal;
    public Double minimal;
    public int humidity;

    public DownloadTask(Context context) {
        this.context = context;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getMaximal() {
        return maximal;
    }

    public Double getMinimal() {
        return minimal;
    }

    public int getHumidity() {
        return humidity;
    }

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {

            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            while (data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);

            String weatherInfo = jsonObject.getString("main");

            Log.i("Weather content", weatherInfo);
            JSONObject jsonObject1 = new JSONObject(weatherInfo);

            temperature = (Double) jsonObject1.getDouble("temp");
            Log.i("temperture======", temperature.toString());
            minimal = (Double) jsonObject1.getDouble("temp_min");
            Log.i("Minimal======", minimal.toString());
            maximal = (Double) jsonObject1.getDouble("temp_max");
            Log.i("Maximal======", maximal.toString());
            humidity = (int) jsonObject1.getInt("humidity");
            Log.i("humidity======", String.valueOf(humidity));

            ((MainActivity)context).convertToCelsius();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
