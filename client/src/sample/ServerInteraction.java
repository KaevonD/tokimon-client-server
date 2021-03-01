package sample;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * communicates information to the server.
 * contains functions that return all of
 * the tokimon, return a specific tokimon,
 * add a new tokimon, change the stats of
 * a tokimon, and delete a tokimon
 */

public class ServerInteraction {
    public static JSONArray tokimonList;
    public static JSONObject currentToki;

    public static void returnAllTokimon() throws IOException {
        URL url = new URL("http://localhost:8080/api/tokimon/"+"all");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String data = reader.readLine();
        reader.close();

        JSONParser jsonParser = new JSONParser();

        try {
            tokimonList = (JSONArray)jsonParser.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        connection.disconnect();
    }


    public static JSONObject getOneTokimon(int id) throws IOException {
        for(Object toki : tokimonList){
            JSONObject curr = (JSONObject)toki;
            if(Math.toIntExact((long)curr.get("id"))==id){
                currentToki = curr;
                return currentToki;
            }
        }
        return null;
    }

    public static void addToki(String name, int weight, int height, int strength, String ability, String color) throws IOException{
        try {
            URL url = new URL("http://localhost:8080/api/tokimon/" + "add");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

            writer.write("{\"name\":\""+name+"\",\"weight\":"+weight+",\"height\":"+height+",\"strength\":"+strength+",\"ability\":\""+ability+"\",\"color\":\""+color+"\"}");
            writer.flush();
            writer.close();
            connection.getResponseCode();
            connection.disconnect();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void changeStats( String name, String weight, String height, String strength, String ability, String color)throws IOException{
        try{
            if(currentToki == null) {
                return;
            }
            URL url = new URL("http://localhost:8080/api/tokimon/"+"change/"+currentToki.get("id"));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());


            if(weight.equals("")){
                weight="0";
            }
            if(height.equals("")){
                height="0";
            }
            if(strength.equals("")){
                strength="0";
            }
            System.out.println(Integer.parseInt(weight));
            writer.write("{\"name\":\""+name+"\",\"weight\":"+Integer.parseInt(weight)+",\"height\":"+Integer.parseInt(height)+",\"strength\":"+Integer.parseInt(strength)+",\"ability\":\""+ability+"\",\"color\":\""+color+"\"}");
            writer.flush();
            writer.close();
            connection.getResponseCode();
            connection.disconnect();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void deleteToki() throws IOException{
        try{
            if(currentToki == null) {
                return;
            }
            URL url = new URL("http://localhost:8080/api/tokimon/" + currentToki.get("id"));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("DELETE");
            connection.getResponseCode();
            connection.disconnect();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
