package com.owant.zenplin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2018/3/14.
 */

public class ZeplinImageSort {

    List<File> allImages;
    String sortPath;

    public ZeplinImageSort() {
    }

    public ZeplinImageSort(String sourcePath) {
        allImages = readImageFiles(sourcePath);
        sortPath = createSortDir(sourcePath);
        sortImage(allImages, sortPath);
    }

    public List<File> readImageFiles(String filePath) {
        List<File> fileList = new ArrayList<>();
        File parentFile = new File(filePath);
        if (parentFile.exists()) {
            File[] files = parentFile.listFiles();
            for (File f : files) {
                if (f.getName().endsWith(".png") && f.getName().contains("@3x")) {
                    fileList.add(f);
                }
            }
        } else {
            System.err.println("filePath not exit!");
        }
        return fileList;
    }

    public String createSortDir(String filePath) {
        File sortFile = new File(filePath + "/Sort/");
        if (!sortFile.exists()) {
            sortFile.mkdirs();
        }
        return sortFile.getPath();
    }

    public void sortImage(List<File> source, String sortPath) {
        for (File file : source) {
            String name = file.getName().replace("@3x", "");
            File saveFile = new File(sortPath + "/" + name);
            copyFile(file, saveFile);
        }
    }

    public void copyFile(File source, File save) {
        try {
            FileInputStream fileInputStream = new FileInputStream(source);
            if (!save.exists()) {
                save.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(save);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            fileInputStream.close();

            System.out.printf("save %s \t\t\t from %s\n", save.getPath(), source.getName());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        ZeplinImageSort sort = new ZeplinImageSort();
//        sort.createSortDir("C:\\Users\\Dell\\Desktop\\OwantProject\\JavaStudy\\src\\main\\java\\com\\owant\\tool");
//        List<File> files = sort.readImageFiles("C:\\Users\\Dell\\Desktop\\photo");
//        for (File f : files) {
//            System.out.printf("file:%s\n", f.getName());
//        }
//
//        File source=new File("C:\\Users\\Dell\\Desktop\\OwantProject\\JavaStudy\\src\\main\\java\\com\\owant\\tool\\ZeplinImageSort.java");
//        File save=new File("C:\\Users\\Dell\\Desktop\\OwantProject\\JavaStudy\\src\\main\\java\\com\\owant\\tool\\Sort\\Test.java");
//        sort.copyFile(source,save);
//
//
        new ZeplinImageSort(args[0]);

    }

}
