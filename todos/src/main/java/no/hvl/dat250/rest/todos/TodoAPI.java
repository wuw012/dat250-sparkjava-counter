package no.hvl.dat250.rest.todos;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static spark.Spark.*;

/**
 * Rest-Endpoint.
 */
public class TodoAPI {

    static Todo todo = null;

    public static boolean isNumeric(String strNum) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8000);
        }

        after((req, res) -> res.type("application/json"));

        Set<Todo> todos = new HashSet<>();

        // Read (GET) TODO-items
        get("/todos", (request, response) -> {
            Gson gson = new Gson();
            return gson.toJson(todos);
        });

        // Create (POST) TODO-items
        post("/todos", (request, response) -> {
            Todo todo;
            Gson gson = new Gson();
            todo = gson.fromJson(request.body(), Todo.class);

            todos.add(todo);
            return gson.toJson(todo);
        });


        // Update (PUT) TODO-items
        put("/todos/:id", (request, response) -> {
            String inputID = request.params(":id");
            if (!TodoAPI.isNumeric(inputID)) {
                return String.format("The id \"%s\" is not a number!", inputID);
            }

            Gson gson = new Gson();
            Todo inputTodo = gson.fromJson(request.body(), Todo.class);
            for (Todo todo : todos) {
                if (inputID.equals(todo.getId().toString())) {
                    todo.setSummary(inputTodo.getSummary());
                    todo.setDescription(inputTodo.getDescription());
                    return "Updated todo";
                }
            }
                return String.format("Todo with the id \"%s\" not found!", inputID);
            }
        );

        // Delete (DELETE) TODO-items
        delete("/todos/:id", (request, response) -> {
            String inputID = request.params(":id");
            if (!TodoAPI.isNumeric(inputID)) {
                return String.format("The id \"%s\" is not a number!", inputID);
            }

            Gson gson = new Gson();
            for (Todo todo : todos) {
                if (inputID.equals(todo.getId().toString())) {
                    todos.remove(todo);
                    return gson.toJson(todo);
                }
            }
            return String.format("Todo with the id \"%s\" not found!", inputID);
        });

    }
}