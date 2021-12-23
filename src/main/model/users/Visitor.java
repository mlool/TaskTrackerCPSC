package model.users;

import model.Event;
import model.EventLog;
import model.tasks.ListOfTasks;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// a visitor class
public class Visitor extends User {

    // Constructs a visitor
    public Visitor(String userID, String password, ListOfTasks toDoList, ArrayList<String> listOfDevelopers) {
        super(userID, password, toDoList, listOfDevelopers);
        super.status = "visitor";
    }

    // EFFECT: places visitor to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("userID", super.userID);
        json.put("password", super.password);
        json.put("listOfDevelopers", listOfDeveloperToJson());
        json.put("status", status);
        return json;
    }

    // EFFECT: places listOfDeveloper to json
    private JSONArray listOfDeveloperToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String name : super.getListOfDevelopers()) {
            jsonArray.put(name);
        }
        return jsonArray;
    }
}
