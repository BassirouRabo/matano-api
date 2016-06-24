package controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.LegacyWebSocket;
import play.mvc.Result;
import play.mvc.WebSocket;


public class Home extends Controller {

    @Transactional
    public Result index() {
        return ok("matano ?");
    }

    public LegacyWebSocket<String> socket() {
        return WebSocket.whenReady((in, out) -> {
            // For each event received on the socket,
            in.onMessage(System.out::println);

            // When the socket is closed.
            // in.onClose(() -> System.out.println("Disconnected"));


            // Send a single 'Hello!' message
            //   out.write("Hello!");


        });
    }

    public LegacyWebSocket<String> socket1() {
        System.out.println("socket1");
        return WebSocket.whenReady((in, out) -> {

            in.onMessage((consumer) -> {
                System.out.println(consumer.toString());
                System.out.println("onMessage");
            });

            in.onClose(new Runnable() {
                @Override
                public void run() {
                    System.out.println("onClose");
                }
            });
        });
    }

}
