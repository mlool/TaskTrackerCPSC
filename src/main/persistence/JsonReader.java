package persistence;

import model.exceptions.UserAlreadyExistException;
import model.tasks.Date;
import model.tasks.ListOfTasks;
import model.tasks.Task;
import model.users.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads toDoList from JSON data stored in file
// got from JsonSerializationDemo
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ListOfUsers from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfData read() throws IOException, UserAlreadyExistException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfUsers(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    // got from JsonSerializationDemo
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ListOfUsers from JSON object and returns it
    private ListOfData parseListOfUsers(JSONObject jsonObject) throws UserAlreadyExistException {
        ListOfTasks toDoList = findToDoList(jsonObject.getJSONArray("toDoList"));
        ListOfAdmins listOfAdmins = findAdminList(jsonObject, toDoList);
        ListOfDevelopers listOfDevelopers = findDeveloperList(jsonObject, toDoList);
        ListOfVisitors listOfVisitors = findVisitorList(jsonObject, toDoList);

        return new ListOfData(listOfAdmins, listOfDevelopers, listOfVisitors, toDoList);
    }

    // EFFECTS: parses toDoList from JSON object and returns it
    private ListOfTasks findToDoList(JSONArray jsonArray) {
        ListOfTasks toDoList = new ListOfTasks();
        for (Object json : jsonArray) {
            Task addTask = findTask((JSONObject) json);
            toDoList.addTask(addTask);
        }
        return toDoList;
    }

    // EFFECTS: parses task from JSON object and returns it
    private Task findTask(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Date dateOfCreation = findDate(jsonObject.getJSONObject("dateOfCreation"));
        Date dateOfCompletion = findDate(jsonObject.getJSONObject("dateOfCompletion"));
        int urgency = jsonObject.getInt("urgency");
        int hoursRequiredForCompletion = jsonObject.getInt("hoursRequiredForCompletion");
        String status = jsonObject.getString("status");
        String assignedDeveloper = jsonObject.getString("assignedDeveloper");
        boolean visibility = jsonObject.getBoolean("visibility");

        return new Task(name, dateOfCreation, dateOfCompletion, urgency, hoursRequiredForCompletion, status,
                assignedDeveloper, visibility);
    }

    // EFFECTS: parses date from JSON object and returns it
    private Date findDate(JSONObject jsonObject) {
        int year = jsonObject.getInt("year");
        int month = jsonObject.getInt("month");
        int day = jsonObject.getInt("day");
        return new Date(year, month, day);
    }

    // EFFECTS: parses ListOfAdmins from JSON object and returns it
    private ListOfAdmins findAdminList(JSONObject jsonObject, ListOfTasks toDoList) throws UserAlreadyExistException {
        JSONArray jsonArray = jsonObject.getJSONArray("adminList");
        ListOfAdmins adminList = new ListOfAdmins();
        for (Object json : jsonArray) {
            Admin addAdmin = findAdmin((JSONObject) json, toDoList);
            adminList.add(addAdmin);
        }
        return adminList;
    }

    // EFFECTS: parses admin from JSON object and returns it
    private Admin findAdmin(JSONObject jsonObject, ListOfTasks toDoList) {
        String userID = jsonObject.getString("userID");
        String password = jsonObject.getString("password");
        ArrayList<String> listOfDeveloper = findDeveloperListString(jsonObject.getJSONArray("listOfDevelopers"));
        return new Admin(userID, password, toDoList, listOfDeveloper);
    }

    // EFFECTS: parses developerNameList from JSON object and returns it
    private ArrayList<String> findDeveloperListString(JSONArray jsonArray) {
        ArrayList<String> developerList = new ArrayList<>();
        for (Object json : jsonArray) {
            developerList.add((String) json);
        }
        return developerList;
    }

    // EFFECTS: parses ListOfDevelopers from JSON object and returns it
    private ListOfDevelopers findDeveloperList(JSONObject jsonObject, ListOfTasks toDoList)
            throws UserAlreadyExistException {
        JSONArray jsonArray = jsonObject.getJSONArray("developerList");
        ListOfDevelopers developerList = new ListOfDevelopers();
        for (Object json : jsonArray) {
            Developer addDeveloper = findDeveloper((JSONObject) json, toDoList);
            developerList.add(addDeveloper);
        }
        return developerList;
    }

    // EFFECTS: parses developer from JSON object and returns it
    private Developer findDeveloper(JSONObject jsonObject, ListOfTasks toDoList) {
        String userID = jsonObject.getString("userID");
        String password = jsonObject.getString("password");
        ArrayList<String> listOfDeveloper = findDeveloperListString(jsonObject.getJSONArray("listOfDevelopers"));
        return new Developer(userID, password, toDoList, listOfDeveloper);
    }

    // EFFECTS: parses ListOfAdmins from JSON object and returns it
    private ListOfVisitors findVisitorList(JSONObject jsonObject, ListOfTasks toDoList)
            throws UserAlreadyExistException {
        JSONArray jsonArray = jsonObject.getJSONArray("visitorList");
        ListOfVisitors visitorList = new ListOfVisitors();
        for (Object json : jsonArray) {
            Visitor addVisitor = findVisitor((JSONObject) json, toDoList);
            visitorList.add(addVisitor);
        }
        return visitorList;
    }

    // EFFECTS: parses visitor from JSON object and returns it
    private Visitor findVisitor(JSONObject jsonObject, ListOfTasks toDoList) {
        String userID = jsonObject.getString("userID");
        String password = jsonObject.getString("password");
        ArrayList<String> listOfDeveloper = findDeveloperListString(jsonObject.getJSONArray("listOfDevelopers"));
        return new Visitor(userID, password, toDoList, listOfDeveloper);
    }
}

