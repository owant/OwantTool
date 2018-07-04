package com.owant.lint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class LintImage implements PatternPng.FindCallback {
    private String projectPath;
    private String projectImagesPath;
    private boolean delete = false;
    private ConcurrentSkipListSet<String> allImagesMap;
    private List<String> allJSFiles = new ArrayList();
    public static AtomicInteger counter = new AtomicInteger(0);
    private ExecutorService threadPool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        if (args.length == 3) {
            new LintImage(args[0], args[1], Boolean.valueOf(args[2]));
        } else {
            new LintImage(args[0], args[1], false);
        }

    }

    public LintImage(String projectPath, String projectImagesPath, boolean delete) {
        this.projectPath = projectPath;
        this.projectImagesPath = projectImagesPath;
        this.delete = delete;
        this.allImagesMap = new ConcurrentSkipListSet();
        System.out.println("\n\n");
        System.out.println("==========扫描js文件===========");
        this.getJSFiles(this.projectPath);
        counter.set(this.allJSFiles.size());
        System.out.println("\n\n");
        System.out.println("===========查找png===========");
        Iterator var4 = this.allJSFiles.iterator();

        while (var4.hasNext()) {
            String path = (String) var4.next();
            String doc = this.readJSFile(path);
            PatternPng patternPng = new PatternPng(doc, this);
            this.threadPool.execute(patternPng);
        }

        this.threadPool.shutdown();
    }

    private void getJSFiles(String path) {
        File parent = new File(path);
        if (parent.exists()) {
            File[] files = parent.listFiles();
            File[] var4 = files;
            int var5 = files.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                File f = var4[var6];
                if (f.isFile()) {
                    if (f.getName().endsWith(".js") || f.getName().endsWith(".ccs")) {
                        this.allJSFiles.add(f.getAbsolutePath());
                        System.out.println("add file:\t" + f.getAbsolutePath());
                    }
                } else {
                    this.getJSFiles(f.getPath());
                }
            }
        } else {
            System.err.println("parent path not exits!");
        }

    }

    public String readJSFile(String path) {
        StringBuffer content = new StringBuffer();
        File jsFile = new File(path);
        if (jsFile.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(jsFile);
                byte[] buffer = new byte[1024];
                boolean var6 = false;

                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    content.append(new String(buffer, 0, len));
                }

                inputStream.close();
            } catch (FileNotFoundException var8) {
                var8.printStackTrace();
            } catch (IOException var9) {
                var9.printStackTrace();
            }
        }

        return content.toString();
    }

    public void onTarget(String png) {
        System.out.println("find png:\t" + png);
        if (!this.allImagesMap.contains(png)) {
            this.allImagesMap.add(png);
        }

    }

    public void onFilter() {
        this.filter();
    }

    private void filter() {
        System.out.println("\n\n");
        System.out.println("==========需要删除的图片=========");
        File sourceImage = new File(this.projectImagesPath);
        if (sourceImage.exists()) {
            File[] fs = sourceImage.listFiles();
            File[] var3 = fs;
            int var4 = fs.length;
            long sum = 0L;

            for (int var5 = 0; var5 < var4; ++var5) {
                File item = var3[var5];
                if ((item.getName().endsWith(".png") || item.getName().endsWith(".PNG")) && !this.allImagesMap.contains(item.getName())) {
                    if (this.delete) {
                        System.out.println("delete file:\t" + item.getAbsolutePath());
                        sum += item.length();
                        item.deleteOnExit();
                    } else {
                        System.out.println("should delete:\t" + item.getAbsolutePath());
                        sum += item.length();
                    }
                }
            }

            System.out.printf("Total size: %s\n", getPrintSize(sum));
        } else {
            System.err.println("file not exits:" + this.projectImagesPath);
        }

    }

    public static String getPrintSize(long size) {
        if (size < 1024L) {
            return size + " B";
        } else {
            size /= 1024L;
            if (size < 1024L) {
                return size + " KB";
            } else {
                size /= 1024L;
                if (size < 1024L) {
                    size *= 100L;
                    return size / 100L + "." + size % 100L + " MB";
                } else {
                    size = size * 100L / 1024L;
                    return size / 100L + "." + size % 100L + " GB";
                }
            }
        }
    }
}
