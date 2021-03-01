package ca.cmpt213.server.dao;

import ca.cmpt213.server.model.Tokimon;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.io.FileReader;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * contains the functions that change the arraylist of
 * tokimon upon recieving information from the client.
 */

@Repository("fakeDao")
public class TokimonAccess implements TokimonDao {

    private static ArrayList<Tokimon> tokimons = new ArrayList<>();
    private int counter = 0;

    @Override
    public int addTokimon(Tokimon tokimon) {
        tokimons.add(new Tokimon(counter++, tokimon.getName(), tokimon.getWeight(), tokimon.getHeight(), tokimon.getAbility(), tokimon.getStrength(), tokimon.getColor()));
        updateJson();
        return 1;
    }

    @Override
    public ArrayList<Tokimon> returnAllTokimon() {
        if(counter == 0) {
            readJson();
        }
        return tokimons;
    }

    @Override
    public int deleteTokimon(int id) {
        for(int i = 0; i < tokimons.size(); i++) {
            if(tokimons.get(i).getId() == id) {
                tokimons.remove(i);
                updateJson();
                return 1;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public int changeTokimon(int id, Tokimon tokimon) {
        Tokimon temp = getTokimon(id);

        if (tokimon.getName() != "") {
            temp.setName(tokimon.getName());
        }

        temp.setWeight(Math.max(tokimon.getWeight(), 1));
        temp.setHeight(Math.max(tokimon.getHeight(), 1));

        if (tokimon.getAbility() != "") {
            temp.setAbility(tokimon.getAbility());
        }

        temp.setStrength(Math.max(tokimon.getStrength(), 0));
        if( temp.getStrength() > 100) {
                temp.setStrength(100);
        }

        if (tokimon.getColor() != "") {
            temp.setColor(tokimon.getColor());
        }
        updateJson();
        return 1;
    }

    @Override
    public Tokimon getTokimon(int id) {
        for (Tokimon tokimon : tokimons) {
            if (tokimon.getId() == id) {
                return tokimon;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public void updateJson() {
        JSONArray allTokimons = new JSONArray();
        for (Tokimon tokimon : tokimons) {
            JSONObject temp = new JSONObject();
            temp.put("id", tokimon.getId());
            temp.put("name", tokimon.getName());
            temp.put("weight", tokimon.getWeight());
            temp.put("height", tokimon.getHeight());
            temp.put("ability", tokimon.getAbility());
            temp.put("strength", tokimon.getStrength());
            temp.put("color", tokimon.getColor());
            allTokimons.add(temp);
        }
        try (FileWriter file = new FileWriter("data/tokimon.json")) {

            file.write(allTokimons.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readJson() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("data/tokimon.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray tokimonList = (JSONArray) obj;
            if(tokimonList.size() == 0){
                return;
            }
            for(Object currentTok : tokimonList) {
                JSONObject current = (JSONObject)currentTok;
                Tokimon temp = new Tokimon((int)Math.toIntExact((long)(current.get("id"))), (String)current.get("name"), (int)Math.toIntExact((long)(current.get("weight"))), (int)Math.toIntExact((long)(current.get("height"))), (String)current.get("ability"), (int)Math.toIntExact((long)(current.get("strength"))), (String)current.get("color"));
                tokimons.add(temp);
                if((int)Math.toIntExact((long)(current.get("id"))) >= counter) {
                    counter = (int)Math.toIntExact((long)(current.get("id"))) + 1;
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

}
