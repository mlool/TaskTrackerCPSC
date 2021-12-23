package model;

import model.exceptions.UserAlreadyExistException;
import model.exceptions.UserDoesNotExistException;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.tasks.Date;
import model.tasks.ListOfTasks;
import model.tasks.Task;
import model.users.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// test for json
public class JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ListOfData lou = new ListOfData(new ListOfAdmins(), new ListOfDevelopers(), new ListOfVisitors(),
                    new ListOfTasks());
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyListOfUsers() {
        try {
            ListOfData lou = new ListOfData(new ListOfAdmins(), new ListOfDevelopers(), new ListOfVisitors(),
                    new ListOfTasks());
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyListOfUsers.json");
            writer.open();
            writer.write(lou);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyListOfUsers.json");
            try {
                lou = reader.read();
            } catch (UserAlreadyExistException e) {
                fail("should not happen");
            }
            assertFalse(lou.getAdminList().checkForAdmin("admin1", "password"));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterLisOfUsers() {
        try {
            ListOfTasks toDoList = new ListOfTasks();

            ListOfAdmins adminList = new ListOfAdmins();
            ListOfDevelopers developerList = new ListOfDevelopers();
            ListOfVisitors visitorList = new ListOfVisitors();

            ArrayList<String> developerStringList = new ArrayList<>();
            developerStringList.add("dev1");

            toDoList.addTask(new Task("new test", new Date(2022, 2, 2), 2,
                    2, "not started"));

            try {
                developerList.add(new Developer("dev1", "123", toDoList, developerStringList));
                adminList.add(new Admin("admin1", "123", toDoList, developerList.namesOnly()));
                adminList.addDeveloper(new Developer("dev2", "123", toDoList, developerStringList));
                visitorList.add(new Visitor("visitor1", "231", toDoList, developerList.namesOnly()));
                assertEquals("231", visitorList.getVisitor("visitor1").getPassword());
            } catch (UserAlreadyExistException | UserDoesNotExistException e) {
                fail("should not happen");
            }

            try {
                visitorList.add(new Visitor("visitor1", "231", toDoList, developerList.namesOnly()));
                fail("should not happen");
            } catch (UserAlreadyExistException e) {
                //expected
            }

            try {
                adminList.add(new Admin("admin1", "123", toDoList, developerList.namesOnly()));
                fail("should not happen");
            } catch (UserAlreadyExistException e) {
                //expected
            }

            try {
                developerList.add(new Developer("dev1", "123", toDoList, developerStringList));
                fail("should not happen");
            } catch (UserAlreadyExistException e) {
                //expected
            }

            ListOfData lou = new ListOfData(adminList, developerList, visitorList,
                    toDoList);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyListOfUsers.json");
            writer.open();
            writer.write(lou);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyListOfUsers.json");
            try {
                lou = reader.read();
            } catch (UserAlreadyExistException e) {
                fail("should not happen");
            }
            assertTrue(lou.getAdminList().checkForAdmin("admin1", "123"));
            try {
                assertEquals("dev1", lou.getAdminList().getAdmin("admin1")
                        .getListOfDevelopers().get(0));
                assertEquals("dev2", lou.getAdminList().getAdmin("admin1")
                        .getListOfDevelopers().get(1));

            } catch (UserDoesNotExistException e) {
                fail("should not happen");
            }

            assertTrue(lou.getDeveloperList().checkForDeveloper("dev1", "123"));
            assertFalse(lou.getDeveloperList().checkForDeveloper("dev1", "321"));
            assertFalse(lou.getDeveloperList().checkForDeveloper("dev3", "123"));

            assertTrue(lou.getDeveloperList().findName("dev1"));
            assertFalse(lou.getDeveloperList().findName("dev2"));

            assertTrue(lou.getVisitorList().checkForVisitor("visitor1", "231"));
            assertFalse(lou.getVisitorList().checkForVisitor("visitor1", "321"));
            assertFalse(lou.getVisitorList().checkForVisitor("vis3", "321"));

            assertEquals(1, lou.getToDoList().getLength());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testUnFoundUser() {
        ListOfTasks toDoList = new ListOfTasks();

        ListOfAdmins adminList = new ListOfAdmins();
        ListOfDevelopers developerList = new ListOfDevelopers();
        ListOfVisitors visitorList = new ListOfVisitors();

        ArrayList<String> developerStringList = new ArrayList<>();
        developerStringList.add("dev1");

        toDoList.addTask(new Task("new test", new Date(2022, 2, 2), 2,
                2, "not started"));

        try {
            developerList.add(new Developer("dev1", "123", toDoList, developerStringList));
            adminList.add(new Admin("admin1", "123", toDoList, developerList.namesOnly()));
            adminList.addDeveloper(new Developer("dev2", "123", toDoList, developerStringList));
            visitorList.add(new Visitor("visitor1", "231", toDoList, developerList.namesOnly()));
            assertEquals("231", visitorList.getVisitor("visitor1").getPassword());
        } catch (UserAlreadyExistException | UserDoesNotExistException e) {
            fail("should not happen");
        }

        ListOfData lou = new ListOfData(adminList, developerList, visitorList,
                toDoList);
        JsonWriter writer = new JsonWriter("./data/testUnfoundUser.json");
        try {
            writer.open();
        } catch (FileNotFoundException e) {
            fail("should not happen");
        }
        writer.write(lou);
    }

}
