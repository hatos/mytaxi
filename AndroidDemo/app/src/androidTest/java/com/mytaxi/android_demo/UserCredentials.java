package com.mytaxi.android_demo;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UserCredentials {

    public static Map<String,String> getuserdata() throws Exception {
        String url = "https://randomuser.me/api/?seed=a1f30d446f820665";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print in String
        System.out.println(response.toString());

        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println("result after Reading JSON Response");

        JSONArray results=myResponse.getJSONArray("results");
        Map<String,String> usercredentials=new HashMap<String,String>();
for(int i=0;i<results.length();i++)
{
    JSONObject resultElemets=new JSONObject(results.get(i).toString());
    JSONObject login=resultElemets.getJSONObject("login");
    System.out.println(login);
    usercredentials.put("username",login.getString("username"));
    usercredentials.put("password",login.getString("password"));

}
        //System.out.println("origin- "+myResponse.getString("origin"));

        return usercredentials;

    }
}
