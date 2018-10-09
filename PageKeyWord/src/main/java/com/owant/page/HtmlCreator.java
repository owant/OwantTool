package com.owant.page;

public class HtmlCreator {

    private static final String HTML_BODY_TOP = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <title>%s翻译文档校验</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table border=\"1 \">\n";

    private static final String HTML_BODY_BOTTOM = "</table>\n" +
            "</body>\n" +
            "</html>";

    private static final String COLS_TITLE = "<tr>\n" +
            "   <td>页面名字</td>\n" +
            "   <td>键</td>\n" +
            "   <td>英文</td>\n" +
            "   <td>德文</td>\n" +
            "   <td>日文</td>\n" +
            "   <td>西班牙</td>\n" +
            "   <td>法语</td>\n" +
            "   <td>意大利</td>\n" +
            "</tr>\n";

    private static final String COLS_EMPTY = "<tr>\n" +
            "   <td>%s</td>\n" +
            "   <td> </td>\n" +
            "   <td> </td>\n" +
            "   <td> </td>\n" +
            "   <td> </td>\n" +
            "   <td> </td>\n" +
            "   <td> </td>\n" +
            "   <td> </td>\n" +
            "</tr>\n";

    private static final String format_cols = "<tr>\n" +
            "   <td>%s</td>\n" +
            "   <td>%s</td>\n" +
            "   <td>%s</td>\n" +
            "   <td>%s</td>\n" +
            "   <td>%s</td>\n" +
            "   <td>%s</td>\n" +
            "   <td>%s</td>\n" +
            "</tr>\n";


    //表格第一行代码
    private static final String format_rows = "<tr>\n" +
            "   <td rowspan=\"%d\">%s</td>  \n" +
            "</tr>\n";


    public static String getHtmlBodyTop(String moduleName) {
        return String.format(HTML_BODY_TOP, moduleName);
    }

    public static String getColsTitle() {
        return COLS_TITLE;
    }

    public static String getColsEmpty(String pageName) {
        return String.format(COLS_EMPTY, pageName);
    }

    public static String getFormatRows(int count, String pageName) {
        if (count == 1) {
            return String.format(getColsEmpty(pageName));
        }
        return String.format(format_rows, count, pageName);
    }

    public static String getHtmlBodyBottom() {
        return HTML_BODY_BOTTOM;
    }


    public static String getFormatCols(String key, String en, String de, String ja, String es, String fr, String it) {
        return String.format(format_cols, key, en, de, ja,es,fr,it);
    }
}
