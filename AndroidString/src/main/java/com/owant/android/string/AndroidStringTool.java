package com.owant.android.string;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class AndroidStringTool {


    private  LinkedHashMap<String, String> enSource;//英文
    private  LinkedHashMap<String, String> deSource;//德语
    private  LinkedHashMap<String, String> frSource;//法语
    private  LinkedHashMap<String, String> itSource;//意大利
    private  LinkedHashMap<String, String> esSource;//西班牙
    private  LinkedHashMap<String, String> jaSource;//日语

    private static String tabs_title = "|key|en|de|fr|it|es|ja|\n";
    private static String tabs_title_line = "|:---:|:---:|:----|:----|:-----|:-----|:-----|\n";
    private static String tabs_row = "|%s|%s|%s|%s|%s|%s|%s|\n";


    public AndroidStringTool() {
        enSource=readStringXML("/Users/owant/MyGit/VesyncBuild/android/app/src/main/res/values/strings.xml");
        deSource=readStringXML("/Users/owant/MyGit/VesyncBuild/android/app/src/main/res/values-de/strings.xml");
        frSource=readStringXML("/Users/owant/MyGit/VesyncBuild/android/app/src/main/res/values-fr/strings.xml");
        itSource=readStringXML("/Users/owant/MyGit/VesyncBuild/android/app/src/main/res/values-it/strings.xml");
        esSource=readStringXML("/Users/owant/MyGit/VesyncBuild/android/app/src/main/res/values-es/strings.xml");
        jaSource=readStringXML("/Users/owant/MyGit/VesyncBuild/android/app/src/main/res/values-ja/strings.xml");


        System.out.printf(tabs_title);
        System.out.printf(tabs_title_line);

        Iterator<Map.Entry<String, String>> iterator = enSource.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            System.out.printf(tabs_row, key,next.getValue(),deSource.get(key),frSource.get(key),itSource.get(key),esSource.get(key),jaSource.get(key));
        }


    }

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
    }
}
