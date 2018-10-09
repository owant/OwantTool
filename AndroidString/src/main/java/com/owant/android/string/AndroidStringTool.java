package com.owant.android.string;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class AndroidStringTool {


    LinkedHashMap<String, String> enSources;


    private LinkedHashMap<String, String> readStringXML(String filePath) {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        try {
            InputStream inputStream = new FileInputStream(new File(filePath));
            XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
            xmlPullParser.setInput(inputStream, "utf-8");

            int eventType = xmlPullParser.getEventType();
            String currentTag = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = xmlPullParser.getName();
                        if ("string".equals(tagName)) {
                            int attributeCount = xmlPullParser.getAttributeCount();
                            if (attributeCount == 1) {
                                String attributeName = xmlPullParser.getAttributeName(0);
                                if ("name".equals(attributeName)) {
                                    currentTag = xmlPullParser.getAttributeValue(0);
                                }
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        String text = xmlPullParser.getText();
                        if (result.get(text) == null && currentTag.length() > 0) {
                            result.put(currentTag, text);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        currentTag = "";
                        break;
                }

                eventType = xmlPullParser.next();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private String readRFile(String filePath) {
        StringBuffer resultBuffer = new StringBuffer();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(buffer)) != -1) {
                resultBuffer.append(new String(buffer, 0, len, "UTF-8"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultBuffer.toString();
    }

    public static void main(String[] args) {
        AndroidStringTool androidStringTool = new AndroidStringTool();
        LinkedHashMap<String, String> result = androidStringTool.readStringXML("/Users/owant/Desktop/strings.xml");
        Iterator<Map.Entry<String, String>> iterator = result.entrySet().iterator();

//        String rContext = androidStringTool.readRFile("/Users/owant/Desktop/R.java");
//        System.out.println(rContext);


        LinkedHashMap<String, String> jaSources = androidStringTool.readStringXML("/Users/owant/MyGit/AirSource/android/app/src/main/res/values-de/strings.xml");


        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
//            System.out.printf("%s:%s\n", next.getKey(), next.getValue());
//            if (!rContext.contains(next.getKey())) {
//                System.out.println(next.getKey());
//            }

            jaSources.remove(next.getKey());
        }

        Iterator<Map.Entry<String, String>> iterator1 = jaSources.entrySet().iterator();
        while (iterator1.hasNext()) {
            Map.Entry<String, String> next = iterator1.next();
            System.out.printf("%s:%s\n", next.getKey(), next.getValue());
        }


    }
}
