package com.coverevi.coverevi.HTTP;

import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpHandler {
    private String URL;

    public HttpHandler(String URL) {
        this.URL = URL;
    }

    public String createGETRequest() {
        try {
            HttpURLConnection getConnection = (HttpURLConnection) new URL(this.URL).openConnection();
            getConnection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(getConnection.getInputStream(), HTTP.UTF_8));
            String webPage = "";

            while (true) {
                String data = reader.readLine();
                if (data == null) {
                    break;
                }
                webPage = new StringBuilder(String.valueOf(webPage)).append(data).toString();
            }

            return webPage;
        } catch (java.io.IOException e) {
            Log.e("createGETRequest", URL + " adresine bağlanırken hata oluştu: " + e.getMessage());
        }

        return "";
    }

    public String createPOSTRequest(List postData) {
        InputStream inputStream;

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(this.URL);
            httppost.setEntity(new UrlEncodedFormEntity(postData, "utf-8"));

            inputStream = httpclient.execute(httppost).getEntity().getContent();

            BufferedReader bufr = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();

            sb.append(bufr.readLine() + "\n");

            String str;

            while (true) {
                str = bufr.readLine();

                if (str == null) {
                    inputStream.close();
                    return sb.toString();
                }

                sb.append(new StringBuilder(String.valueOf(str)).append("\n").toString());
            }


        } catch (Exception e) {
            Log.e("createPOSTRequest", URL + " adresine bağlanırken hata oluştu: " + e.getMessage());
        }

        return "";
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}