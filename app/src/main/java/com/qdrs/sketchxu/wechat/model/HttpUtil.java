package com.qdrs.sketchxu.wechat.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static int get(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            InputStreamReader bis = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(bis);
            String response = "";
            String str;
            while ((str = reader.readLine()).length() > 0){
                response += str + "\n";
            }
            Log.d("HttpUtil", "response:" + response);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int put(String url, String userInfo) {
        String response = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type",  "application/json");
            conn.connect();

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(userInfo);
            writer.flush();

            InputStreamReader bis = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(bis);

            String str;
            while ((str = reader.readLine()).length() > 0){
                response += str;
            }
            Log.d("HttpUtil", "response:" + response);

            conn.disconnect();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Integer.parseInt(response);
    }

    public static int post(String url, String userInfo) {
        String response = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type",  "application/json");
            conn.connect();

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(userInfo);
            writer.flush();

            InputStreamReader bis = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(bis);
            String str;
            while ((str = reader.readLine()).length() > 0){
                response += str;
            }
            Log.d("HttpUtil", "response:" + response);

            conn.disconnect();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Integer.parseInt(response);
    }
}
