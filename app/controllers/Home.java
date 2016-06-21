package controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;


public class Home extends Controller {

    @Transactional
    public Result index() {
        return ok("matano");
    }

}
