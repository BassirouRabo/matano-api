package controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Home extends Controller {

    @Transactional
    public Result index() {
        Date date = new Date();
        String jour = new SimpleDateFormat("dd/MM/yyyy").format(date);
        return ok("matano " + jour);
    }

}
