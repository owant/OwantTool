package com.owant.page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.regex.Pattern;

public class ReadHttpTranslations {

    private JSONObject enSource;//英文
    private JSONObject deSource;//德语
    private JSONObject frSource;//法语
    private JSONObject itSource;//意大利
    private JSONObject esSource;//西班牙
    private JSONObject jaSource;//日语


    private static String tabs_title = "|key|en|de|fr|it|es|ja|\n";
    private static String tabs_title_line = "|:---:|:---:|:----|:----|:-----|:-----|:-----|\n";
    private static String tabs_row = "|%s|%s|%s|%s|%s|%s|%s|\n";

    private List<String> sourtList = new ArrayList<>();


    public ReadHttpTranslations() {
        initTranSource("/Users/owant/MyGit/AirSource/app/Commons/translations");

        System.out.printf(tabs_title);
        System.out.printf(tabs_title_line);

        Iterator<Map.Entry<String, Object>> iterator = enSource.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();

            Pattern pattern = Pattern.compile("[0-9]*");
            boolean matches = pattern.matcher(key).matches();
            if (matches) {
//                System.out.println(key + ":" + next.getValue());
//                System.out.printf(tabs_row, key, next.getValue(), deSource.getString(key), frSource.getString(key), itSource.getString(key), jaSource.getString(key));
                sourtList.add(
                        String.format(tabs_row, key, next.getValue(), deSource.getString(key), frSource.getString(key), itSource.getString(key), esSource.getString(key),jaSource.getString(key))
                );
            }
        }


        Collections.sort(sourtList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int key = Integer.parseInt(o1.substring(1, o1.indexOf('|', 1)));
                int key2 = Integer.parseInt(o2.substring(1, o2.indexOf('|', 1)));

//                System.out.println(key + ">><<" + key2);
                if (key < key2) {
                    return -1;
                } else if (key > key) {
                    return 1;
                }
                return 0;
            }
        });

        for(String item:sourtList){
            System.out.printf(item);
        }

    }

    private void initTranSource(String transPath) {
        try {
            enSource = JSON.parseObject(FileTool.readFileContent(transPath + "//en.json"));
            deSource = JSON.parseObject(FileTool.readFileContent(transPath + "//de.json"));
            frSource = JSON.parseObject(FileTool.readFileContent(transPath + "//fr.json"));
            itSource = JSON.parseObject(FileTool.readFileContent(transPath + "//it.json"));
            esSource = JSON.parseObject(FileTool.readFileContent(transPath + "//es.json"));
            jaSource = JSON.parseObject(FileTool.readFileContent(transPath + "//ja.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReadHttpTranslations readHttpTranslations = new ReadHttpTranslations();
    }
}
