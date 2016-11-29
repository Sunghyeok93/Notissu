package com.notissu.Util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by forhack on 2016-11-29.
 */
public class IOUtils {

    public static InputStream URLToInputStream(URL url) {
        InputStream inputStream = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            inputStream = conn.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public static InputStream URLToInputStream(String urlStr) {
        InputStream inputStream = null;
        try {
            URL url = new URL(urlStr);
            inputStream = URLToInputStream(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;

    }
}
