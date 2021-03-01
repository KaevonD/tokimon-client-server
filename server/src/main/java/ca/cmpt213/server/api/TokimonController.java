package ca.cmpt213.server.api;

import ca.cmpt213.server.model.Tokimon;
import ca.cmpt213.server.service.TokimonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * recieves information from the client and
 * sends it to the respective functions
 */

@RequestMapping("api/tokimon")
@RestController
public class TokimonController {

    private final TokimonService tokimonService;

    @Autowired
    public TokimonController(TokimonService tokimonService) {
        this.tokimonService = tokimonService;
    }

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTokimon(@RequestBody Tokimon tokimon){
        tokimonService.addTokimon(tokimon);
    }

    @GetMapping("all")
    public ArrayList<Tokimon> returnAllTokimon() {
        return tokimonService.returnAllTokimon();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTokimon(@PathVariable("id") int id){
        tokimonService.deleteTokimon(id);
    }

    @PostMapping("change/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeTokimon(@PathVariable("id") int id, @RequestBody Tokimon tokimon) {
        tokimonService.changeTokimon(id, tokimon);
    }

    @GetMapping("{id}")
    public Tokimon getTokimon(@PathVariable("id") int id) {
        return tokimonService.getTokimon(id);
    }

}
