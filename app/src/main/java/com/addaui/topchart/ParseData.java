package com.addaui.topchart;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by ADDAUI on 5/8/2016.
 */
public class ParseData {

    private String xmlData;
    private ArrayList<Entry> entries;

    public ParseData(String xmlData) {
        this.xmlData = xmlData;
        entries = new ArrayList<>();
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public boolean process(){
        boolean status = true;
        Entry currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                switch(eventType){
                    case XmlPullParser.START_TAG :
                        Log.v("Dev_ADDAUI.Tags","Starting tag " + tagName);
                        if(tagName.equalsIgnoreCase("entry")) {
                            inEntry = true;
                            currentRecord = new Entry();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        Log.v("Dev_ADDAUI.Tags","Text from tag "+tagName+" is ( "+textValue+ " )");
                        break;

                    case XmlPullParser.END_TAG:
                        Log.v("Dev_ADDAUI.Tags","End tag " + tagName);
                       if(inEntry){
                            if(tagName.equalsIgnoreCase("entry")){
                                Log.v("Dev_ADDAUI.Entries",currentRecord.toString());
                                entries.add(currentRecord);
                                inEntry = false;
                            }else if(tagName.equalsIgnoreCase("name")){
                                currentRecord.setName(textValue);
                            }else if(tagName.equalsIgnoreCase("artist")){
                                currentRecord.setArtist(textValue);
                            }else if(tagName.equalsIgnoreCase("releasedate")){
                                currentRecord.setReleaseDate(textValue.substring(0,10));
                            }else if(tagName.equalsIgnoreCase("image")){
                                currentRecord.setImgUrl(textValue);
                            }else if(tagName.equalsIgnoreCase("category")){
                                currentRecord.setCategory(xpp.getAttributeValue(null, "term"));
                            }
                        }


                        break;

                    default://No'in
                }
                eventType = xpp.next();
            }
        }catch (Exception e){
            status = false;
            Log.e("Dev_ADDAUI","Process Catch Block : " + e.getMessage());
        }
        return true;
    }

}
