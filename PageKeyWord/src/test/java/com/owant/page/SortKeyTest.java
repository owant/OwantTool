package com.owant.page;

import org.junit.Test;

import java.io.FileNotFoundException;

public class SortKeyTest {
    SortKey sortKey;

    @Test
    public void main() {
//        sortKey = new SortKey("E:\\Saft\\app\\Commons\\translations",
//                "E:\\Saft\\app\\ModuleEntry\\wifioutlet_7a.js",
//        "F:\\open_source\\PageKeyWord\\src\\main\\java\\com\\owant\\page");


        sortKey = new SortKey("E:\\7A2\\app\\Commons\\translations",
                "E:\\7A2\\app\\ModuleEntry\\wifioutlet_15a.js",
                "C:\\Users\\Dell\\Desktop\\OwantTool\\PageKeyWord\\src\\main\\java\\com\\owant\\page");

//        sortKey = new SortKey("E:\\7A2\\app\\Commons\\translations",
//                "E:\\7A2\\app\\ModuleEntry\\NetworkConfig.js",
//                "C:\\Users\\Dell\\Desktop\\OwantTool\\PageKeyWord\\src\\main\\java\\com\\owant\\page");
    }


    @Test
    public void getAllImport() {
        main();

        try {
            sortKey.noLooperImport.clear();
            sortKey.getAllImport("E:\\Saft\\app\\Module\\air_purifier_132\\Components\\DeviceHome.js");
            for (String item : sortKey.noLooperImport) {
                System.out.println(item);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void patternKey() {
    }
}