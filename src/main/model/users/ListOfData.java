package model.users;

import persistence.Writable;
import model.tasks.ListOfTasks;
import org.json.JSONObject;

// stores admin, developer, and visitor lists
public class ListOfData implements Writable {
    private ListOfAdmins adminList;
    private ListOfDevelopers developerList;
    private ListOfVisitors visitorList;
    private ListOfTasks toDoList;

    // EFFECTS: instantiates a new task list
    public ListOfData(ListOfAdmins listOfAdmins, ListOfDevelopers listOfDevelopers, ListOfVisitors listOfVisitors,
                      ListOfTasks listOfTasks) {
        adminList = listOfAdmins;
        developerList = listOfDevelopers;
        visitorList = listOfVisitors;
        toDoList = listOfTasks;
    }

    // EFFECT: returns the adminList
    public ListOfAdmins getAdminList() {
        return adminList;
    }

    // EFFECT: returns the developerList
    public ListOfDevelopers getDeveloperList() {
        return developerList;
    }

    // EFFECT: returns the visitorList
    public ListOfVisitors getVisitorList() {
        return visitorList;
    }

    // EFFECT: returns the toDoList
    public ListOfTasks getToDoList() {
        return toDoList;
    }

    // converts listOfUsers to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("toDoList", toDoList.toJson());
        json.put("adminList", adminList.toJson());
        json.put("developerList", developerList.toJson());
        json.put("visitorList", visitorList.toJson());
        return json;
    }
























    //    // EFFECT: returns the userList
//    public ArrayList<User> getUserList() {
//        return userList;
//    }
//
//    // MODIFIES: this
//    // EFFECT: adds a user to the userList and returns true, returns false if userID already exist
//    public boolean signUp(User newUser) {
//        for (User user : userList) {
//            if (user.getUserID().equals(newUser.getUserID())) {
//                return false;
//            }
//        }
//        userList.add(newUser);
//        return true;
//    }
//
//    // MODIFIES: this
//    // EFFECT: removes a user from the userList and returns true, false if user not found
//    public boolean removeUser(User userToRemove) {
//        for (User user : userList) {
//            if (user.getUserID().equals(userToRemove.getUserID())) {
//                userList.remove(userToRemove);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // MODIFIES: this
//    // EFFECT: removes a user from the userList and returns true, false if user not found
//    public boolean removeUser(String userToRemove) {
//        if (findUser(userToRemove) == null) {
//            return false;
//        }
//        return removeUser(findUser(userToRemove));
//    }
//
//    // EFFECT: returns user from userList just from the userID, null if not found
//    public User findUser(String userName) {
//        for (User user : userList) {
//            if (user.getUserID().equals(userName)) {
//                return user;
//            }
//        }
//        return null;
//    }

}
