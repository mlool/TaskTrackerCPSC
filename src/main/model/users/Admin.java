package model.users;

import model.Event;
import model.EventLog;
import model.tasks.Date;
import model.tasks.ListOfTasks;
import model.tasks.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Admin extends User {

    // Constructs a new admin
    public Admin(String userID, String password, ListOfTasks toDoList, ArrayList<String> listOfDevelopers) {
        super(userID, password, toDoList, listOfDevelopers);
        super.status = "admin";
    }

    // MODIFIES: ListOfTasks
    // EFFECT: add task to the toDoList, return true if successfully added, else false
    public boolean addTask(String name, Date dateOfCompletion, int urgency, int hoursRequiredForCompletion) {
        Task taskToAdd = new Task(name, dateOfCompletion, urgency, hoursRequiredForCompletion, "Not started");
        return toDoList.addTask(taskToAdd);
    }

    // MODIFIES: Task
    // EFFECT: modifies the given task, if task does not exist, do nothing
    @SuppressWarnings("methodlength")
    public void modifyTask(String taskToBeModified, String partOfTaskToModify, String modifiedValue) {
        Task modifiedTask = toDoList.getTaskWithName(taskToBeModified);
        switch (partOfTaskToModify) {
            case "name":
                changeNameOfTask(modifiedTask, modifiedValue);
                EventLog.getInstance().logEvent(new Event("admin: " + super.getUserID() + " renamed task: "
                        + taskToBeModified + " to " + modifiedValue));
                break;
            case "status":
                changeStatus(modifiedTask, modifiedValue);
                EventLog.getInstance().logEvent(new Event("admin: " + super.getUserID()
                        + " changed status of task: " + taskToBeModified + " to " + modifiedValue));
                break;
            case "developer":
                changeDeveloper(modifiedTask, modifiedValue);
                EventLog.getInstance().logEvent(new Event("admin: " + super.getUserID()
                        + " changed developer of task: " + taskToBeModified + " to " + modifiedValue));
                break;
            case "delete":
                deleteTask(taskToBeModified);
                EventLog.getInstance().logEvent(new Event("admin: " + super.getUserID()
                        + " deleted task: " + taskToBeModified));
                break;
            default:
                break;
        }
    }

    // MODIFIES: Task
    // EFFECT: modifies the given task, if task does not exist, do nothing
    public void modifyTask(String taskToBeModified, String partOfTaskToModify, int modifiedValue) {
        Task modifiedTask = toDoList.getTaskWithName(taskToBeModified);
        switch (partOfTaskToModify) {
            case "urgency":
                // urgency between [0 - 5]
                changeUrgency(modifiedTask, modifiedValue);
                EventLog.getInstance().logEvent(new Event("admin: " + super.getUserID()
                        + " changed urgency of task: " + taskToBeModified + " to " + modifiedValue));
                break;
            case "hours required for completion":
                changeHoursRequiredForCompletion(modifiedTask, modifiedValue);
                EventLog.getInstance().logEvent(new Event("admin: " + super.getUserID()
                        + " changed hours required for task: " + taskToBeModified + " to " + modifiedValue));
                break;
            default:
                break;
        }
    }

    // MODIFIES: Task
    // EFFECT: modifies the given task, if task does not exist, do nothing
    public void modifyTask(String taskToBeModified, String partOfTaskToModify, int year, int month, int day) {
        Task modifiedTask = toDoList.getTaskWithName(taskToBeModified);
        if ("date of completion".equals(partOfTaskToModify)) {
            changeDateOfCompletion(modifiedTask, year, month, day);
            EventLog.getInstance().logEvent(new Event("admin: " + super.getUserID()
                    + " changed date of task: " + taskToBeModified + " to " + year + "/" + month + "/" + day));
        }
    }

    // MODIFIES: Task
    // EFFECT: change name of task given from user input
    private void changeNameOfTask(Task modifiedTask, String modifiedValue) {
        modifiedTask.setName(modifiedValue);
    }

    // MODIFIES: Task
    // EFFECT: change date of completion of task given from user input
    private void changeDateOfCompletion(Task modifiedTask, int year, int month, int day) {
        modifiedTask.setDateOfCompletion(year, month, day);
    }

    // MODIFIES: Task
    // EFFECT: change urgency of task given from user input
    private void changeUrgency(Task modifiedTask, int modifiedValue) {
        modifiedTask.setUrgency(modifiedValue);
    }

    // MODIFIES: Task
    // EFFECT: change hours required for completion of task given from user input
    private void changeHoursRequiredForCompletion(Task modifiedTask, int modifiedValue) {
        modifiedTask.setHoursRequiredForCompletion(modifiedValue);
    }

    // MODIFIES: Task
    // EFFECT: change status of task given from user input
    private void changeStatus(Task modifiedTask, String modifiedValue) {
        modifiedTask.setStatus(modifiedValue);
    }

    // MODIFIES: Task
    // EFFECT: change assigned developer given from user input
    private void changeDeveloper(Task modifiedTask, String modifiedValue) {
        assignDeveloper(modifiedValue, modifiedTask);
    }

    // MODIFIES: Task
    // EFFECT: delete a task from the toDoList, do nothing if task does not exist
    private void deleteTask(String taskName) {
        toDoList.remove(toDoList.getTaskWithName(taskName));
    }

    // MODIFIES: Task
    // EFFECT: assigns a developer from listOfDevelopers to a task, if developer does not exist do nothing
    private void assignDeveloper(String developerName, Task modifiedTask) {
        for (String developer : getListOfDevelopers()) {
            if (developer.equals(developerName)) {
                modifiedTask.setAssignedDeveloper(developerName);
            }
        }
    }

    // converts admin to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("userID", super.userID);
        json.put("password", super.password);
        json.put("listOfDevelopers", listOfDeveloperToJson());
        json.put("toDoList", super.toDoList.toJson());
        return json;
    }

    // converts list of developers to json
    private JSONArray listOfDeveloperToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String name : super.getListOfDevelopers()) {
            jsonArray.put(name);
        }
        return jsonArray;
    }
}
