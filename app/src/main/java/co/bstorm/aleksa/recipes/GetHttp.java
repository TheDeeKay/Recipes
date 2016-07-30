package co.bstorm.aleksa.recipes;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aleksa on 7/30/16.
 */
public class GetHttp extends AsyncTask<Void, Void, Void>{

    @Override
    protected Void doInBackground(Void... params) {

        HttpURLConnection connection = null;

        try {

            URL url = new URL("http://jsonplaceholder.typicode.com/todos");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream is = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (is == null)
                return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;

            while ((line = reader.readLine()) != null)
                buffer.append(line + "\n");

            Log.e("AsyncTask", buffer.toString());

            return null;

        } catch (MalformedURLException e) {
            if (connection != null)
            e.printStackTrace();
        } catch (IOException e) {
            try {
                Log.e("Connection fail code", connection.getResponseMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Log.e("AsyncTask", "Odje je");
            e.printStackTrace();
        }

        return null;
    }
}
