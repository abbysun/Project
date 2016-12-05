//package com.sun.abby.cst2335_finalproject;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.CursorAdapter;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
///**
// * Created by Chase Thorne on 2016-11-23.
// */
//
//public class TelevisionBookmarkActivity extends AppCompatActivity {
//
//    private ArrayList<String> bookmarkArray;
//    private SQLiteDatabase db;
//    private ProgressBar progBar;
//    ListView lvBookmarks;
//    BookmarkCursorAdapter bmAdapter;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //progBar = (ProgressBar) findViewById(R.id.television_bookmark_progbar);
//
//        BookmarkQuery bmQuery = new BookmarkQuery();
//        bmQuery.execute();
//    }
//
//
//    class BookmarkQuery extends AsyncTask<String, Integer, String[]> {
//
//        @Override
//        protected String[] doInBackground(String... strings) {
//            //SQLiteOpenHelper class connecting to SQLite
//            TelevisionDatabaseHelper tvdbh = new TelevisionDatabaseHelper(TelevisionBookmarkActivity.this);
//
//            // Get access to the underlying writeable database
//            SQLiteDatabase db = tvdbh.getWritableDatabase();
//
//            // Query for items from the database and get a cursor back
//            Cursor bmCursor = db.rawQuery("SELECT  * FROM bookmarks", null);
//
//            // Find ListView to populate
//            lvBookmarks = (ListView) findViewById(R.id.television_bookmark_list);
//
//            // Setup cursor adapter using cursor from last step
//            bmAdapter = new BookmarkCursorAdapter(TelevisionBookmarkActivity.this, bmCursor);
//
//            // Attach cursor adapter to the ListView
////            lvBookmarks.setAdapter(bmAdapter);
//
//            for (int i = 0; i < 100; i++) {
//                publishProgress(i);
//
//                //this should cause the progress bar to fill slowly
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            return null;
//        }
//
//        protected void onProgressUpdate(Integer... value) {
//            progBar.setProgress(value[0]);
//        }
//
//        protected void onPostExecute(String... string) {
//            lvBookmarks.setAdapter(bmAdapter);
//        }
//    }
//
//
//    public class BookmarkCursorAdapter extends CursorAdapter {
//        public BookmarkCursorAdapter(Context context, Cursor cursor) {
//            super(context, cursor, 0);
//        }
//
//        // The newView method is used to inflate a new view and return it,
//        // you don't bind any data to the view at this point.
//        @Override
//        public View newView(Context context, Cursor cursor, ViewGroup parent) {
//            return LayoutInflater.from(context).inflate(R.layout.living_room_television_bookmark_entry, parent, false);
//        }
//
//        // The bindView method is used to bind all data to a given view
//        // such as setting the text on a TextView.
//        @Override
//        public void bindView(View view, Context context, Cursor cursor) {
//            // Find fields to populate in inflated template
//            TextView tvChannel = (TextView) view.findViewById(R.id.television_bookmark_channel);
//            TextView tvName = (TextView) view.findViewById(R.id.television_bookmark_name);
//
//            // Extract properties from cursor
//            String channel = cursor.getString(cursor.getColumnIndexOrThrow("channel"));
//            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
//
//            // Populate fields with extracted properties
//            tvChannel.setText(channel);
//            tvName.setText(name);
//        }
//    }
//
//
//    class Bookmark {
//
//        //table name
//        public static final String TABLE_NAME = "bookmarks";
//
//        //column names
//        public static final String KEY_ID = "id";
//        public static final String KEY_CHANNEL = "channel";
//        public static final String KEY_NAME = "name";
//
//        //stored attributes
//        public String id;
//        public String channel;
//        public String name;
//
//    }
//}
