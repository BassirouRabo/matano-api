package controllers;

import models.Utilisateur;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    @Transactional
    public Result index() {
       Utilisateur utilisateur = new Utilisateur();

        utilisateur.setEmail("Karim");
        utilisateur.setTelephone("92332322");
        utilisateur.create(utilisateur);


        return ok(index.render("Your new application is ready."));
    }

}
