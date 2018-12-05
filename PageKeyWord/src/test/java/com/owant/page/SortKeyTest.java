package com.owant.page;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.TreeSet;

public class SortKeyTest {
    SortKey sortKey;

    @Before
    public void init(){
        sortKey=new SortKey();
    }

    @Test
    public void main() {
        sortKey = new SortKey("/Users/owant/MyGit/DevAirSource/app/Commons/translations",
                "/Users/owant/MyGit/DevAirSource/app/ModuleEntry/fatScale_esf37.js",
                "/Users/owant/MyGit/OwantTool/PageKeyWord/src/test/java/com/owant/page");
    }


    @Test
    public void getAllImport() {
        try {
            sortKey.noLooperImport.clear();
            sortKey.getAllImport("/Users/owant/MyGit/DevAirSource/app/Module/fatScale_esf37/Components/Settings.js");
            for (String item : sortKey.noLooperImport) {
                System.out.println(item);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void patternKey() {
        sortKey = new SortKey();
        sortKey.readNavigation("/Users/owant/MyGit/DevAirSource/app/ModuleEntry/fatScale_esf37.js");
        Iterator<String> iterator = sortKey.pagesSet.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
        }
    }


    @Test
    public void patternImportJS(){
        TreeSet<String> tree = sortKey.patternKey("//     import StyleDeviceName from '../../../Commons/StyleDeviceName';\n", sortKey.PATTERN_OF_IMPORT_FILE);
        Iterator<String> iterator = tree.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}