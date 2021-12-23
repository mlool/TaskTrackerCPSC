package model.users;

import model.Event;
import model.EventLog;
import model.exceptions.UserAlreadyExistException;
import model.exceptions.UserDoesNotExistException;
import org.json.JSONArray;

import java.util.ArrayList;

public class ListOfDevelopers {
    private ArrayList<Developer> developerArrayList;

    public ListOfDevelopers() {
        developerArrayList = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("new developer list initiated"));
    }

    // MODIFIES: this
    // EFFECT: adds a developer to the ArrayList
    //         if given developer already exist, throws UserAlreadyExistException
    public void add(Developer newDeveloper) throws UserAlreadyExistException {
        if (namesOnly().contains(newDeveloper.getUserID())) {
            throw new UserAlreadyExistException();
        }
        EventLog.getInstance().logEvent(new Event("new developer: " + newDeveloper.getUserID()
                + " added to developer list"));
        developerArrayList.add(newDeveloper);
    }

    // MODIFIES: this
    // EFFECT: removes the given developer from the ArrayList
    //         if given developer does not exist, throws UserDoesNotExistException
    public void remove(Developer developerToRemove) throws UserDoesNotExistException {
        if (developerArrayList.contains(developerToRemove)) {
            developerArrayList.remove(developerToRemove);
            EventLog.getInstance().logEvent(new Event("developer: " + developerToRemove.getUserID()
                    + " removed from developer list"));
        } else {
            throw new UserDoesNotExistException();
        }
    }

    // MODIFIES: this
    // EFFECT: removes the given developer with given name from the ArrayList
    //         if given developer does not exist, throws UserDoesNotExistException
    public void remove(String developerToRemove) throws UserDoesNotExistException {
        for (Developer developer : developerArrayList) {
            if (developer.getUserID().equals(developerToRemove)) {
                developerArrayList.remove(developer);
                EventLog.getInstance().logEvent(new Event("developer: " + developerToRemove
                        + " removed from developer list"));
                return;
            }
        }
        throw new UserDoesNotExistException();
    }

    // EFFECT: returns an ArrayList of userID in the given user list
    public ArrayList<String> namesOnly() {
        ArrayList<String> resultingUserIDList = new ArrayList<>();
        for (Developer user : developerArrayList) {
            resultingUserIDList.add(user.getUserID());
        }
        return resultingUserIDList;
    }

    // EFFECT: returns the Developer with the given name
    //         if given name does not exist, returns UserDoesNotExistException
    public Developer getDeveloper(String developerName) throws UserDoesNotExistException {
        for (Developer developer : developerArrayList) {
            if (developer.getUserID().equals(developerName)) {
                return developer;
            }
        }
        throw new UserDoesNotExistException();
    }

    // EFFECT: checks if userID exist and password is correct, if both are true, return true, else false
    public boolean checkForDeveloper(String userID, String inputPassword) {
        for (Developer developer : developerArrayList) {
            if (developer.getUserID().equals(userID)) {
                return developer.getPassword().equals(inputPassword);
            }
        }
        return false;
    }

    // EFFECT: returns true if name exist, else false
    public boolean findName(String name) {
        for (Developer developerName : developerArrayList) {
            if (developerName.getUserID().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // converts list of developers to json
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (Developer developer : developerArrayList) {
            jsonArray.put(developer.toJson());
        }

        return jsonArray;
    }
}
