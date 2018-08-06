package com.example.test.programmingmama.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class OneSignalUtils {

    public static void netWorkCall(Context cn, String message, String id) throws JSONException {

        PrefManager pref = new PrefManager(cn);
        String appid = pref.geAppID();
        String token = pref.getToken();

        Log.d("UserId", "appid : " + appid + "token : " + token);

        String jsonString = new JSONObject()
                .put("app_id", appid)
                .put("filters", new JSONArray().put(0,
                        new JSONObject()
                                .put("field", "tag")
                                .put("key", "id")
                                .put("relation", "=")
                                .put("value", id)
                ))
                .put("contents", new JSONObject().put("en", message)).toString();

//        String jsonString = new JSONObject()
//                .put("app_id", appid)
//                .put("filters", new JSONArray().put(0,
//                        new JSONObject()
//                        .put("field", "email")
//                        .put("relation","is")
//                        .put("value",email)
//                ))
//                .put("contents", new JSONObject()
//                        .put("en", "message")).toString();
//
//        Log.d("OneUtils", "json 1: "+jsonString);
//
//        String strJsonBody = "{"
//                + "\"app_id\": " + "\"" + appid + "\","
//                + "\"filters\": [{\"field\": \"email\",  \"relation\": \"is\", \"value\": " + "\"" + email + "\"}],"
//                + "\"contents\": {\"en\": " + "\"" + message + "\"}"
//                + "}";

        Log.d("OneUtils", "json 1: " + jsonString);


        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String jsonResponse;

                    URL url = new URL("https://onesignal.com/api/v1/notifications");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setUseCaches(false);
                    con.setDoOutput(true);
                    con.setDoInput(true);

                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    con.setRequestProperty("Authorization", "Basic "+token+"");
                    con.setRequestMethod("POST");


                    System.out.println("strJsonBody:\n" + jsonString);

                    byte[] sendBytes = jsonString.getBytes("UTF-8");
                    con.setFixedLengthStreamingMode(sendBytes.length);

                    OutputStream outputStream = con.getOutputStream();
                    outputStream.write(sendBytes);

                    int httpResponse = con.getResponseCode();
                    System.out.println("httpResponse: " + httpResponse);
                    Log.d("OneUtils", "httpResponse: "+httpResponse);

                    if (httpResponse >= HttpURLConnection.HTTP_OK
                            && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                        Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                        scanner.close();
                    } else {
                        Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                        scanner.close();
                    }
                    System.out.println("jsonResponse:\n" + jsonResponse);
                    Log.d("OneUtils", "jsonResponse: "+jsonResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("OneUtils", "error: "+e.getMessage());
                }
            }
        }).start();



      /*  new Thread(() -> {
            try {
                String jsonResponse;

                URL url = new URL("https://onesignal.com/api/v1/notifications");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setUseCaches(false);
                con.setDoOutput(true);
                con.setDoInput(true);

                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setRequestProperty("Authorization", "Basic " + token + "");
                con.setRequestMethod("POST");


                System.out.println("strJsonBody:\n" + strJsonBody);

                byte[] sendBytes = jsonString.getBytes("UTF-8");
                con.setFixedLengthStreamingMode(sendBytes.length);

                OutputStream outputStream = con.getOutputStream();
                outputStream.write(sendBytes);

                int httpResponse = con.getResponseCode();
                System.out.println("httpResponse: " + httpResponse);
                Log.d("OneUtils", "httpResponse: "+httpResponse);

                if (httpResponse >= HttpURLConnection.HTTP_OK
                        && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                    Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                } else {
                    Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                    scanner.close();
                }
                Toast.makeText(cn, jsonResponse, Toast.LENGTH_SHORT).show();
                System.out.println("jsonResponse:\n" + jsonResponse);
                Log.d("OneUtils", "jsonResponse: "+jsonResponse);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("OneUtils", "error: "+e.getMessage());
            }
        }).start();*/


    }


}
