package com.owant.page;

public class MainClient {

    public static void main(String[] args) {
        if (args.length == 3) {
            new SortKey(args[0], args[1], args[2]);
        } else {
            System.out.println("Please input args[翻译文件夹,导航模块,输出文件路径]!");
        }
    }

}
