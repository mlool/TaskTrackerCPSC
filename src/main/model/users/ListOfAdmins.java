package model.users;

import model.Event;
import model.EventLog;
import model.exceptions.UserAlreadyExistException;
import model.exceptions.UserDoesNotExistException;
import org.json.JSONArray;

import java.util.ArrayList;

// a list of admins
public class ListOfAdmins {
    private ArrayList<Admin> adminArrayList;

    public ListOfAdmins() {
        adminArrayList = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("new admin list initiated"));
    }

    // MODIFIES: this
    // EFFECT: adds a admin to the ArrayList
    //         if given admin already exist, throws UserAlreadyExistException
    public void add(Admin newAdmin) throws UserAlreadyExistException {
        if (namesOnly().contains(newAdmin.getUserID())) {
            throw new UserAlreadyExistException();
        }
        EventLog.getInstance().logEvent(new Event("new admin: " + newAdmin.getUserID()
                + " added to admin list"));
        adminArrayList.add(newAdmin);
    }

    // MODIFIES: this
    // EFFECT: removes the given admin from the ArrayList
    //         if given admin does not exist, throws UserDoesNotExistException
    public void remove(Admin adminToRemove) throws UserDoesNotExistException {
        if (adminArrayList.contains(adminToRemove)) {
            adminArrayList.remove(adminToRemove);
            EventLog.getInstance().logEvent(new Event("admin: " + adminToRemove.getUserID()
                    + " removed from admin list"));
        } else {
            throw new UserDoesNotExistException();
        }
    }

    // MODIFIES: this
    // EFFECT: removes the given admin with given name from the ArrayList
    //         if given admin does not exist, throws UserDoesNotExistException
    public void remove(String adminToRemove) throws UserDoesNotExistException {
        for (Admin admin : adminArrayList) {
            if (admin.getUserID().equals(adminToRemove)) {
                adminArrayList.remove(admin);
                EventLog.getInstance().logEvent(new Event("admin: " + adminToRemove
                        + " removed from admin list"));
                return;
            }
        }
        throw new UserDoesNotExistException();
    }

    // EFFECT: returns an ArrayList of userID in the given user list
    public ArrayList<String> namesOnly() {
        ArrayList<String> resultingUserIDList = new ArrayList<>();
        for (Admin admin : adminArrayList) {
            resultingUserIDList.add(admin.getUserID());
        }
        return resultingUserIDList;
    }

    // EFFECT: returns the Admin with the given name
    //         if given name does not exist, returns UserDoesNotExistException
    public Admin getAdmin(String adminName) throws UserDoesNotExistException {
        for (Admin admin : adminArrayList) {
            if (admin.getUserID().equals(adminName)) {
                return admin;
            }
        }
        throw new UserDoesNotExistException();
    }

    // EFFECT: checks if userID exist and password is correct, if both are true, return true, else false
    public boolean checkForAdmin(String userID, String inputPassword) {
        for (Admin admin : adminArrayList) {
            if (admin.getUserID().equals(userID)) {
                return admin.getPassword().equals(inputPassword);
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECT: add the developer to all admins in list
    public void addDeveloper(Developer newDeveloper) {
        for (Admin admin : adminArrayList) {
            admin.addDev(newDeveloper);
        }
    }

    // places list of admins to json
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (Admin admin : adminArrayList) {
            jsonArray.put(admin.toJson());
        }

        return jsonArray;
    }
}
