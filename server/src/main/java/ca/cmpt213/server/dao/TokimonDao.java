package ca.cmpt213.server.dao;

import ca.cmpt213.server.model.Tokimon;

import java.util.ArrayList;

/**
 * interface for functions that change
 * the arraylist of tokimon and the
 * json file that stores them
 */
public interface TokimonDao {

    int addTokimon(Tokimon tokimon);

    ArrayList<Tokimon> returnAllTokimon();

    int deleteTokimon(int id);

    int changeTokimon(int id, Tokimon tokimon);

    Tokimon getTokimon(int id);
}
