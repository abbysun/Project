package com.sun.abby.cst2335_finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class to connect to the URL to download the weather for Ottawa
 * @author yun
 */
public class Weather_Fragment extends Fragment {
    /**
     * weatherURL String object that is url address
     */
    private static final String weatherURL =
            "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    /**
     * current_temp TextView object that is for the value of current temperature
     */
    private TextView current_temp;
    /**
     * mininum_temp TextView object that is the value of minimum temperature
     */
    private TextView mininum_temp;
    /**
     * maxinum_temp TextView object that is the value of max temperature
     */
    private TextView maxinum_temp;

    /**
     * default constructor of Weather_Fragment class
     */
    public Weather_Fragment() {
        // Required empty public constructor
    }

    /**
     * To creates and returns the view hierarchy associated with the fragment
     * @param inflater The LayoutInflater object that used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here
     * @return the container of the fragment
     */
    @Override
public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    View v = inflater.inflate(R.layout.fragment_weather_, container,false);
    current_temp = (TextView)v.findViewById(R.id.currentTemp);
    mininum_temp = (TextView)v.findViewById(R.id.minTemp);
    maxinum_temp = (TextView)v.findViewById(R.id.maxTemp);

    new ForecastQuery().execute(weatherURL);
            return v;
}//end of onCreateView

    /**
     * inner class extends AsyncTask which is easy use of the UI thread
     */
    class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String cur_temp;
        private String min_temp;
        private String max_temp;

        /**
         * This method is used to perform background computation that can take a long time
         * @param params String argument
         * @return success if connect to url
         */
        protected String doInBackground(String... params) {

            HttpURLConnection conn = null;

            try {
                URL url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(3000 /* milliseconds */);
                conn.setConnectTimeout(4000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                // Starts the query
                conn.connect();

                XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
                XmlPullParser parser =factory.newPullParser();
               // XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

                parser.setInput(conn.getInputStream(), null);

                parser.nextTag();

                while (parser.next() != XmlPullParser.END_DOCUMENT) {

                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();
                    // Starts by looking for the entry tag
                    if (name.equals("temperature")) {

                        cur_temp = parser.getAttributeValue(null, "value");
                        min_temp = parser.getAttributeValue(null, "min");
                        Thread.sleep(1000);

                        max_temp = parser.getAttributeValue(null, "max");
                        Thread.sleep(1000);

                    }

                }
                return "success";
            } catch (MalformedURLException e) {

                return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }//end of if
            }// end of finally


        }// end of doInBackground

        /**
         * To invoke on the UI thread after the background computation finishes
         * @param s Sting object
         */
        protected void onPostExecute(String s) {
            current_temp.setText("Current temperature: " + cur_temp);
            mininum_temp.setText("Minimum temperature: " + min_temp);
            maxinum_temp.setText("Maximum temperature: " + max_temp);


        } // end of onPostExecute
    }// end of ForecastQuery
    }//end of Fragment