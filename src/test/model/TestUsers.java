package model;

import model.exceptions.UserAlreadyExistException;
import model.exceptions.UserDoesNotExistException;
import model.tasks.Date;
import model.tasks.ListOfTasks;
import model.users.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// test of list of users
public class TestUsers {
    Admin newAdmin;
    Developer newDeveloper;
    Visitor newVisitor;

    ListOfAdmins listOfAdmins;
    ListOfDevelopers listOfDevelopers;
    ListOfVisitors listOfVisitors;

    ListOfTasks currentProject;
    ArrayList<String> developerList;

    ListOfData newUserList;

    @BeforeEach
    public void runBefore() {
        currentProject = new ListOfTasks();
        developerList = new ArrayList<>();
        listOfAdmins = new ListOfAdmins();
        listOfDevelopers = new ListOfDevelopers();
        listOfVisitors = new ListOfVisitors();
        newAdmin = new Admin("admin1", "12345", currentProject, developerList);
        newDeveloper = new Developer("developer1", "12345", currentProject, developerList);
        newVisitor = new Visitor("visitor1", "12345", currentProject, developerList);

//        try {
//            listOfAdmins.add(newAdmin);
//            listOfDevelopers.add(newDeveloper);
//            listOfVisitors.add(newVisitor);
//        } catch (UserAlreadyExistException e) {
//            fail("should not happen");
//        }

        newUserList = new ListOfData(listOfAdmins, listOfDevelopers, listOfVisitors, currentProject);
    }
//
    @Test
    public void testSignUp() {
        try {
            newUserList.getAdminList().add(newAdmin);
            newUserList.getDeveloperList().add(newDeveloper);
            newUserList.getVisitorList().add(newVisitor);
        } catch (UserAlreadyExistException e) {
            fail("should not happen");
        }

        try {
            newUserList.getAdminList().add(newAdmin);
            newUserList.getDeveloperList().add(newDeveloper);
            newUserList.getVisitorList().add(newVisitor);
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserAlreadyExistException e) {
            // expected
        }

        try {
            assertEquals(newAdmin, newUserList.getAdminList().getAdmin("admin1"));
            assertEquals(newDeveloper, newUserList.getDeveloperList().getDeveloper("developer1"));
            assertEquals(newVisitor, newUserList.getVisitorList().getVisitor("visitor1"));
            assertEquals("admin", newUserList.getAdminList().getAdmin("admin1").getStatus());
            assertEquals("developer", newUserList.getDeveloperList().getDeveloper("developer1")
                    .getStatus());
            assertEquals("visitor", newUserList.getVisitorList().getVisitor("visitor1")
                    .getStatus());
        } catch (UserDoesNotExistException e) {
            fail("should not happen");
        }
    }

