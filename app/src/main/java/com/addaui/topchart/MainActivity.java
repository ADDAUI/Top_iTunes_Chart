package com.addaui.topchart;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String mFileContents ;
    public static String xmlUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=25/xml";
    private Button btn ;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        listView = (ListView) findViewById(R.id.listView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseData parseData = new ParseData(mFileContents);
                parseData.process();

//                ArrayAdapter<Entry> entryAdapter = new ArrayAdapter<Entry>(MainActivity.this, android.R.layout.simple_list_item_1, parseData.getEntries());
                CustomAdapter customAdapter = new CustomAdapter(MainActivity.this,R.layout.list_item,parseData.getEntries());
                listView.setAdapter(customAdapter);
            }
        });

        DownloadData downloadData = new DownloadData();
        downloadData.execute(xmlUrl);


    }

    private class DownloadData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            mFileContents = downloadXMLContent(params[0]);
            if( mFileContents == null ){
                Log.w("Dev_ADDAUI","mFileContents is NULL !!!");
            }
            return mFileContents;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Dev_ADDAUI","On Post-Execute : Result was " + s);

        }

        private String downloadXMLContent(String xmlUrl){
            StringBuilder tempBuffer = new StringBuilder();
            try{
                URL url = new URL(xmlUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                int response = httpURLConnection.getResponseCode();
                Log.i("Dev_ADDAUI","Connection Response code : " + response);
                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                //TODO try : http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string

                //Convert String Using Tim Buchalka way
                int charRead;
                char[] inputBuffer = new char[500];
                while(true){
                    charRead = isr.read(inputBuffer);

                    if(charRead <= 0 ){
                        break;
                    }
                    tempBuffer.append(String.copyValueOf(inputBuffer,0,charRead));
                }

                return tempBuffer.toString();

            } catch(IOException e){
                Log.e("Dev_ADDAUI","IOException : Error reading data " + e.getMessage());
            } catch(SecurityException e){
                Log.e("Dev_ADDAUI","Security exception triggered, Missing permission maybe ?" + e.getMessage());
            }

            return null;
        }
    }
}
