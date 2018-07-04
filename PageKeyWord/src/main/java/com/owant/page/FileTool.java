package com.owant.page;

import java.io.*;

public class FileTool {

    public static String readFileContent(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        StringBuffer contentBuffer = new StringBuffer();
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    contentBuffer.append(new String(buffer, 0, len, "utf-8"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return contentBuffer.toString();
    }

    public static void saveFileContentAppend(String path, String content) {
        File file = new File(path);
        if (file.exists()) {
            try {
                OutputStreamWriter fileOut = new OutputStreamWriter(new FileOutputStream(file, true), "utf-8");
                BufferedWriter bufferedWriter = new BufferedWriter(fileOut);
                bufferedWriter.write(content);
                bufferedWriter.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