    @Test
    public void testRemove() {
        try {
            newUserList.getAdminList().add(newAdmin);
            newUserList.getDeveloperList().add(newDeveloper);
            newUserList.getVisitorList().add(newVisitor);
        } catch (UserAlreadyExistException e) {
            fail("should not happen");
        }

        try {
            newUserList.getAdminList().remove("admin1");
        } catch (UserDoesNotExistException e) {
            fail("should not happen");
        }

        try {
            assertEquals(newAdmin, newUserList.getAdminList().getAdmin("admin1"));
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserDoesNotExistException e) {
            // expected
        }

        try {
            newUserList.getDeveloperList().remove("developer1");
        } catch (UserDoesNotExistException e) {
            fail("should not happen");
        }

        try {
            assertEquals(newDeveloper, newUserList.getDeveloperList().getDeveloper("developer1"));
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserDoesNotExistException e) {
            // expected
        }

        try {
            newUserList.getVisitorList().remove("visitor1");
        } catch (UserDoesNotExistException e) {
            fail("should not happen");
        }

        try {
            assertEquals(newVisitor, newUserList.getVisitorList().getVisitor("visitor1"));
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserDoesNotExistException e) {
            // expected
        }

        try {
            newUserList.getAdminList().add(newAdmin);
            newUserList.getDeveloperList().add(newDeveloper);
            newUserList.getVisitorList().add(newVisitor);
        } catch (UserAlreadyExistException e) {
            fail("should not happen");
        }

        try {
            newUserList.getAdminList().remove(newAdmin);
        } catch (UserDoesNotExistException e) {
            fail("should not happen");
        }

        try {
            assertEquals(newAdmin, newUserList.getAdminList().getAdmin("admin1"));
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserDoesNotExistException e) {
            // expected
        }

        try {
            newUserList.getDeveloperList().remove(newDeveloper);
        } catch (UserDoesNotExistException e) {
            fail("should not happen");
        }

        try {
            assertEquals(newDeveloper, newUserList.getDeveloperList().getDeveloper("developer1"));
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserDoesNotExistException e) {
            // expected
        }

        try {
            newUserList.getVisitorList().remove(newVisitor);
        } catch (UserDoesNotExistException e) {
            fail("should not happen");
        }

        try {
            assertEquals(newVisitor, newUserList.getVisitorList().getVisitor("visitor1"));
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserDoesNotExistException e) {
            // expected
        }

        try {
            newUserList.getDeveloperList().remove("developer1");
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserDoesNotExistException e) {
            //expected
        }

        try {
            newUserList.getDeveloperList().remove(newDeveloper);
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserDoesNotExistException e) {
            //expected
        }

        try {
            newUserList.getAdminList().remove("admin1");
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserDoesNotExistException e) {
            //expected
        }

        try {
            newUserList.getAdminList().remove(newAdmin);
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserDoesNotExistException e) {
            //expected
        }

        try {
            newUserList.getVisitorList().remove("visitor1");
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserDoesNotExistException e) {
            //expected
        }

        try {
            newUserList.getVisitorList().remove(newVisitor);
            fail("UserAlreadyExistException should have been thrown");
        } catch (UserDoesNotExistException e) {
            //expected
        }

        try {
            newUserList.getAdminList().add(newAdmin);
            newUserList.getDeveloperList().add(newDeveloper);
            newUserList.getVisitorList().add(newVisitor);
        } catch (UserAlreadyExistException e) {
            fail("should not happen");
        }

        Admin newAdmin2 = new Admin("admin2", "12345", currentProject, developerList);
        Developer newDeveloper2 = new Developer("developer2", "12345", currentProject, developerList);
        Visitor newVisitor2 = new Visitor("visitor2", "12345", currentProject, developerList);

        try {
            newUserList.getAdminList().add(newAdmin2);
            newUserList.getDeveloperList().add(newDeveloper2);
            newUserList.getVisitorList().add(newVisitor2);
        } catch (UserAlreadyExistException e) {
            fail("should not happen");
        }


        try {
            newUserList.getAdminList().remove("admin2");
        } catch (UserDoesNotExistException e) {
            fail("should not happen");
        }

        try {
            newUserList.getDeveloperList().remove("developer2");
        } catch (UserDoesNotExistException e) {
            fail("should not happen");
        }

        try {
            newUserList.getVisitorList().remove("visitor2");
        } catch (UserDoesNotExistException e) {
            fail("should not happen");
        }

        try {
            newUserList.getAdminList().add(newAdmin2);
            newUserList.getDeveloperList().add(newDeveloper2);
            newUserList.getVisitorList().add(newVisitor2);
        } catch (UserAlreadyExistException e) {
            fail("should not happen");
        }

        try {
            assertEquals(newAdmin2, newUserList.getAdminList().getAdmin("admin2"));
            assertEquals(newDeveloper2, newUserList.getDeveloperList().getDeveloper("developer2"));
            assertEquals(newVisitor2, newUserList.getVisitorList().getVisitor("visitor2"));
        } catch (UserDoesNotExistException e) {
            fail("should not happen");
        }

        assertTrue(newUserList.getAdminList().checkForAdmin("admin2", "12345"));

    }

    @Test
    public void testSetUserID() {
        newAdmin.changeUserID("newAdmin1");
        assertEquals("newAdmin1", newAdmin.getUserID());

        newDeveloper.changeUserID("newDev1");
        assertEquals("newDev1", newDeveloper.getUserID());

        newVisitor.changeUserID("newVis1");
        assertEquals("newVis1", newVisitor.getUserID());
    }

    @Test
    public void testSetPassword() {
        newAdmin.changePassword("54321");
        assertEquals("54321", newAdmin.getPassword());

        newDeveloper.changePassword("54321");
        assertEquals("54321", newDeveloper.getPassword());

        newVisitor.changePassword("54321");
        assertEquals("54321", newVisitor.getPassword());
    }

