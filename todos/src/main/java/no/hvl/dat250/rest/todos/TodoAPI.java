package no.hvl.dat250.rest.todos;

import static spark.Spark.*;

/**
 * Rest-Endpoint.
 */
public class TodoAPI {

    public static void main(String[] args) {
        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        after((req, res) -> res.type("application/json"));

        // TODO: Implement API, such that the testcases succeed.
    }
}
