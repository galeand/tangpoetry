package com.sse.tangpoetry.util;

import sun.awt.CharsetString;

import java.io.*;

/**
 * @name: FileUtils
 * @author: yf.xiang
 * @create: 2020-01-22 18:39
 * @description:
 */
public class FileUtils {

    public static String readJsonFile(String path){

        try {
            File jsonFile = new File(path);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile));
            int ch = 0;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1){
                sb.append((char)ch);
            }
            reader.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