    @Test
    public void testFilterToDoList() {
        newAdmin.addTask("test1", new Date(2020, 10, 15),
                4, 3);
        assertFalse(newAdmin.addTask("test1", new Date(2020, 10, 15),
                4, 3));
        newDeveloper.addTask("test2", new Date(2020, 12, 28),
                2, 5);
        newDeveloper.addTask("test3", new Date(2021, 12, 13),
                3, 3);
        newAdmin.addTask("test4", new Date(2021, 10, 7),
                3, 5);
        newAdmin.addTask("test5", new Date(2021, 11, 15),
                1, 7);

        newAdmin.filterToDoList("overdue");
        assertTrue(newAdmin.getToDoList().getTaskWithName("test1").getVisibility());
        assertTrue(newDeveloper.getToDoList().getTaskWithName("test2").getVisibility());
        assertFalse(newDeveloper.getToDoList().getTaskWithName("test3").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test4").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test5").getVisibility());

        newAdmin.filterToDoList("reset");
        assertTrue(newAdmin.getToDoList().getTaskWithName("test1").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test2").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test3").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test4").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test5").getVisibility());

        newAdmin.filterToDoList("bababa");
        assertTrue(newAdmin.getToDoList().getTaskWithName("test1").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test2").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test3").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test4").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test5").getVisibility());

        newDeveloper.takeOnTask("test4");
        newAdmin.modifyTask("test2", "developer", "developer1");
        newAdmin.filterToDoList("not assigned yet");
        assertTrue(newAdmin.getToDoList().getTaskWithName("test1").getVisibility());
        assertFalse(newAdmin.getToDoList().getTaskWithName("test2").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test3").getVisibility());
        assertFalse(newAdmin.getToDoList().getTaskWithName("test4").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test5").getVisibility());

        newAdmin.filterToDoList("reset");
        newDeveloper.changeStatus("test2", "In progress");
        newAdmin.modifyTask("test3", "status", "Completed");
        newVisitor.filterToDoList("Not started");
        assertTrue(newAdmin.getToDoList().getTaskWithName("test1").getVisibility());
        assertFalse(newAdmin.getToDoList().getTaskWithName("test2").getVisibility());
        assertFalse(newAdmin.getToDoList().getTaskWithName("test3").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test4").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test5").getVisibility());

        newAdmin.filterToDoList("reset");
        newDeveloper.changeStatus("test2", "In progress");
        newAdmin.modifyTask("test3", "status", "Completed");
        newAdmin.modifyTask("test5", "status", "In progress");
        newVisitor.filterToDoList("In progress");
        assertFalse(newAdmin.getToDoList().getTaskWithName("test1").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test2").getVisibility());
        assertFalse(newAdmin.getToDoList().getTaskWithName("test3").getVisibility());
        assertFalse(newAdmin.getToDoList().getTaskWithName("test4").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test5").getVisibility());

        newAdmin.filterToDoList("reset");
        newDeveloper.changeStatus("test2", "In progress");
        newAdmin.modifyTask("test3", "status", "Completed");
        newAdmin.modifyTask("test5", "status", "In progress");
        newDeveloper.changeStatus("test4", "Completed");
        newVisitor.filterToDoList("Completed");
        assertFalse(newAdmin.getToDoList().getTaskWithName("test1").getVisibility());
        assertFalse(newAdmin.getToDoList().getTaskWithName("test2").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test3").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test4").getVisibility());
        assertFalse(newAdmin.getToDoList().getTaskWithName("test5").getVisibility());
        assertEquals(5, newAdmin.getToDoList().getTaskList().size());

        newAdmin.filterToDoList("reset");
        newAdmin.filterToDoList(3);
        assertTrue(newAdmin.getToDoList().getTaskWithName("test1").getVisibility());
        assertFalse(newAdmin.getToDoList().getTaskWithName("test2").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test3").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test4").getVisibility());
        assertFalse(newAdmin.getToDoList().getTaskWithName("test5").getVisibility());
    }

    @Test
    public void testAddTask() {
        newAdmin.addTask("test1", new Date(2021, 10, 15),
                1, 1);
        assertEquals(1, newAdmin.getToDoList().getLength());
        assertEquals(2021, newAdmin.getToDoList().getTaskWithName("test1").getDateOfCompletion().getYear());
        assertEquals(10,
                newAdmin.getToDoList().getTaskWithName("test1").getDateOfCompletion().getMonthValue());
        assertEquals(15,
                newAdmin.getToDoList().getTaskWithName("test1").getDateOfCompletion().getDayOfMonth());

        assertEquals(2021, newAdmin.getToDoList().getTaskWithName("test1").getDateOfCreation().getYear());
        assertEquals(11,
                newAdmin.getToDoList().getTaskWithName("test1").getDateOfCreation().getMonthValue());
        assertEquals(24,
                newAdmin.getToDoList().getTaskWithName("test1").getDateOfCreation().getDayOfMonth());

        assertEquals(1,
                newAdmin.getToDoList().getTaskWithName("test1").getUrgency());
        assertEquals("Not started",
                newDeveloper.getToDoList().getTaskWithName("test1").getStatus());
        assertEquals(1,
                newAdmin.getToDoList().getTaskWithName("test1").getHoursRequiredForCompletion());
//        assertEquals(LocalDate.now(), newAdmin.getToDoList().getTaskWithName("test1").getDateOfCreation());


        newDeveloper.addTask("test2", new Date(2022, 10, 23),
                2, 5);
        assertEquals("test1", newAdmin.getToDoList().getTaskList().get(0).getName());
        assertEquals(2, newDeveloper.getToDoList().getLength());
        assertEquals(2022, newAdmin.getToDoList().getTaskWithName("test2").getDateOfCompletion().getYear());
        assertEquals(10,
                newAdmin.getToDoList().getTaskWithName("test2").getDateOfCompletion().getMonthValue());
        assertEquals(23,
                newAdmin.getToDoList().getTaskWithName("test2").getDateOfCompletion().getDayOfMonth());
        assertEquals("Not started",
                newAdmin.getToDoList().getTaskWithName("test2").getStatus());
        assertEquals(2,
                newAdmin.getToDoList().getTaskWithName("test2").getUrgency());
        assertEquals(5,
                newAdmin.getToDoList().getTaskWithName("test2").getHoursRequiredForCompletion());

        assertFalse(newAdmin.addTask("test1", new Date(2021, 10, 15),
                1, 1));

        assertNull(newAdmin.getToDoList().getTaskWithName("task10"));
    }

    @Test
    public void testDeveloperTakeOnTask() {
        newAdmin.addTask("test1", new Date(2021, 10, 15),
                1, 1);
        newDeveloper.addTask("test2", new Date(2022, 10, 23),
                2, 5);
        newDeveloper.addTask("test3", new Date(2021, 12, 13),
                3, 3);

        assertEquals("developer1", newAdmin.getListOfDevelopers().get(0));

        assertEquals("none", newAdmin.getToDoList().getTaskWithName("test1").getAssignedDeveloper());
        assertEquals("none", newAdmin.getToDoList().getTaskWithName("test2").getAssignedDeveloper());
        assertEquals("none", newAdmin.getToDoList().getTaskWithName("test3").getAssignedDeveloper());
        assertEquals("none", newDeveloper.getToDoList().getTaskWithName("test1").getAssignedDeveloper());
        assertEquals("none", newDeveloper.getToDoList().getTaskWithName("test2").getAssignedDeveloper());
        assertEquals("none", newDeveloper.getToDoList().getTaskWithName("test3").getAssignedDeveloper());

        newDeveloper.takeOnTask("testtestblblblbl");

        assertEquals("none", newAdmin.getToDoList().getTaskWithName("test1").getAssignedDeveloper());
        assertEquals("none", newAdmin.getToDoList().getTaskWithName("test2").getAssignedDeveloper());
        assertEquals("none", newAdmin.getToDoList().getTaskWithName("test3").getAssignedDeveloper());
        assertEquals("none", newDeveloper.getToDoList().getTaskWithName("test1").getAssignedDeveloper());
        assertEquals("none", newDeveloper.getToDoList().getTaskWithName("test2").getAssignedDeveloper());
        assertEquals("none", newDeveloper.getToDoList().getTaskWithName("test3").getAssignedDeveloper());

        newDeveloper.takeOnTask("test1");

        assertEquals("developer1", newAdmin.getToDoList().getTaskWithName("test1").getAssignedDeveloper());
        assertEquals("none", newDeveloper.getToDoList().getTaskWithName("test2").getAssignedDeveloper());
        assertEquals("none", newDeveloper.getToDoList().getTaskWithName("test3").getAssignedDeveloper());

        newDeveloper.takeOnTask("test3");
        assertEquals("developer1", newDeveloper.getToDoList().getTaskWithName("test3").getAssignedDeveloper());
    }

    @Test
    public void testDeveloperChangeStatus() {
        newAdmin.addTask("test1", new Date(2021, 10, 15),
                1, 1);
        newDeveloper.addTask("test2", new Date(2022, 10, 23),
                2, 5);
        newDeveloper.addTask("test3", new Date(2021, 12, 13),
                3, 3);
        newDeveloper.takeOnTask("test3");

        newDeveloper.changeStatus("test3", "In progress");
        assertEquals("In progress", newAdmin.getToDoList().getTaskWithName("test3").getStatus());

        newDeveloper.changeStatus("test2", "Completed");
        assertEquals("Not started", newAdmin.getToDoList().getTaskWithName("test2").getStatus());

        newDeveloper.takeOnTask("test1");
        newDeveloper.changeStatus("test1", "Completed");

        assertEquals("Completed", newAdmin.getToDoList().getTaskWithName("test1").getStatus());

        assertFalse(newDeveloper.changeStatus("test7864268", "Completed"));
    }

    @Test
    public void testDeveloperFilterOwnTask() {
        newAdmin.addTask("test1", new Date(2021, 10, 15),
                1, 1);
        newDeveloper.addTask("test2", new Date(2022, 10, 23),
                2, 5);
        newDeveloper.addTask("test3", new Date(2021, 12, 13),
                3, 3);
        newAdmin.addTask("test4", new Date(2021, 10, 15),
                1, 1);
        newAdmin.addTask("test5", new Date(2021, 10, 15),
                1, 1);

        newDeveloper.takeOnTask("test2");
        newDeveloper.takeOnTask("test3");
        newDeveloper.takeOnTask("test5");

        newDeveloper.filterOwnTask();

        assertFalse(newAdmin.getToDoList().getTaskWithName("test1").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test2").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test3").getVisibility());
        assertFalse(newAdmin.getToDoList().getTaskWithName("test4").getVisibility());
        assertTrue(newAdmin.getToDoList().getTaskWithName("test5").getVisibility());
    }

    @Test
    public void testAdminModifyTask() {
        newAdmin.addTask("test1", new Date(2021, 10, 15),
                1, 3);
        newDeveloper.addTask("test2", new Date(2022, 10, 23),
                2, 5);
        newDeveloper.addTask("test3", new Date(2021, 12, 13),
                3, 3);
        newAdmin.addTask("test4", new Date(2021, 10, 19),
                3, 5);
        newAdmin.addTask("test5", new Date(2021, 11, 15),
                1, 7);


        newAdmin.modifyTask("test3", "no idea", "developer1");
        assertEquals("test3", newAdmin.getToDoList().getTaskWithName("test3").getName());

        newAdmin.modifyTask("test3", "no idea", "developer1");
        assertEquals("test3", newAdmin.getToDoList().getTaskWithName("test3").getName());

        newAdmin.modifyTask("test2", "name", "newTest2");
        assertEquals("newTest2", newAdmin.getToDoList().getTaskWithName("newTest2").getName());

        newAdmin.modifyTask("test5", "status", "In progress");
        assertEquals("In progress", newAdmin.getToDoList().getTaskWithName("test5").getStatus());

        newAdmin.modifyTask("test3", "developer", "developer1");
        assertEquals("developer1", newAdmin.getToDoList().getTaskWithName("test3").getAssignedDeveloper());

        newAdmin.modifyTask("test3", "developer", "developer123");
        assertEquals("developer1", newAdmin.getToDoList().getTaskWithName("test3").getAssignedDeveloper());

        newAdmin.modifyTask("test1", "delete", null);
        assertEquals(4, newAdmin.getToDoList().getLength());

        newAdmin.modifyTask("test4", "urgency", 2);
        assertEquals(2, newAdmin.getToDoList().getTaskWithName("test4").getUrgency());

        newAdmin.modifyTask("test4", "hours required for completion", 6);
        assertEquals(6, newAdmin.getToDoList().getTaskWithName("test4").getHoursRequiredForCompletion());

        newAdmin.modifyTask("test4", "hours required for", 89);
        assertEquals(6, newAdmin.getToDoList().getTaskWithName("test4").getHoursRequiredForCompletion());

        newAdmin.modifyTask("test3", "date of completion",
                2021, 12, 23);
        assertEquals(2021,
                newAdmin.getToDoList().getTaskWithName("test3").getDateOfCompletion().getYear());
        assertEquals(12,
                newAdmin.getToDoList().getTaskWithName("test3").getDateOfCompletion().getMonthValue());
        assertEquals(23,
                newAdmin.getToDoList().getTaskWithName("test3").getDateOfCompletion().getDayOfMonth());

        newAdmin.modifyTask("test3", "not date of completion",
                2021, 12, 23);
        assertEquals(2021,
                newAdmin.getToDoList().getTaskWithName("test3").getDateOfCompletion().getYear());
        assertEquals(12,
                newAdmin.getToDoList().getTaskWithName("test3").getDateOfCompletion().getMonthValue());
        assertEquals(23,
                newAdmin.getToDoList().getTaskWithName("test3").getDateOfCompletion().getDayOfMonth());
    }
}
