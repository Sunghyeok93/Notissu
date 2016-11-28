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

    public static File inputStreamToFile(Context context, InputStream inputStream) {
        File file =  null;
        try {
            file = new File(context.getFilesDir(),"temp");
            OutputStream outStream = new FileOutputStream(file);
            // 읽어들일 버퍼크기를 메모리에 생성
            byte[] buf = new byte[1024];
            int len = 0;
            // 끝까지 읽어들이면서 File 객체에 내용들을 쓴다
            while ((len = inputStream.read(buf)) > 0){
                outStream.write(buf, 0, len);
            }
            // Stream 객체를 모두 닫는다.
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //학교 홈페이지 같은 경우 HTTP Header로 User-Agent를 설정해줘야 제대로된 Response가 나온다.
    public static File URLConnectionToFile(Context context, URLConnection urlConnection) {
        File file = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) urlConnection;
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            file = inputStreamToFile(context, conn.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File URLToFile(Context context, URL url) {
        File file = null;
        try {
            file = URLConnectionToFile(context, url.openConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File URLToFile(Context context, String urlStr) {
        File file = null;
        try {
            URL url = new URL(urlStr);
            file = URLToFile(context, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String fileToString(File file) {
        StringBuffer fileData = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(file));
            char[] buf = new char[1024];
            int numRead=0;
            while((numRead=reader.read(buf)) != -1){
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileData.toString();
    }
}
