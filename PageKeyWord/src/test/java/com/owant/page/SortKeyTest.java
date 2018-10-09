package com.owant.page;

import org.junit.Test;

import java.io.FileNotFoundException;

public class SortKeyTest {
    SortKey sortKey;

    @Test
    public void main() {
        sortKey = new SortKey("/Users/owant/MyGit/AirSource/app/Commons/translations",
                "/Users/owant/MyGit/AirSource/app/ModuleEntry/air_purifier_131.js",
                "/Users/owant/MyGit/OwantTool/PageKeyWord/src/test/java/com/owant/page");
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