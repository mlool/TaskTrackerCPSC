package model.users;

import model.Event;
import model.EventLog;
import model.exceptions.UserAlreadyExistException;
import model.exceptions.UserDoesNotExistException;
import org.json.JSONArray;

import java.util.ArrayList;

// represents a list of visitor users
public class ListOfVisitors {
    private ArrayList<Visitor> visitorArrayList;

    public ListOfVisitors() {
        visitorArrayList = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("new visitor list initiated"));
    }

    // MODIFIES: this
    // EFFECT: adds a visitor to the ArrayList
    //         if given admin already exist, throws UserAlreadyExistException
    public void add(Visitor newVisitor) throws UserAlreadyExistException {
        if (namesOnly().contains(newVisitor.getUserID())) {
            throw new UserAlreadyExistException();
        }
        EventLog.getInstance().logEvent(new Event("new visitor: " + newVisitor.getUserID()
                + " added to visitor list"));
        visitorArrayList.add(newVisitor);
    }

    // MODIFIES: this
    // EFFECT: removes the given visitor from the ArrayList
    //         if given visitor does not exist, throws UserDoesNotExistException
    public void remove(Visitor visitorToRemove) throws UserDoesNotExistException {
        if (visitorArrayList.contains(visitorToRemove)) {
            visitorArrayList.remove(visitorToRemove);
            EventLog.getInstance().logEvent(new Event("visitor: " + visitorToRemove.getUserID()
                    + " removed from visitor list"));
        } else {
            throw new UserDoesNotExistException();
        }
    }

    // MODIFIES: this
    // EFFECT: removes the given visitor with given name from the ArrayList
    //         if given visitor does not exist, throws UserDoesNotExistException
    public void remove(String visitorToRemove) throws UserDoesNotExistException {
        for (Visitor visitor : visitorArrayList) {
            if (visitor.getUserID().equals(visitorToRemove)) {
                visitorArrayList.remove(visitor);
                EventLog.getInstance().logEvent(new Event("visitor: " + visitorToRemove
                        + " removed from visitor list"));
                return;
            }
        }
        throw new UserDoesNotExistException();
    }

    // EFFECT: returns an ArrayList of userID in the given user list
    public ArrayList<String> namesOnly() {
        ArrayList<String> resultingUserIDList = new ArrayList<>();
        for (Visitor visitor : visitorArrayList) {
            resultingUserIDList.add(visitor.getUserID());
        }
        return resultingUserIDList;
    }

    // EFFECT: returns the visitor with the given name
    //         if given name does not exist, returns UserDoesNotExistException
    public Visitor getVisitor(String visitorName) throws UserDoesNotExistException {
        for (Visitor visitor : visitorArrayList) {
            if (visitor.getUserID().equals(visitorName)) {
                return visitor;
            }
        }
        throw new UserDoesNotExistException();
    }

    // EFFECT: checks if userID exist and password is correct, if both are true, return true, else false
    public boolean checkForVisitor(String userID, String inputPassword) {
        for (Visitor visitor : visitorArrayList) {
            if (visitor.getUserID().equals(userID)) {
                return visitor.getPassword().equals(inputPassword);
            }
        }
        return false;
    }

    // EFFECT: places visitorList to json
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (Visitor visitor : visitorArrayList) {
            jsonArray.put(visitor.toJson());
        }

        return jsonArray;
    }
}
