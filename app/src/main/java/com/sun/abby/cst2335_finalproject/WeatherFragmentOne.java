package com.sun.abby.cst2335_finalproject;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragmentOne extends Fragment {

    private static final String weatherURL =
            "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

    private ImageView weather_image;
    private TextView cur_temp;
   // private TextView location;
    private TextView lastUpdate;
    private TextView detail;
    private ProgressBar pBar;
    static Bitmap weather_icon = null;


    public WeatherFragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_weather_fragment_one, container, false);
      //  location = (TextView)view. findViewById(R.id.location);
        lastUpdate = (TextView)view.findViewById(R.id.lastupdate);
        weather_image = (ImageView)view.findViewById(R.id.weather_image);
        detail= (TextView)view.findViewById(R.id.detail);
        cur_temp = (TextView)view.findViewById(R.id.temperature);
        pBar = (ProgressBar)view.findViewById(R.id.progressBar);

        new WeatherQuery().execute(weatherURL);

        return view;
    }// end of onCreateView

    class WeatherQuery extends AsyncTask<String, Integer, String> {
       // private String loc;
        private String update;
        private String humidity;
        private String pressure;
        private String curr_tem;
        private Bitmap cur_weather;
        private String iconName;


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

                // return conn.getInputStream();

                XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
                XmlPullParser parser =factory.newPullParser();
                //XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

                parser.setInput(conn.getInputStream(), null);
                parser.nextTag();

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();
                    // Starts by looking for the entry tag
                    if(name.equals("lastupdate")){
                        update = parser.getAttributeValue(null, "value");
                        //lastUpdate.setText("Last update: "+update);
                        this.publishProgress(25);
                    }

                   else if (name.equals("temperature")) {
                        curr_tem = parser.getAttributeValue(null, "value");
                        this.publishProgress(50);}

                   else if(name.equals("humidity")){

                        humidity = parser.getAttributeValue(null, "value");}
                   else if (name.equals("pressure"))   {
                        pressure= parser.getAttributeValue(null, "value");
                        Thread.sleep(1000);
                        this.publishProgress(75);

                    } else if (name.equals("weather")) {
                        iconName = parser.getAttributeValue(null, "icon");
                    }
                }

                String bitmapName = iconName + ".png";

                if (fileExistance(bitmapName)) {
                    cur_weather = readImage(bitmapName);


                } else {
                    String bitmapURL = "http://openweathermap.org/img/w/" + bitmapName;


                    cur_weather = getImage(new URL(bitmapURL), bitmapName);



                }
                this.publishProgress(100);

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

        @Override
        protected void onProgressUpdate(Integer... value) {
            pBar.setProgress(value[0]);

            pBar.setVisibility(View.VISIBLE);
        }// end of onProgressUpdate

        protected void onPostExecute(String s) {
            cur_temp.setText("Current temperature: " + curr_tem);
            lastUpdate.setText("Last Update: " + update);
            detail.setText("Humidty: " + humidity+"%"+"\n"+"Pressure: "+pressure+"hpa");
            weather_image.setImageBitmap(cur_weather);
            pBar.setVisibility(View.INVISIBLE);

        } // end of onPostExecute

        public boolean fileExistance(String fname) {
            File file = getActivity().getFileStreamPath(fname);


            return file.exists();

        }

        public Bitmap getImage(URL url, String fName) {
            HttpURLConnection connection = null;
            weather_icon = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    weather_icon = BitmapFactory.decodeStream(connection.getInputStream());
                    FileOutputStream outputStream = getActivity().openFileOutput(fName, Context.MODE_PRIVATE);
                    weather_icon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return weather_icon;
        }

        public Bitmap readImage(String imagefile) {
            Bitmap bm = null;
            try {
                FileInputStream fis = getActivity().openFileInput(imagefile);

                bm = BitmapFactory.decodeStream(fis);
                fis.close();

            } catch (Exception e) {
                e.printStackTrace();

            }
            return bm;

        }
    }//end of class ForecastQuery


}// end of class WeatherFragmentOne
