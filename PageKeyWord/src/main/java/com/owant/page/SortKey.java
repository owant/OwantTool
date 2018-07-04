package com.owant.page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortKey {

    private String transFilePath;
    private String navigationFilePath;
    private String saveHtmlPath;


    //翻译的数据
    private JSONObject enSource;
    private JSONObject deSource;
    private JSONObject jaSource;

    /**
     * 配对import,不配对第三方库的内容
     */
    private static final String PATTERN_OF_IMPORT_FILE = "import\\b\\s.{1,}\\bfrom\\s['\"](\\.{1,}.{1,})['\"]";
    /**
     * 配对翻译文本
     */
    private static final String PATTERN_OF_KEY_WORD = "I18n\\.t\\([\"']([^\\s:.)]{1,})[\"']\\)";
    private TreeSet<String> pagesSet = new TreeSet<>();


    private Long createTime;
    private SimpleDateFormat format = new SimpleDateFormat("hh_mm_ss");
    private String saveFile;

    private TreeSet<String> responseSet = new TreeSet<>();

    public SortKey(String transFilePath, String navigationFilePath, String saveHtmlPath) {
        this.transFilePath = transFilePath;
        this.navigationFilePath = navigationFilePath;
        this.saveHtmlPath = saveHtmlPath;

        File navigationFile = new File(navigationFilePath);
        if (navigationFile.exists()) {

            //初始化翻译文件
            initTranSource(this.transFilePath);

            //读取导航页面
            readNavigation(this.navigationFilePath);


            //保存文件
            createTime = new Date().getTime();
            this.saveFile = this.saveHtmlPath + "//" + "校对文本" + format.format(createTime) + ".html";
            File file = new File(this.saveFile);

            System.out.println("---------------create html-----------------");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileTool.saveFileContentAppend(this.saveFile, HtmlCreator.getHtmlBodyTop(navigationFile.getName().replace(".js", "")));
            FileTool.saveFileContentAppend(this.saveFile, HtmlCreator.getColsTitle());

            int i = 1;
            for (String page : pagesSet) {
                if (!page.endsWith(".png") && !page.endsWith(".json")) {
                    doPageBusiness(navigationFile.getParentFile().getPath(), page + ".js");
                    printShow(i);
                    i++;
                }
            }
            System.out.println("-------------------end--------------------");
            System.out.println("-------- open the html file in excel------");


            FileTool.saveFileContentAppend(this.saveFile, HtmlCreator.getFormatRows(responseSet.size() + 1, "http response"));
            for (String re : responseSet) {
                String en = enSource.getString(re);
                String de = deSource.getString(re);
                String js = jaSource.getString(re);
                if (en == null) {
                    en = "";
                }
                if (de == null) {
                    de = "";
                }
                if (js == null) {
                    js = "";
                }
                FileTool.saveFileContentAppend(this.saveFile, HtmlCreator.getFormatCols(re, en, de, js));
            }


            FileTool.saveFileContentAppend(this.saveFile, HtmlCreator.getHtmlBodyBottom());
        }
    }

    private void printShow(int i) {
        StringBuffer buffer = new StringBuffer("------------------------------------------");
        StringBuffer start = new StringBuffer("*");
        for (int c = 0; c < i; c++) {
            start.append("*");
        }
        i++;
        buffer = buffer.replace(buffer.length() / 2, buffer.length() / 2 + i, start.toString());
        buffer = buffer.replace(buffer.length() / 2 - i, buffer.length() / 2, start.toString());
        System.out.println(buffer);
    }

    private void initTranSource(String transPath) {
        try {
            enSource = JSON.parseObject(FileTool.readFileContent(transPath + "\\en.json"));
            deSource = JSON.parseObject(FileTool.readFileContent(transPath + "\\de.json"));
            jaSource = JSON.parseObject(FileTool.readFileContent(transPath + "\\ja.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readNavigation(String navigationFilePath) {
        try {
            String navigationContent = FileTool.readFileContent(navigationFilePath);
            pagesSet = patternKey(navigationContent, PATTERN_OF_IMPORT_FILE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void doPageBusiness(String parentPath, String relativePath) {
        File f = new File(new File(parentPath), relativePath);
        if (f.exists()) {
            try {
                //读取页面
                String pageContent = FileTool.readFileContent(f.getAbsolutePath());
                //页面的名字
                String pageName = f.getName().replace(".js", "");

                //读取当前key
                TreeSet<String> keys = patternKey(pageContent, PATTERN_OF_KEY_WORD);
                noLooperImport.clear();
                getAllImport(f.getPath());

                for (String item : noLooperImport) {
                    TreeSet<String> childKeys = patternKey(FileTool.readFileContent(item), PATTERN_OF_KEY_WORD);
                    for (String ck : childKeys) {
                        if (!isNumeric(ck)) {//标识号
                            keys.add(ck);
                        } else {
                            responseSet.add(ck);
                        }
                    }
                }

                FileTool.saveFileContentAppend(this.saveFile, HtmlCreator.getFormatRows(keys.size() + 1, pageName));
                for (String key : keys) {
                    String en = enSource.getString(key);
                    String de = deSource.getString(key);
                    String js = jaSource.getString(key);
                    if (en == null) {
                        en = "";
                    }
                    if (de == null) {
                        de = "";
                    }
                    if (js == null) {
                        js = "";
                    }
                    FileTool.saveFileContentAppend(this.saveFile, HtmlCreator.getFormatCols(key, en, de, js));
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 防止进入导包循环
     */
    public TreeSet<String> noLooperImport = new TreeSet<>();

    public void getAllImport(String pagePath) throws FileNotFoundException {
        String content = FileTool.readFileContent(pagePath);
        if (content.length() > 0) {
            TreeSet<String> tempImport = patternKey(content, PATTERN_OF_IMPORT_FILE);
            for (String item : tempImport) {
                if (!item.endsWith(".png") && !item.endsWith(".json")) {
                    File f = new File(new File(pagePath).getParentFile(), item + ".js");
                    String canonicalPath = f.getPath();
                    if (noLooperImport.add(canonicalPath)) {
                        getAllImport(canonicalPath);
                    }
                }
            }
        }
    }

    /**
     * 进行正则配对
     *
     * @param content
     * @param patternKey
     */
    public TreeSet<String> patternKey(String content, String patternKey) {
        Pattern pattern = Pattern.compile(patternKey);
        Matcher matcher = pattern.matcher(content);
        TreeSet<String> treeSet = new TreeSet<>();
        while (matcher.find()) {
            treeSet.add(matcher.group(1));
        }
        return treeSet;
    }

}
