package com.sun.abby.cst2335_finalproject;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
 * @author yun
 */
public class WeatherFragmentOne extends Fragment {
    /**
     * weatherURL String object that is url address
     */
    private static final String weatherURL =
            "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    /**
     * weather_image ImageView object is the image for the current weathe
     */
    private ImageView weather_image;
    /**
     * cur_temp TextView object is the value of current temperature
     */
    private TextView cur_temp;
    /**
     * lastUpdate TextView object which shows the last time of the weather info been updated
     */
    private TextView lastUpdate;
    /**
     * detail TextView object shows the detail info of current weather
     */
    private TextView detail;
    /**
     * pBar ProgressBar object that show the progress of downloading
     */
    private ProgressBar pBar;
    /**
     * weather_icon Bitmap object stores the weather picture
     */
    static Bitmap weather_icon = null;

    /**
     * default constructor
     */
    public WeatherFragmentOne() {
        // Required empty public constructor
    }

    /**
     o creates and returns the view hierarchy associated with the fragment
     * @param inflater The LayoutInflater object that used to inflate any views in the fragment
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here
     * @return the container of the fragment
     */
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
        pBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        new WeatherQuery().execute(weatherURL);

        return view;
    }// end of onCreateView

    /**
     * inner class extends AsyncTask which is easy use of the UI thread
     */
    class WeatherQuery extends AsyncTask<String, Integer, String> {
       // private String loc;
        private String update;
        private String humidity;
        private String pressure;
        private String curr_tem;
        private Bitmap cur_weather;
        private String iconName;

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

        /**
         * This method is used to display any form of progress
         * in the user interface while the background computation is still executing.
         * @param value execution time
         */
        @Override
        protected void onProgressUpdate(Integer... value) {
            pBar.setProgress(value[0]);

            pBar.setVisibility(View.VISIBLE);
        }// end of onProgressUpdate

        /**
         * To invoke on the UI thread after the background computation finishes
         * @param s Sting object
         */
        protected void onPostExecute(String s) {
            cur_temp.setText("Current temperature: " + curr_tem);
            lastUpdate.setText("Last Update: " + update);
            detail.setText("Humidty: " + humidity+"%"+"\n"+"Pressure: "+pressure+"hpa");
            weather_image.setImageBitmap(cur_weather);
            pBar.setVisibility(View.INVISIBLE);

        } // end of onPostExecute

        /**
         * To distinct if the file has been download before
         * @param fname String object which is the file name
         * @return true of false
         */
        public boolean fileExistance(String fname) {
            File file = getActivity().getFileStreamPath(fname);


            return file.exists();

        }

        /**
         * To get the image of current weather
         * @param url URL object that is the URL address
         * @param fName String object this is the icon name
         * @return the weather icon
         */
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

        /**
         *  To read icon file
         * @param imagefile Bitmap object icon picture
         * @return icon picture
         */
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
