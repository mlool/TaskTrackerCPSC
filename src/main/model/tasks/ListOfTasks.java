package model.tasks;

import model.Event;
import model.EventLog;
import org.json.JSONArray;

import java.util.ArrayList;

public class ListOfTasks {
    private ArrayList<Task> taskList;

    // EFFECTS: instantiates a new task list
    public ListOfTasks() {
        taskList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECT: if task with same name does not already exist, add a new task and return true, otherwise false
    public boolean addTask(Task newTask) {
        for (Task task : taskList) {
            if (task.isTaskName(newTask.getName())) {
                return false;
            }
        }
        taskList.add(newTask);
        return true;
    }

    // MODIFIES: this
    // EFFECT: if task exist, remove that task and return true, else return false
    public boolean remove(Task taskToRemove) {
        return taskList.remove(taskToRemove);
    }

    // EFFECT: returns the task with name
    public Task getTaskWithName(String name) {
        for (Task task : taskList) {
            if (task.isTaskName(name)) {
                return task;
            }
        }
        return null;
    }

    // EFFECT: return taskList's length
    public int getLength() {
        return taskList.size();
    }

    // EFFECT: returns the taskList
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    // ==============Filter Functions======================

    //MODIFIES: task
    // EFFECT: turn visibility on for every task
    public void reset() {
        for (Task task : taskList) {
            task.setVisibility(true);
        }
    }

    // MODIFIES: task
    // EFFECT: turn visibility off for all tasks with urgency lower than the given urgency
    public void filterTaskWithUrgency(int urgency) {
        for (Task task : taskList) {
            if (!task.isMoreThanUrgency(urgency)) {
                task.setVisibility(false);
            }
        }
    }

    // MODIFIES: task
    // EFFECT: turn visibility off for all tasks that is not overdue
    public void filterTaskWithOverdue() {
        for (Task task : taskList) {
            if (!task.isOverDue()) {
                task.setVisibility(false);
            }
        }
    }

    // MODIFIES: task
    // EFFECT: turn visibility off for all tasks that is not assigned to given user
    public void filterTasksWithDeveloper(String developerName) {
        for (Task task : taskList) {
            if (!task.isAssigned(developerName)) {
                task.setVisibility(false);
            }
        }
    }

    // MODIFIES: task
    // EFFECT: turn visibility off for all tasks that is already assigned
    public void filterTasksWithoutDeveloper() {
        for (Task task : taskList) {
            if (!task.isOpenToDeveloper()) {
                task.setVisibility(false);
            }
        }
    }

    // MODIFIES: this
    // EFFECT: turn visibility off for all task that is not of the wanted status
    public void filterTaskWithStatus(String wantedStatus) {
        for (Task task : taskList) {
            if (!task.isSameStatus(wantedStatus)) {
                task.setVisibility(false);
            }
        }
    }

    // converts list of users to json file
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task task : taskList) {
            jsonArray.put(task.toJson());
        }

        return jsonArray;
    }
}
