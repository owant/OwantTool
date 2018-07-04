package com.owant.page;

import org.junit.Test;

public class HtmlCreatorTest {

    @Test
    public void getHtmlBodyTop() {
        System.out.println(HtmlCreator.getHtmlBodyTop("Airpurifier132"));
    }

    @Test
    public void getColsTitle() {
        System.out.println(HtmlCreator.getColsTitle());
    }

    @Test
    public void getColsEmpty() {
        System.out.println(HtmlCreator.getColsEmpty("Setting"));
    }

    @Test
    public void getFormatCols() {
        System.out.println(HtmlCreator.getFormatCols("ok", "ok", "Okey", "确认"));
    }

    @Test
    public void getFormatRows() {
        System.out.println(HtmlCreator.getFormatRows(12, "DeviceHome"));
        System.out.println(HtmlCreator.getFormatRows(1, "DeviceHome"));
    }

    @Test
    public void getHtmlBodyBottom() {
        System.out.println(HtmlCreator.getHtmlBodyBottom());
    }
}