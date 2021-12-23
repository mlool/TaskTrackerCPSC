package model.tasks;

import model.Event;
import model.EventLog;
import persistence.Writable;
import org.json.JSONObject;

import java.time.LocalDate;

public class Task implements Writable {
    private String name;

    private Date dateOfCreation;
    private Date dateOfCompletion;

    private int urgency;
    private int hoursRequiredForCompletion;
    private String status;

    private String assignedDeveloper;
    private boolean visibility;

    // EFFECTS: instantiates a task with prefilled dateOfCreation and assignedDeveloper of none
    public Task(String name, Date dateOfCompletion, int urgency, int hoursRequiredForCompletion,
                String status) {
        this.name = name;
        this.dateOfCreation = new Date();
        this.dateOfCompletion = dateOfCompletion;
        this.urgency = urgency;
        this.hoursRequiredForCompletion = hoursRequiredForCompletion;
        this.status = status;
        this.assignedDeveloper = "none";
        this.visibility = true;
        EventLog.getInstance().logEvent(new Event("new task: " + name + " added"));
    }

    // EFFECTS: instantiates a task with prefilled dateOfCreation and assignedDeveloper of none
    public Task(String name, Date dateOfCreation, Date dateOfCompletion, int urgency, int hoursRequiredForCompletion,
                String status, String assignedDeveloper, boolean visibility) {
        this.name = name;
        this.dateOfCreation = dateOfCreation;
        this.dateOfCompletion = dateOfCompletion;
        this.urgency = urgency;
        this.hoursRequiredForCompletion = hoursRequiredForCompletion;
        this.status = status;
        this.assignedDeveloper = assignedDeveloper;
        this.visibility = visibility;
        EventLog.getInstance().logEvent(new Event("new task: " + name + " added"));
    }

//    // EFFECTS: instantiates default task, with today's date and everything else at default
//    public Task() {
//        this.name = "Unspecified";
//        this.dateOfCreation = LocalDate.now();
//        this.dateOfCompletion = dateOfCreation.plusMonths(1);
//        this.urgency = 0;
//        this.hoursRequiredForCompletion = 0;
//        this.status = "Not started";
//        this.assignedDeveloper = "none";
//        this.visibility = true;
//    }

    //=========================Setters=========================

    // MODIFIES: this
    // EFFECT: change name
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECT: change dateOfCompletion by consuming year, month, and day
    public void setDateOfCompletion(int year, int month, int day) {
        dateOfCompletion = new Date(year, month, day);
    }

    // MODIFIES: this
    // EFFECT: change urgency, higher = more urgent
    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    // MODIFIES: this
    // EFFECT: change hoursRequiredForCompletion
    public void setHoursRequiredForCompletion(int newHoursRequiredForCompletion) {
        this.hoursRequiredForCompletion = newHoursRequiredForCompletion;
    }

    // MODIFIES: this
    // EFFECT: change status of task
    public void setStatus(String status) {
        this.status = status;
    }

    // MODIFIES: this
    // EFFECT: change assignedDeveloper
    public void setAssignedDeveloper(String assignedDeveloperName) {
        this.assignedDeveloper = assignedDeveloperName;
    }

    // MODIFIES: this
    // EFFECT: turn visibility on or off
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    //=========================Getters=========================

    // EFFECT: get name
    public String getName() {
        return name;
    }

    // EFFECT: get dateOfCreation
    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    // EFFECT: get dateOfCompletion
    public Date getDateOfCompletion() {
        return dateOfCompletion;
    }

    // EFFECT: get urgency
    public int getUrgency() {
        return urgency;
    }

    // EFFECT: get hoursRequiredForCompletion
    public int getHoursRequiredForCompletion() {
        return hoursRequiredForCompletion;
    }

    // EFFECT: get status
    public String getStatus() {
        return status;
    }

    // EFFECT: get assignedDeveloper
    public String getAssignedDeveloper() {
        return assignedDeveloper;
    }

    // EFFECT: get visibility
    public boolean getVisibility() {
        return this.visibility;
    }

    //=========================Others=========================

    // EFFECT: returns true if the two tasks have same name, false otherwise
    public boolean isTaskName(String taskName) {
        return this.name.equals(taskName);
    }

    // EFFECT: returns true if urgency same or more urgent than given urgencyLevel
    public boolean isMoreThanUrgency(int urgencyLevel) {
        return urgency >= urgencyLevel;
    }

    // EFFECT: returns true if date of completion have passed and task is not completed
    public boolean isOverDue() {
//        Date currentDate = new Date();
//        if (currentDate.getYear() < dateOfCompletion.getYear()) {
//            return false;
//        } else if (currentDate.getMonthValue() < dateOfCompletion.getMonthValue()) {
//            return false;
//        } else if (currentDate.getDayOfMonth() < dateOfCompletion.getDayOfMonth()) {
//            return false;
//        } else if (currentDate.getYear() == dateOfCompletion.getYear()
//                && currentDate.getMonthValue() == dateOfCompletion.getMonthValue()
//                && currentDate.getDayOfMonth() == dateOfCompletion.getDayOfMonth()) {
//            return false;
//        } else {
//            return true;
//        }

        LocalDate currentDate = LocalDate.now();
        return currentDate.compareTo(LocalDate.of(dateOfCompletion.getYear(),
                dateOfCompletion.getMonthValue(), dateOfCompletion.getDayOfMonth())) > 0;
    }

    // EFFECT: returns true if task is assigned to given user
    public boolean isAssigned(String developerName) {
        return assignedDeveloper.equals(developerName);
    }

    // EFFECT: returns true if task is not assigned to anyone
    public boolean isOpenToDeveloper() {
        return assignedDeveloper.equals("none");
    }

    // EFFECT: returns true if status is same as given wantedStatus
    public boolean isSameStatus(String wantedStatus) {
        return status.equals(wantedStatus);
    }

    // converts task to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("dateOfCreation", dateOfCreation.toJson());
        json.put("dateOfCompletion", dateOfCompletion.toJson());
        json.put("urgency", urgency);
        json.put("hoursRequiredForCompletion", hoursRequiredForCompletion);
        json.put("status", status);
        json.put("assignedDeveloper", assignedDeveloper);
        json.put("visibility", visibility);
        return json;
    }
}
