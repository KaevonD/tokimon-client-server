package ca.cmpt213.server.service;

import ca.cmpt213.server.dao.TokimonDao;
import ca.cmpt213.server.model.Tokimon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * connects the controller and the access classes
 */

@Service
public class TokimonService {

    private final TokimonDao tokimonDao;

    @Autowired
    public TokimonService(@Qualifier("fakeDao") TokimonDao tokimonDao) {
        this.tokimonDao = tokimonDao;
    }

    public int addTokimon(Tokimon tokimon) {
        return tokimonDao.addTokimon(tokimon);
    }

    public ArrayList<Tokimon> returnAllTokimon() {
        return tokimonDao.returnAllTokimon();
    }

    public int deleteTokimon(int id) {
        return tokimonDao.deleteTokimon(id);
    }

    public int changeTokimon(int id, Tokimon tokimon) {
        return tokimonDao.changeTokimon(id, tokimon);
    }

    public Tokimon getTokimon(int id) {
        return tokimonDao.getTokimon(id);
    }

}