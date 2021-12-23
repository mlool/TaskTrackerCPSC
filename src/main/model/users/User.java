package model.users;

import model.tasks.ListOfTasks;
import persistence.Writable;

import java.util.ArrayList;

// an abstraction of user class
public abstract class User implements Writable {
    protected String userID;
    protected String password;
    protected String status;

    protected ListOfTasks toDoList;

    private ArrayList<String> listOfDevelopers;

    // EFFECT: creates new user with userID, password, and status
    public User(String userID, String password, ListOfTasks toDoList, ArrayList<String> listOfDevelopers) {
        this.userID = userID;
        this.password = password;
        this.toDoList = toDoList;
        this.listOfDevelopers = listOfDevelopers;
    }

    // REQUIRES: newUserID not less than of length 5
    // MODIFIES: this
    // EFFECT: changes the userID
    public void changeUserID(String newUserID) {
        this.userID = newUserID;
    }

    // REQUIRES: newPassword not less than of length 5
    // MODIFIES: this
    // EFFECT: changes the password
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // EFFECT: returns the userID
    public String getUserID() {
        return userID;
    }

    // EFFECT: returns the password
    public String getPassword() {
        return password;
    }

    // EFFECT: returns the toDoList
    public ListOfTasks getToDoList() {
        return toDoList;
    }

    // EFFECT: returns the developer list
    public ArrayList<String> getListOfDevelopers() {
        return listOfDevelopers;
    }

    // EFFECT: returns the status of the User
    public String getStatus() {
        return status;
    }

    // MODIFIES: Task
    // EFFECT: modifies the toDoList with tasks of varying visibility depending on filter
    public void filterToDoList(String filter) {
        switch (filter) {
            case "reset":
                toDoList.reset();
                break;
            case "overdue":
                toDoList.filterTaskWithOverdue();
                break;
            case "not assigned yet":
                toDoList.filterTasksWithoutDeveloper();
                break;
            case "Not started":
                toDoList.filterTaskWithStatus("Not started");
                break;
            case "In progress":
                toDoList.filterTaskWithStatus("In progress");
                break;
            case "Completed":
                toDoList.filterTaskWithStatus("Completed");
                break;
        }
    }

    // MODIFIES: Task
    // EFFECT: modifies the toDoList with tasks of varying visibility depending on filter
    public void filterToDoList(int filter) {
        toDoList.filterTaskWithUrgency(filter);
    }

    // MODIFIES: this
    // EFFECT: adds developer listOfDevelopers
    public void addDev(Developer developer) {
        listOfDevelopers.add(developer.getUserID());
    }
}
