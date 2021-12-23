package model.users;

import model.Event;
import model.EventLog;
import model.tasks.ListOfTasks;
import model.tasks.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// a developer
public class Developer extends User {

    // Constructs a new developer
    // MODIFIES: super
    // EFFECT: Instantiates a new developer and add its id to the developer list
    public Developer(String userID, String password, ListOfTasks toDoList, ArrayList<String> listOfDevelopers) {
        super(userID, password, toDoList, listOfDevelopers);
        super.getListOfDevelopers().add(userID);
        super.status = "developer";
    }

    // MODIFIES: ListOfTask
    // EFFECT: add task to the toDoList
    public void addTask(String name, model.tasks.Date dateOfCompletion, int urgency, int hoursRequiredForCompletion) {
        Task taskToAdd = new Task(name, dateOfCompletion, urgency, hoursRequiredForCompletion, "Not started");
        toDoList.addTask(taskToAdd);
    }

    // MODIFIES: Task
    // EFFECT: assigns self to a specified task that is open to assignment, if taskName does not exist do nothing
    public void takeOnTask(String taskName) {
        if (!(toDoList.getTaskWithName(taskName) == null)) {
            toDoList.getTaskWithName(taskName).setAssignedDeveloper(userID);
            EventLog.getInstance().logEvent(new Event("developer: " + super.getUserID()
                    + " assigned him/her-self to task : " + taskName));
        }
    }

    // MODIFIES: Task
    // EFFECT: changes the status of task that self is working on and return true,
    //         if taskName does not exist, return false
    //         if self not working on it, return false;
    public boolean changeStatus(String taskName, String newStatus) {
        if (!(toDoList.getTaskWithName(taskName) == null)) {
            if (toDoList.getTaskWithName(taskName).isAssigned(userID)) {
                toDoList.getTaskWithName(taskName).setStatus(newStatus);
                EventLog.getInstance().logEvent(new Event("developer: " + super.getUserID()
                        + " changed status of the task: " + taskName + " to " + newStatus));
                return true;
            }
            return false;
        }
        return false;
    }

    // MODIFIES: Task
    // EFFECT: turn visibility off for every task except tasks assigned to self
    public void filterOwnTask() {
        toDoList.filterTasksWithDeveloper(userID);
    }

    // converts developer to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("userID", super.userID);
        json.put("password", super.password);
        json.put("listOfDevelopers", listOfDeveloperToJson());
        json.put("status", status);
        return json;
    }

    private JSONArray listOfDeveloperToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String name : super.getListOfDevelopers()) {
            jsonArray.put(name);
        }
        return jsonArray;
    }
}
