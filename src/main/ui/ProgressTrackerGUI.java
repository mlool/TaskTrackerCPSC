package ui;

import model.EventLog;
import model.exceptions.UserAlreadyExistException;
import model.exceptions.UserDoesNotExistException;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.tasks.Date;
import model.tasks.ListOfTasks;
import model.tasks.Task;
import model.users.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

// progress tracker app with graphical user interface
public class ProgressTrackerGUI {
    private static final String JSON_STORE = "./data/listOfUsers.json";

    private ListOfData listOfData;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // constructs new progress tracker app with graphics
    public ProgressTrackerGUI() {
        listOfData = new ListOfData(new ListOfAdmins(), new ListOfDevelopers(), new ListOfVisitors(),
                new ListOfTasks());
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        new HomeScreen();
    }

    // Home screen
    private class HomeScreen extends JFrame implements ActionListener {

        // constructs new progress tracker
        public HomeScreen() {
            super("Home Screen");

//            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(500, 500));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));

            setLayout(new GridLayout(2,1));

            try {
                BufferedImage iconImage = ImageIO.read(new File("data/icon.jpg"));
                BufferedImage resizedIconImage = new BufferedImage(300, 200, BufferedImage.TYPE_INT_RGB);

                Graphics2D graphics2D = resizedIconImage.createGraphics();
                graphics2D.drawImage(iconImage, 0, 0, 300, 200, null);
                graphics2D.dispose();

                add(new JLabel(new ImageIcon(resizedIconImage), JLabel.CENTER));
            } catch (IOException e) {
                System.out.println("cannot happen");
            }

            add(addButtonPanel());

            pack();
            setVisible(true);
        }

        // EFFECT: returns the button panel
        private JPanel addButtonPanel() {
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(3,2));
            buttonPanel.add(new JButton(new LogIn()));
            buttonPanel.add(new JButton(new SignUp()));
            buttonPanel.add(new JButton(new SaveData()));
            buttonPanel.add(new JButton(new LoadData()));
            buttonPanel.add(new JButton(new QuitApplication()));
            return buttonPanel;
        }

        // Action performed by sign up button
        private class SignUp extends AbstractAction {

            // Constructs new sign up action
            SignUp() {
                super("Sign Up");
            }

            // EFFECT: redirect the user to sign up page
            @Override
            public void actionPerformed(ActionEvent evt) {
                new SignUpScreen();
                setVisible(false);
            }
        }

        // Action performed by save button
        private class SaveData extends AbstractAction {

            // Constructs new save data action
            SaveData() {
                super("Save Data");
            }

            // MODIFIES: this
            // EFFECT: saves current data
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(listOfData);
                    jsonWriter.close();
                    new SuccessSaveScreen();
                    setVisible(false);
                } catch (FileNotFoundException e) {
                    new FailSaveScreen();
                    setVisible(false);
                }
            }
        }

        // action performed by load data button
        private class LoadData extends AbstractAction {

            // constructs new load data action
            LoadData() {
                super("Load Data");
            }

            // MODIFIES: this
            // EFFECT: loads the data
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    listOfData = jsonReader.read();
                    new SuccessLoadScreen();
                    setVisible(false);
                } catch (IOException | UserAlreadyExistException e) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                    new FailLoadScreen();
                    setVisible(false);
                }
            }
        }

        // action performed by log in button
        private class LogIn extends AbstractAction {

            // constructs new log in action
            LogIn() {
                super("Log In");
            }

            // EFFECT: redirects the user to log in page
            @Override
            public void actionPerformed(ActionEvent evt) {
                new LogInScreen();
                setVisible(false);
            }
        }

        private class QuitApplication extends AbstractAction {

            // constructs new log in action
            QuitApplication() {
                super("Quit");
            }

            // EFFECT: redirects the user to log in page
            @Override
            public void actionPerformed(ActionEvent evt) {
                Iterator<Event> eventIterator = EventLog.getInstance().iterator();
                while (eventIterator.hasNext()) {
                    System.out.println(eventIterator.next().toString());
                }
                System.exit(0);
                setVisible(false);
            }
        }

        // EFFECT: does nothing
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // Sign Up screen
    private class SignUpScreen extends JFrame implements ActionListener {

        // constructs new progress tracker
        public SignUpScreen() {
            super("Sign Up Screen");

            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            add(addButtonPanel());

            pack();
            setVisible(true);
        }

        // EFFECT: returns a button panel
        private JPanel addButtonPanel() {
            JPanel buttonPanel = new JPanel();

            buttonPanel.setLayout(new GridLayout(5,1));
            buttonPanel.add(new JButton(new GoBack()));
            buttonPanel.add(new Label("Which type of user are you"));
            buttonPanel.add(new JButton(new AddAdmin()));
            buttonPanel.add(new JButton(new AddDeveloper()));
            buttonPanel.add(new JButton(new AddVisitor()));
            return buttonPanel;
        }

        // go back to home
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("go back");
            }

            // EFFECT: opens a new home screen window and turn this one invisible
            @Override
            public void actionPerformed(ActionEvent evt) {
                new HomeScreen();
                setVisible(false);
            }
        }

        // add admin
        private class AddAdmin extends AbstractAction {

            // constructs an add admin action
            AddAdmin() {
                super("Admin");
            }

            // EFFECT: opens a sign-up admin screen
            @Override
            public void actionPerformed(ActionEvent evt) {
                new SignUpAdminScreen();
                setVisible(false);
            }
        }

        // add developer
        private class AddDeveloper extends AbstractAction {

            // constructs an add developer action
            AddDeveloper() {
                super("Developer");
            }

            // EFFECT: opens a sign-up developer screen
            @Override
            public void actionPerformed(ActionEvent evt) {
                new SignUpDeveloperScreen();
                setVisible(false);
            }
        }

        // add visitor
        private class AddVisitor extends AbstractAction {

            // constructs an add visitor action
            AddVisitor() {
                super("Visitor");
            }

            // EFFECT: opens a sign-up visitor screen
            @Override
            public void actionPerformed(ActionEvent evt) {
                new SignUpVisitorScreen();
                setVisible(false);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {}
    }

    // Sign Up Screen for admin
    private class SignUpAdminScreen extends JFrame implements ActionListener {

        JTextField userNameField;
        JPasswordField passwordField;
        JPasswordField passwordFieldReEnter;

        // constructs an admin sign up screen
        public SignUpAdminScreen() {
            super("Sign Up Admin Screen");

            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            userNameField = new JTextField(5);
            passwordField = new JPasswordField(5);
            passwordFieldReEnter = new JPasswordField(5);

            JButton btn = new JButton("Sign Up");
            btn.setActionCommand("signUp");
            btn.addActionListener(this);

            setLayout(new GridLayout(6,1));
            add(new JButton(new GoBack()));
            add(new Label("What is the user name"));
            add(userNameField);
            add(new Label("What is the password"));
            add(passwordField);
            add(new Label("re-enter the password"));
            add(passwordFieldReEnter);
            add(btn);

            pack();
            setVisible(true);
        }

        // go back to sign up screen
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("go back");
            }

            // EFFECT: go back to the signup screen
            @Override
            public void actionPerformed(ActionEvent evt) {
                new SignUpScreen();
                setVisible(false);
            }
        }

        // MODIFIES: this
        // EFFECT: signs up the admin with given input
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals("signUp")) {
                String inputUserID = userNameField.getText();
                String inputPassword = passwordField.getText();
                String inputPasswordAgain = passwordFieldReEnter.getText();
                if (inputPassword.equals(inputPasswordAgain)) {
                    Admin newAdmin = new Admin(inputUserID, inputPassword, listOfData.getToDoList(),
                            listOfData.getDeveloperList().namesOnly());
                    try {
                        listOfData.getAdminList().add(newAdmin);
                        successAdding();
                    } catch (UserAlreadyExistException e) {
                        failAdding();
                    }
                } else {
                    wrongPassword();
                }

            }
        }

        // EFFECT: go to the success adding user screen
        private void successAdding() {
            new SuccessAddUserScreen();
            setVisible(false);
        }

        // EFFECT: go to the fail adding user screen
        private void failAdding() {
            new FailAddUserScreen();
            setVisible(false);
        }

        private void wrongPassword() {
            new FailAddUserScreen();
            setVisible(false);
        }
    }

    // Sign Up Screen for developer
    private class SignUpDeveloperScreen extends JFrame implements ActionListener {

        JTextField userNameField;
        JTextField passwordField;

        // constructs developer screen
        public SignUpDeveloperScreen() {
            super("Sign Up Developer Screen");

            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            userNameField = new JTextField(5);
            passwordField = new JTextField(5);
            JButton btn = new JButton("Sign Up");
            btn.setActionCommand("signUp");
            btn.addActionListener(this);

            setLayout(new GridLayout(6,1));
            add(new JButton(new GoBack()));
            add(new Label("What is the user name"));
            add(userNameField);
            add(new Label("What is the password"));
            add(passwordField);
            add(btn);

            pack();
            setVisible(true);
        }

        // go back to sign up screen
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("go back");
            }

            // EFFECT: go back to the signup screen
            @Override
            public void actionPerformed(ActionEvent evt) {
                new SignUpScreen();
                setVisible(false);
            }
        }

        // MODIFIES: this
        // EFFECT: signs up the developer with given input
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals("signUp")) {
                String inputUserID = userNameField.getText();
                String inputPassword = passwordField.getText();
                Developer newDeveloper = new Developer(inputUserID, inputPassword, listOfData.getToDoList(),
                        listOfData.getDeveloperList().namesOnly());
                try {
                    listOfData.getDeveloperList().add(newDeveloper);
                    listOfData.getAdminList().addDeveloper(newDeveloper);
                    successAdding();
                } catch (UserAlreadyExistException e) {
                    failAdding();
                }
            }
        }

        // EFFECT: go to the success adding user screen
        private void successAdding() {
            new SuccessAddUserScreen();
            setVisible(false);
        }

        // EFFECT: go to the fail adding user screen
        private void failAdding() {
            new FailAddUserScreen();
            setVisible(false);
        }
    }

    // Sign Up Screen for visitor
    private class SignUpVisitorScreen extends JFrame implements ActionListener {

        JTextField userNameField;
        JTextField passwordField;

        // constructs a new visitor screen
        public SignUpVisitorScreen() {
            super("Sign Up Visitor Screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            userNameField = new JTextField(5);
            passwordField = new JTextField(5);
            JButton btn = new JButton("Sign Up");
            btn.setActionCommand("signUp");
            btn.addActionListener(this);

            setLayout(new GridLayout(6,1));
            add(new JButton(new GoBack()));
            add(new Label("What is the user name"));
            add(userNameField);
            add(new Label("What is the password"));
            add(passwordField);
            add(btn);

            pack();
            setVisible(true);
        }

        // go back to the signup screen
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("go back");
            }

            // EFFECT: go back to the signup screen and set the current page's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new SignUpScreen();
                setVisible(false);
            }
        }

        // MODIFIES: this
        // EFFECT: signs up the visitor with given input
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals("signUp")) {
                String inputUserID = userNameField.getText();
                String inputPassword = passwordField.getText();
                Visitor newVisitor = new Visitor(inputUserID, inputPassword, listOfData.getToDoList(),
                        listOfData.getDeveloperList().namesOnly());
                try {
                    listOfData.getVisitorList().add(newVisitor);
                    successAdding();
                } catch (UserAlreadyExistException e) {
                    failAdding();
                }
            }
        }

        // EFFECT: go to the success adding user screen
        private void successAdding() {
            new SuccessAddUserScreen();
            setVisible(false);
        }

        // EFFECT: go to the fail adding user screen
        private void failAdding() {
            new FailAddUserScreen();
            setVisible(false);
        }
    }

    // Success screen for signing up user
    private class SuccessAddUserScreen extends JFrame implements ActionListener {

        // constructs a new screen for add user screen
        public SuccessAddUserScreen() {
            super("Success Screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            setLayout(new GridLayout(2,1));
            add(new JButton(new GoBack()));
            add(new Label("User successfully created"));

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs new go back action
            GoBack() {
                super("Okay");
            }

            // EFFECT: go back to the home screen
            @Override
            public void actionPerformed(ActionEvent evt) {
                new HomeScreen();
                setVisible(false);
            }
        }

        // EFFECT: nothing
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // Fail screen for signing up user
    private class FailAddUserScreen extends JFrame implements ActionListener {

        // constructs a new fail user screen
        public FailAddUserScreen() {
            super("Fail Screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            setLayout(new GridLayout(2,1));
            add(new JButton(new GoBack()));
            add(new Label("Unable to create user"));

            pack();
            setVisible(true);
        }

        // go back action
        private class GoBack extends AbstractAction {

            // constructs new go back action
            GoBack() {
                super("Okay");
            }

            // EFFECT: go back to the signup screen
            @Override
            public void actionPerformed(ActionEvent evt) {
                new SignUpScreen();
                setVisible(false);
            }
        }

        // EFFECT: do nothing
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    private class NotMatchPasswordScreen extends JFrame implements ActionListener {

        // constructs a new fail user screen
        public NotMatchPasswordScreen() {
            super("Fail Screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            setLayout(new GridLayout(2,1));
            add(new JButton(new GoBack()));
            add(new Label("Unable to create user"));

            pack();
            setVisible(true);
        }

        // go back action
        private class GoBack extends AbstractAction {

            // constructs new go back action
            GoBack() {
                super("Okay");
            }

            // EFFECT: go back to the signup screen
            @Override
            public void actionPerformed(ActionEvent evt) {
                new SignUpScreen();
                setVisible(false);
            }
        }

        // EFFECT: do nothing
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // Success screen for save screen
    private class SuccessSaveScreen extends JFrame implements ActionListener {

        // constructs a success save screen
        public SuccessSaveScreen() {
            super("Success Screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            setLayout(new GridLayout(2,1));
            add(new JButton(new GoBack()));
            add(new Label("Data successfully saved"));

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("Okay");
            }

            // MODIFIES: this
            // EFFECT: go back to the home screen
            @Override
            public void actionPerformed(ActionEvent evt) {
                new HomeScreen();
                setVisible(false);
            }
        }

        // EFFECT: do nothing
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // Fail screen for save screen
    private class FailSaveScreen extends JFrame implements ActionListener {

        // constructs new fail save screen
        public FailSaveScreen() {
            super("Fail Screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            setLayout(new GridLayout(2,1));
            add(new JButton(new GoBack()));
            add(new Label("Data not saved"));

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("Okay");
            }

            // MODIFIES: this
            // EFFECT: go back to the home screen and change the current screen visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new HomeScreen();
                setVisible(false);
            }
        }

        // EFFECT: nothing
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // Success screen for load screen
    private class SuccessLoadScreen extends JFrame implements ActionListener {

        // constructs a new success load screen
        public SuccessLoadScreen() {
            super("Success Screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            setLayout(new GridLayout(2,1));
            add(new JButton(new GoBack()));
            add(new Label("Data successfully loaded"));

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            public GoBack() {
                super("Okay");
            }

            // MODIFIES: this
            // EFFECT: go back to the hme screen
            @Override
            public void actionPerformed(ActionEvent evt) {
                new HomeScreen();
                setVisible(false);
            }
        }

        // EFFECT: do nothing
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // Fail screen for load screen
    private class FailLoadScreen extends JFrame implements ActionListener {

        // constructs a new fail load screen
        public FailLoadScreen() {
            super("Fail Screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            setLayout(new GridLayout(2,1));
            add(new JButton(new GoBack()));
            add(new Label("Data not loaded"));

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("Okay");
            }

            // MODIFIES: this
            // EFFECT: opens a new home screen and sets the current screen's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new HomeScreen();
                setVisible(false);
            }
        }

        // EFFECT: do nothing
        @Override
        public void actionPerformed(ActionEvent e) {}
    }

    // Log in screen
    private class LogInScreen extends JFrame implements ActionListener {

        JTextField userNameField;
        JTextField passwordField;

        // constructs a new log in screen
        public LogInScreen() {
            super("Log In Screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            userNameField = new JTextField(5);
            passwordField = new JTextField(5);
            JButton btn = new JButton("Log In");
            btn.setActionCommand("logIn");
            btn.addActionListener(this);

            setLayout(new GridLayout(6,1));
            add(new JButton(new GoBack()));
            add(new Label("What is the user name"));
            add(userNameField);
            add(new Label("What is the password"));
            add(passwordField);
            add(btn);

            pack();
            setVisible(true);
        }


        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("go back");
            }

            // MODIFIES: this
            // EFFECT: opens a new home screen and sets the current screen's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new HomeScreen();
                setVisible(false);
            }
        }

        // EFFECT: finds the status of input user as well as log them to their respective screens
        @Override
        @SuppressWarnings("methodlength")
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals("logIn")) {
                String inputUserName = userNameField.getText();
                String inputPassword = passwordField.getText();

                String userStatus = checkCredentials(inputUserName, inputPassword);
                try {
                    switch (userStatus) {
                        case "admin":
                            new AdminPage(listOfData.getAdminList().getAdmin(inputUserName));
                            setVisible(false);
                            break;
                        case "developer":
                            new DeveloperPage(listOfData.getDeveloperList().getDeveloper(inputUserName));
                            setVisible(false);
                            break;
                        case "visitor":
                            new VisitorPage(listOfData.getVisitorList().getVisitor(inputUserName));
                            setVisible(false);
                            break;
                        default:
                            new FailLogIn("Incorrect password");
                            setVisible(false);
                            break;
                    }
                } catch (UserDoesNotExistException e) {
                    new FailLogIn("User not found");
                    setVisible(false);
                }
            }
        }

        // EFFECT: returns the status of the inputted user
        private String checkCredentials(String userID, String inputPassword) {
            if (listOfData.getAdminList().checkForAdmin(userID, inputPassword)) {
                return "admin";
            } else if (listOfData.getDeveloperList().checkForDeveloper(userID, inputPassword)) {
                return "developer";
            } else if (listOfData.getVisitorList().checkForVisitor(userID, inputPassword)) {
                return "visitor";
            } else {
                return "not found";
            }
        }
    }

    // Fail screen for log in screen
    private class FailLogIn extends JFrame implements ActionListener {

        // constructs a new fail log in screen
        public FailLogIn(String failReason) {
            super("Fail Screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            setLayout(new GridLayout(2,1));
            if (failReason.equals("User not found")) {
                add(new JButton(new GoBack()));
                add(new Label("User does not exist"));
            } else {
                add(new JButton(new GoBack()));
                add(new Label("Incorrect password"));
            }

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("Okay");
            }

            // MODIFIES: this
            // EFFECT: opens a new home screen and sets the current screen's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new HomeScreen();
                setVisible(false);
            }
        }

        // EFFECT: do something
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // Everything (ish) Admin
    // Admin screen
    private class AdminPage extends JFrame implements ActionListener {

        Admin currentAdmin;

        // constructs a new admin page for the given admin
        public AdminPage(Admin admin) {
            super("Admin Home Screen");

            currentAdmin = admin;

            setPreferredSize(new Dimension(1400, 1400));

            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));

            //setLayout(new BoxLayout((JPanel) getContentPane(), BoxLayout.PAGE_AXIS));

            add(addMenuBar());
            add(printedTaskList());

            pack();
            setVisible(true);
        }

        // EFFECT: creates menu bar
        private JMenuBar addMenuBar() {
            JMenuBar menuBar = new JMenuBar();
            menuBar.setSize(1400, 50);
            menuBar.add(addFilterMenu());
            menuBar.add(new JMenuItem(new AddTask()));
            menuBar.add(new JMenuItem(new ModifyTask()));
            menuBar.add(new JMenuItem(new Setting()));

            return menuBar;
        }

        // EFFECT: creates a menu for filter task page
        private JMenu addFilterMenu() {
            JMenu filterMenu = new JMenu("filter tasks");
            filterMenu.add(new JMenuItem(new Reset()));
            filterMenu.add(new JMenuItem(new Overdue()));
            filterMenu.add(new JMenuItem(new NotStarted()));
            filterMenu.add(new JMenuItem(new InProgress()));
            filterMenu.add(new JMenuItem(new Completed()));
            filterMenu.add(new JMenuItem(new NotAssigned()));
            filterMenu.add(addUrgencyMenu());

            return filterMenu;
        }

        // EFFECT: creates menu for the urgency for menu
        private JMenu addUrgencyMenu() {
            JMenu urgencyMenu = new JMenu("urgency");
            urgencyMenu.add(new JMenuItem(new FilterUrgency(0)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(1)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(2)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(3)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(4)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(5)));
            return urgencyMenu;
        }

        // add task action
        private class AddTask extends AbstractAction {

            // constructs an add task action
            AddTask() {
                super("add task");
            }

            // MODIFIES: this
            // EFFECT: open an add task screen for the provided admin and turn this page's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AddTaskAdminScreen(currentAdmin);
                setVisible(false);
            }
        }

        // modify task action
        private class ModifyTask extends AbstractAction {

            // constructs new modify task action
            ModifyTask() {
                super("modify tasks");
            }

            // MODIFIES: this
            // EFFECT: opens the corresponding modify task screen for admin, turns current page's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new ModifyTaskScreenAdmin(currentAdmin);
                setVisible(false);
            }
        }

        // setting screen
        private class Setting extends AbstractAction {

            // constructs new setting page
            Setting() {
                super("Settings");
            }

            // MODIFIES: this
            // EFFECT: opens the corresponding settings screen for admin, turns current page's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new SettingAdminPage(currentAdmin);
                setVisible(false);
            }
        }

        // reset action
        private class Reset extends AbstractAction {

            // constructs new reset action
            Reset() {
                super("reset");
            }

            // MODIFIES: this
            // EFFECT: opens the corresponding admin screen, turns current page's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentAdmin.filterToDoList("reset");
                new AdminPage(currentAdmin);
                setVisible(false);
            }
        }

        // action to filter task not yet started
        private class NotStarted extends AbstractAction {

            // constructs the filter action
            NotStarted() {
                super("not started");
            }

            // MODIFIES: this
            // EFFECT: filters all not started tasks, and turns current page's visibility to false, and refreshes
            //         the admin page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentAdmin.filterToDoList("Not started");
                new AdminPage(currentAdmin);
                setVisible(false);
            }
        }

        // action to filter tasks already in progress
        private class InProgress extends AbstractAction {

            // constructs the filter action
            InProgress() {
                super("in progress");
            }

            // MODIFIES: this
            // EFFECT: filters all in progress tasks, and turns current page's visibility to false, and refreshes
            //         the admin page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentAdmin.filterToDoList("In progress");
                new AdminPage(currentAdmin);
                setVisible(false);
            }
        }

        // action to filter tasks already completed
        private class Completed extends AbstractAction {

            // constructs the filter action
            Completed() {
                super("completed");
            }

            // MODIFIES: this
            // EFFECT: filters all completed tasks, and turns current page's visibility to false, and refreshes
            //         the admin page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentAdmin.filterToDoList("Completed");
                new AdminPage(currentAdmin);
                setVisible(false);
            }
        }

        // action to filter tasks overdue
        private class Overdue extends AbstractAction {

            // constructs the filter action
            Overdue() {
                super("overdue");
            }

            // MODIFIES: this
            // EFFECT: filters overdue tasks, and turns current page's visibility to false, and refreshes
            //         the admin page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentAdmin.filterToDoList("overdue");
                new AdminPage(currentAdmin);
                setVisible(false);
            }
        }

        // action to filter tasks not assigned to any developer
        private class NotAssigned extends AbstractAction {

            // constructs the filter action
            NotAssigned() {
                super("open to development");
            }

            // MODIFIES: this
            // EFFECT: filters tasks not yet assigned, and turns current page's visibility to false, and refreshes
            //         the admin page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentAdmin.filterToDoList("not assigned yet");
                new AdminPage(currentAdmin);
                setVisible(false);
            }
        }

        // action to filter tasks based on urgency
        private class FilterUrgency extends AbstractAction {

            int urgencyLevel;

            // constructs the filter action
            FilterUrgency(int urgencyFiltered) {
                super(Integer.toString(urgencyFiltered));
                urgencyLevel = urgencyFiltered;
            }

            // MODIFIES: this
            // EFFECT: filters tasks on urgency, and turns current page's visibility to false, and refreshes
            //         the admin page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentAdmin.filterToDoList(urgencyLevel);
                new AdminPage(currentAdmin);
                setVisible(false);
            }
        }

        // EFFECT: do nothing
        @Override
        public void actionPerformed(ActionEvent e) {}
    }

    // Adding task screen for admin
    private class AddTaskAdminScreen extends JFrame implements ActionListener {

        Admin currentAdmin;

        JTextField name;
        JTextField dueDateYear;
        JTextField dueDateMonth;
        JTextField dueDateDay;
        JTextField urgency;
        JTextField hours;

        // constructs the screen for adding tasks as an admin
        @SuppressWarnings("methodlength")
        public AddTaskAdminScreen(Admin admin) {
            super("Add task screen");

            currentAdmin = admin;

            setPreferredSize(new Dimension(1400, 1400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            name = new JTextField(5);
            dueDateYear = new JTextField(5);
            dueDateMonth = new JTextField(5);
            dueDateDay = new JTextField(5);
            urgency = new JTextField(5);
            hours = new JTextField(5);

            JButton btn = new JButton("Add task");
            btn.setActionCommand("addTaskList");
            btn.addActionListener(this);

            setLayout(new GridLayout(6,4));

            add(new JButton(new GoBack()));
            add(new Label(""));
            add(new Label(""));
            add(new Label(""));

            add(new Label("Name"));
            add(name);
            add(new Label(""));
            add(new Label(""));

            add(new Label("Due date"));
            add(dueDateYear);
            add(dueDateMonth);
            add(dueDateDay);

            add(new Label("Urgency"));
            add(urgency);
            add(new Label(""));
            add(new Label(""));

            add(new Label("Hours required"));
            add(hours);
            add(new Label(""));
            add(new Label(""));

            add(btn);

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs the go back action
            GoBack() {
                super("Go back");
            }

            // MODIFIES: this
            // EFFECT: go back to the admin page of the specified user and set the current visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AdminPage(currentAdmin);
                setVisible(false);
            }
        }

        // MODIFIES: this
        // EFFECT: after pressing the add task list button, creates new task containing all the given inputs,
        //         then opens the success screen and sets the current screen's visibility to false;
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals("addTaskList")) {
                String inoutName = name.getText();
                int inputYear = Integer.parseInt(dueDateYear.getText());
                int inputMonth = Integer.parseInt(dueDateMonth.getText());
                int inputDay = Integer.parseInt(dueDateDay.getText());
                int inputUrgency = Integer.parseInt(urgency.getText());
                int inputHours = Integer.parseInt(hours.getText());

                Date inputDate = new Date(inputYear, inputMonth, inputDay);

                currentAdmin.addTask(inoutName, inputDate, inputUrgency, inputHours);
                new SuccessAddTaskScreenAdmin(currentAdmin);
                setVisible(false);
            }
        }
    }

    // Success screen when admin adds a class
    private class SuccessAddTaskScreenAdmin extends JFrame implements ActionListener {

        Admin currentAdmin;

        // constructs a new success add task screen for the given admin
        public SuccessAddTaskScreenAdmin(Admin admin) {
            super("Success Screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            currentAdmin = admin;

            setLayout(new GridLayout(2,1));
            add(new JButton(new GoBack()));
            add(new Label("Task successfully added"));

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("Okay");
            }

            // MODIFIES: this
            // EFFECT: go back to the admin page for the provided user
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AdminPage(currentAdmin);
                setVisible(false);
            }
        }

        // EFFECT: do nothing
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // Modify screen for admin
    private class ModifyTaskScreenAdmin extends JFrame implements ActionListener {

        Admin currentAdmin;

        // constructs new screen to modifying tasks
        public ModifyTaskScreenAdmin(Admin admin) {
            super("Modify Task Screen");
            setPreferredSize(new Dimension(1400, 1400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            currentAdmin = admin;

            setLayout(new BoxLayout((JPanel) getContentPane(), BoxLayout.PAGE_AXIS));
            add(new JButton(new GoBack()));
            add(printedModifyingTaskList());

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs the go back action
            GoBack() {
                super("go back");
            }

            // MODIFIES: this
            // EFFECT: go back to the admin page for the given user
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AdminPage(currentAdmin);
                setVisible(false);
            }
        }

        // EFFECT: returns the proper view of the task list with proper editing tools
        @SuppressWarnings("methodlength")
        private JPanel printedModifyingTaskList() {

            JPanel taskListPanel = new JPanel();

            taskListPanel.setLayout(new GridLayout(listOfData.getToDoList().getLength() + 1,8));

            taskListPanel.add(new Label("name "));
            taskListPanel.add(new Label("date of creation "));
            taskListPanel.add(new Label("due date "));
            taskListPanel.add(new Label("urgency "));
            taskListPanel.add(new Label("required hours "));
            taskListPanel.add(new Label("status "));
            taskListPanel.add(new Label("assigned developer "));
            taskListPanel.add(new Label("remove "));

            for (int i = 0; i < listOfData.getToDoList().getLength(); i++) {

                Task task = listOfData.getToDoList().getTaskList().get(i);

                JComboBox<String> statusList = modifyTaskStatus(task);
                statusList.setActionCommand("changeStatus" + i);
                statusList.addActionListener(this);

                JComboBox<Integer> urgencyList = modifyTaskUrgency(task);
                urgencyList.setActionCommand("changeUrgency" + i);
                urgencyList.addActionListener(this);

                JComboBox<String> developerList = modifyTaskDeveloper(task);
                developerList.setActionCommand("changeDeveloper" + i);
                developerList.addActionListener(this);

                JComboBox<Integer> durationList = modifyTaskDuration(task);
                durationList.setActionCommand("changeDuration" + i);
                durationList.addActionListener(this);

                taskListPanel.add(new JButton(new ChangeName(task.getName())));
                taskListPanel.add(new Label(getDateAsString(task.getDateOfCreation())));
                taskListPanel.add(new JButton(new ChangeDateOfCompletion(task.getName(),
                        getDateAsString(task.getDateOfCompletion()))));
                taskListPanel.add(urgencyList);
                taskListPanel.add(durationList);
                taskListPanel.add(statusList);
                taskListPanel.add(developerList);
                taskListPanel.add(new JButton(new RemoveTask(task.getName())));
            }

            return taskListPanel;
        }

        // EFFECT: create the menu for the status of the task
        private JComboBox<String> modifyTaskStatus(Task task) {
            String[] statusChoices = {"Not started", "In progress", "Completed"};
            JComboBox<String> statusList = new JComboBox<>(statusChoices);

            switch (task.getStatus()) {
                case "Not started":
                    statusList.setSelectedIndex(0);
                    break;
                case "In progress":
                    statusList.setSelectedIndex(1);
                    break;
                case "Completed":
                    statusList.setSelectedIndex(2);
                    break;
            }

            return statusList;
        }

        // EFFECT: creates the menu for the urgency of the task
        private JComboBox<Integer> modifyTaskUrgency(Task task) {
            Integer[] statusChoices = {0, 1, 2, 3, 4, 5};
            JComboBox<Integer> statusList = new JComboBox<>(statusChoices);

            statusList.setSelectedIndex(task.getUrgency());
            return statusList;
        }

        // EFFECT: creates the menu for the duration of the task
        private JComboBox<Integer> modifyTaskDuration(Task task) {
            Integer[] durationChoices = new Integer[24];
            for (int i = 0; i < durationChoices.length; i++) {
                durationChoices[i] = i;
            }

            JComboBox<Integer> durationList = new JComboBox<>(durationChoices);

            durationList.setSelectedIndex(task.getHoursRequiredForCompletion());
            return durationList;
        }

        // EFFECT: creates the menu for the developers available to be assigned
        private JComboBox<String> modifyTaskDeveloper(Task task) {
            String[] developerChoice = listOfData.getDeveloperList().namesOnly().toArray(new String[0]);
            String[] developerChoiceList = Arrays.copyOf(developerChoice, developerChoice.length + 1);
            developerChoiceList[developerChoiceList.length - 1] = "none";
            JComboBox<String> developerList = new JComboBox<>(developerChoiceList);

            for (int i = 0; i < developerChoiceList.length; i++) {
                if (task.getAssignedDeveloper().equals(developerChoiceList[i])) {
                    developerList.setSelectedIndex(i);
                }
            }

            return developerList;
        }

        // an action the removes a task
        private class RemoveTask extends AbstractAction {

            String taskName;

            // constructs new remove task action
            RemoveTask(String taskName) {
                super("X");
                this.taskName = taskName;
            }

            // MODIFIES: this
            // EFFECT: deletes the task and refreshes the modify action screen, and sets the old screen visibility
            //         to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentAdmin.modifyTask(taskName, "delete", "");
                new ModifyTaskScreenAdmin(currentAdmin);
                setVisible(false);
            }
        }

        // an action to change the name
        private class ChangeName extends AbstractAction {

            String taskName;

            // constructs action to change the name
            ChangeName(String taskName) {
                super(taskName);
                this.taskName = taskName;
            }

            // MODIFIES: this
            // EFFECT: changes the name of the task and refreshes the modify action screen, and sets the old screen's
            //         visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new ChangeTaskNameScreen(currentAdmin, taskName);
                setVisible(false);
            }
        }

        // an action to change date of completion
        private class ChangeDateOfCompletion extends AbstractAction {

            String taskName;

            // constructs an action to change the date of completion
            ChangeDateOfCompletion(String taskName, String dateAsString) {
                super(dateAsString);
                this.taskName = taskName;
            }

            // MODIFIES: this
            // EFFECT: opens the screen that modifies the date and sets the current screen's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new ChangeDateOfCompletionScreen(currentAdmin, taskName);
                setVisible(false);
            }
        }

        // MODIFIES: this
        // EFFECT: depending on the object changed, change the corresponding value in the task
        @Override
        @SuppressWarnings("methodlength")
        public void actionPerformed(ActionEvent e) {

            for (int i = 0; i < 20; i++) {

                JComboBox cb = (JComboBox)e.getSource();

                if (e.getActionCommand().equals("changeStatus" + i)) {
                    String newStatus = (String)cb.getSelectedItem();
                    Task task = listOfData.getToDoList().getTaskList().get(i);
                    currentAdmin.modifyTask(task.getName(), "status", newStatus);
                }

                if (e.getActionCommand().equals("changeUrgency" + i)) {
                    Task task = listOfData.getToDoList().getTaskList().get(i);
                    currentAdmin.modifyTask(task.getName(), "urgency", cb.getSelectedIndex());
                }

                if (e.getActionCommand().equals("changeDeveloper" + i)) {
                    String newDeveloper = (String)cb.getSelectedItem();
                    Task task = listOfData.getToDoList().getTaskList().get(i);
                    currentAdmin.modifyTask(task.getName(), "developer", newDeveloper);
                }

                if (e.getActionCommand().equals("changeDuration" + i)) {
                    Task task = listOfData.getToDoList().getTaskList().get(i);
                    currentAdmin.modifyTask(task.getName(), "hours required for completion",
                            cb.getSelectedIndex());
                }
            }
        }
    }

    // a screen to change the name of the task
    private class ChangeTaskNameScreen extends JFrame implements ActionListener {

        Admin currentAdmin;
        String taskName;

        JTextField newNameField;

        // constructs a new change task name screen
        public ChangeTaskNameScreen(Admin admin, String task) {
            super("Change task name screen");

            currentAdmin = admin;
            taskName = task;

            setPreferredSize(new Dimension(400, 400));

            newNameField = new JTextField(5);
            JButton btn = new JButton("Change Name");
            btn.setActionCommand("change");
            btn.addActionListener(this);

            setLayout(new GridLayout(listOfData.getToDoList().getLength() + 1,8));
            add(new Label("What is the new name"));
            add(newNameField);
            add(btn);

            pack();
            setVisible(true);
        }

        // MODIFIES: this
        // EFFECT: changes the name of the task, set the current screen to false, and rests the modify task admin screen
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("change")) {
                String inputNewName = newNameField.getText();
                currentAdmin.modifyTask(taskName, "name", inputNewName);
                new ModifyTaskScreenAdmin(currentAdmin);
                setVisible(false);
            }
        }
    }

    // a screen to change the date of completion
    private class ChangeDateOfCompletionScreen extends JFrame implements ActionListener {

        Admin currentAdmin;
        String taskName;

        JTextField newYearField;
        JTextField newMonthField;
        JTextField newDayField;

        // constructs a new screen to change the date of completion
        public ChangeDateOfCompletionScreen(Admin admin, String task) {
            super("Change date of completion screen");

            currentAdmin = admin;
            taskName = task;

            setPreferredSize(new Dimension(600, 600));

            newYearField = new JTextField(5);
            newMonthField = new JTextField(5);
            newDayField = new JTextField(5);

            JButton btn = new JButton("Change Due Date");
            btn.setActionCommand("change");
            btn.addActionListener(this);

            setLayout(new GridLayout(4,2));
            add(new Label("What is the new year"));
            add(newYearField);
            add(new Label("What is the new month"));
            add(newMonthField);
            add(new Label("What is the new day"));
            add(newDayField);

            add(btn);

            pack();
            setVisible(true);
        }

        // MODIFIES: this
        // EFFECT: when the change action is performed, reads all the input and changes the task's date of
        //         completion, refreshes the modify task screen for the current admin
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("change")) {
                int inputYear = Integer.parseInt(newYearField.getText());
                int inputMonth = Integer.parseInt(newMonthField.getText());
                int inputDay = Integer.parseInt(newDayField.getText());
                currentAdmin.modifyTask(taskName,"date of completion", inputYear,
                        inputMonth, inputDay);
                new ModifyTaskScreenAdmin(currentAdmin);
                setVisible(false);
            }
        }
    }

    // a screen for settings for the given admin page
    private class SettingAdminPage extends JFrame implements ActionListener {

        Admin currentAdmin;

        // constructs a new setting admin page
        public SettingAdminPage(Admin user) {
            super("Settings");

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            currentAdmin = user;

            add(addButtonPanel());

            pack();
            setVisible(true);
        }

        // EFFECT: creates the buttons available
        private JPanel addButtonPanel() {
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(3,2));
            buttonPanel.add(new JButton(new GoBack()));
            buttonPanel.add(new JButton(new ChangePassword()));
            buttonPanel.add(new JButton(new LogOut()));
            return buttonPanel;
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("Back");
            }

            // MODIFIES: this
            // EFFECT: go back to the admin page and sets the current screen's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AdminPage(currentAdmin);
                setVisible(false);
            }
        }

        // a logout action
        private class LogOut extends AbstractAction {

            // constructs a new log out action
            LogOut() {
                super("Log out");
            }

            // MODIFIES: this
            // EFFECT: resets all filters applied to the task list and go back to the home screen while
            //         setting the current screen's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentAdmin.filterToDoList("reset");
                new HomeScreen();
                setVisible(false);
            }
        }

        // a change password action
        private class ChangePassword extends AbstractAction {

            // constructs a new change password action
            ChangePassword() {
                super("Change Password");
            }

            // MODIFIES: this
            // EFFECT: opens the screen to change admin password, setting the current screen's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new ChangeAdminPasswordScreen(currentAdmin);
                setVisible(false);
            }
        }

        // EFFECT: do nothing
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // a screen to change password
    private class ChangeAdminPasswordScreen extends JFrame implements ActionListener {

        JTextField passwordField;
        Admin admin;

        // constructs a new change password screen
        public ChangeAdminPasswordScreen(Admin admin) {
            super("Change password screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));

            this.admin = admin;

            passwordField = new JTextField(5);
            JButton btn = new JButton("Change Password");
            btn.setActionCommand("change");
            btn.addActionListener(this);

            setLayout(new GridLayout(4,1));

            add(new JButton(new GoBack()));
            add(new Label("What is the new password"));
            add(passwordField);
            add(btn);

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("go back");
            }

            // MODIFIES: this
            // EFFECT: to back to the admin page for the specified user, setting the current screen's
            //         visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AdminPage(admin);
                setVisible(false);
            }
        }

        // MODIFIES: this
        // EFFECT: changes the password from the given input, then go back to the settings page for the specified
        //         admin, setting the current screen's visibility to false
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals("change")) {
                String inputPassword = passwordField.getText();
                admin.changePassword(inputPassword);
                new SettingAdminPage(admin);
                setVisible(false);
            }
        }
    }

    // Everything (ish) Developer
    // Developer screen
    private class DeveloperPage extends JFrame implements ActionListener {

        Developer currentDeveloper;

        // constructs a new developer page for the given developer
        public DeveloperPage(Developer developer) {
            super("Developer Home Screen");

            currentDeveloper = developer;

            setPreferredSize(new Dimension(1400, 1400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));

            add(addMenuBar());
            add(printedTaskList());

            pack();
            setVisible(true);
        }

        // EFFECT: creates menu bar
        private JMenuBar addMenuBar() {
            JMenuBar menuBar = new JMenuBar();
            menuBar.setSize(1400, 50);
            menuBar.add(addFilterMenu());
            menuBar.add(new JMenuItem(new AddTask()));
            menuBar.add(new JMenuItem(new ModifyTask()));
            menuBar.add(new JMenuItem(new Setting()));

            return menuBar;
        }

        // EFFECT: creates a menu for filter task page
        private JMenu addFilterMenu() {
            JMenu filterMenu = new JMenu("filter tasks");
            filterMenu.add(new JMenuItem(new Reset()));
            filterMenu.add(new JMenuItem(new Overdue()));
            filterMenu.add(new JMenuItem(new NotStarted()));
            filterMenu.add(new JMenuItem(new InProgress()));
            filterMenu.add(new JMenuItem(new Completed()));
            filterMenu.add(new JMenuItem(new NotAssigned()));
            filterMenu.add(new JMenuItem(new OwnTask()));
            filterMenu.add(addUrgencyMenu());

            return filterMenu;
        }

        // EFFECT: creates menu for the urgency for menu
        private JMenu addUrgencyMenu() {
            JMenu urgencyMenu = new JMenu("urgency");
            urgencyMenu.add(new JMenuItem(new FilterUrgency(0)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(1)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(2)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(3)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(4)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(5)));
            return urgencyMenu;
        }

        // reset filters action
        private class Reset extends AbstractAction {

            // constructs new reset action
            Reset() {
                super("reset");
            }

            // MODIFIES: this
            // EFFECT: opens the corresponding developer screen, turns current page's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentDeveloper.filterToDoList("reset");
                new DeveloperPage(currentDeveloper);
                setVisible(false);
            }
        }

        // action to filter task not yet started
        private class NotStarted extends AbstractAction {

            // constructs the filter action
            NotStarted() {
                super("not started");
            }

            // MODIFIES: this
            // EFFECT: filters all not started tasks, and turns current page's visibility to false, and refreshes
            //         the developer page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentDeveloper.filterToDoList("Not started");
                new DeveloperPage(currentDeveloper);
                setVisible(false);
            }
        }

        // action to filter tasks already in progress
        private class InProgress extends AbstractAction {

            // constructs the filter action
            InProgress() {
                super("in progress");
            }

            // MODIFIES: this
            // EFFECT: filters all in progress tasks, and turns current page's visibility to false, and refreshes
            //         the developer page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentDeveloper.filterToDoList("In progress");
                new DeveloperPage(currentDeveloper);
                setVisible(false);
            }
        }

        // action to filter tasks already completed
        private class Completed extends AbstractAction {

            // constructs the filter action
            Completed() {
                super("completed");
            }

            // MODIFIES: this
            // EFFECT: filters all completed tasks, and turns current page's visibility to false, and refreshes
            //         the developer page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentDeveloper.filterToDoList("Completed");
                new DeveloperPage(currentDeveloper);
                setVisible(false);
            }
        }

        // action to filter tasks assigned to the current developer
        private class OwnTask extends AbstractAction {

            // constructs the filter action
            OwnTask() {
                super("assigned to me");
            }

            // MODIFIES: this
            // EFFECT: filter all tasks assigned to self, and turns current page's visibility to false,
            //         refreshes the developer page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentDeveloper.filterOwnTask();
                new DeveloperPage(currentDeveloper);
                setVisible(false);
            }
        }

        // action to filter tasks overdue
        private class Overdue extends AbstractAction {

            // constructs the filter action
            Overdue() {
                super("overdue");
            }

            // MODIFIES: this
            // EFFECT: filters overdue tasks, and turns current page's visibility to false, and refreshes
            //         the developer page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentDeveloper.filterToDoList("overdue");
                new DeveloperPage(currentDeveloper);
                setVisible(false);
            }
        }

        // action to filter tasks not assigned to any developer
        private class NotAssigned extends AbstractAction {

            // constructs the filter action
            NotAssigned() {
                super("open to development");
            }

            // MODIFIES: this
            // EFFECT: filters tasks not yet assigned, and turns current page's visibility to false, and refreshes
            //         the developer page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentDeveloper.filterToDoList("not assigned yet");
                new DeveloperPage(currentDeveloper);
                setVisible(false);
            }
        }

        // action to filter tasks based on urgency
        private class FilterUrgency extends AbstractAction {

            int urgencyLevel;

            // constructs the filter action
            FilterUrgency(int urgencyFiltered) {
                super(Integer.toString(urgencyFiltered));
                urgencyLevel = urgencyFiltered;
            }

            // MODIFIES: this
            // EFFECT: filters tasks on urgency, and turns current page's visibility to false, and refreshes
            //         the developer page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentDeveloper.filterToDoList(urgencyLevel);
                new DeveloperPage(currentDeveloper);
                setVisible(false);
            }
        }

        // add task action
        private class AddTask extends AbstractAction {

            // constructs an add task action
            AddTask() {
                super("add task");
            }

            // MODIFIES: this
            // EFFECT: open an add task screen for the provided developer and turn this page's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AddTaskDeveloperScreen(currentDeveloper);
                setVisible(false);
            }
        }

        // modify task action
        private class ModifyTask extends AbstractAction {

            // constructs new modify task action
            ModifyTask() {
                super("modify tasks");
            }

            // MODIFIES: this
            // EFFECT: opens the corresponding modify task screen for developer, turns current page's visibility
            //         to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new ModifyTaskScreenDeveloper(currentDeveloper);
                setVisible(false);
            }
        }

        // setting screen
        private class Setting extends AbstractAction {

            // constructs new setting page
            Setting() {
                super("Settings");
            }

            // MODIFIES: this
            // EFFECT: opens the corresponding settings screen for developer, turns current page's visibility
            //         to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new SettingDeveloperPage(currentDeveloper);
                setVisible(false);
            }
        }

        // EFFECT: do nothing
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // Adding task screen for developer
    private class AddTaskDeveloperScreen extends JFrame implements ActionListener {

        Developer currentDeveloper;

        JTextField name;
        JTextField dueDateYear;
        JTextField dueDateMonth;
        JTextField dueDateDay;
        JTextField urgency;
        JTextField hours;

        // constructs the screen for adding tasks as a developer
        @SuppressWarnings("methodlength")
        public AddTaskDeveloperScreen(Developer developer) {
            super("Add task screen");

            currentDeveloper = developer;

            setPreferredSize(new Dimension(1400, 1400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            name = new JTextField(5);
            dueDateYear = new JTextField(5);
            dueDateMonth = new JTextField(5);
            dueDateDay = new JTextField(5);
            urgency = new JTextField(5);
            hours = new JTextField(5);

            JButton btn = new JButton("Add task");
            btn.setActionCommand("addTaskList");
            btn.addActionListener(this);

            setLayout(new GridLayout(6,4));
            add(new JButton(new GoBack()));
            add(new Label(""));
            add(new Label(""));
            add(new Label(""));

            add(new Label("Name"));
            add(name);
            add(new Label(""));
            add(new Label(""));

            add(new Label("Due date"));
            add(dueDateYear);
            add(dueDateMonth);
            add(dueDateDay);

            add(new Label("Urgency"));
            add(urgency);
            add(new Label(""));
            add(new Label(""));

            add(new Label("Hours required"));
            add(hours);
            add(new Label(""));
            add(new Label(""));

            add(btn);

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs the go back action
            GoBack() {
                super("Go back");
            }

            // MODIFIES: this
            // EFFECT: go back to the dev page of the specified user and set the current visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new DeveloperPage(currentDeveloper);
                setVisible(false);
            }
        }

        // MODIFIES: this
        // EFFECT: after pressing the add task list button, creates new task containing all the given inputs,
        //         then opens the success screen and sets the current screen's visibility to false;
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals("addTaskList")) {
                String inoutName = name.getText();
                int inputYear = Integer.parseInt(dueDateYear.getText());
                int inputMonth = Integer.parseInt(dueDateMonth.getText());
                int inputDay = Integer.parseInt(dueDateDay.getText());
                int inputUrgency = Integer.parseInt(urgency.getText());
                int inputHours = Integer.parseInt(hours.getText());

                Date inputDate = new Date(inputYear, inputMonth, inputDay);

                currentDeveloper.addTask(inoutName, inputDate, inputUrgency, inputHours);
                new SuccessAddTaskScreenDeveloper(currentDeveloper);
                setVisible(false);
            }
        }
    }

    // Success screen when developer adds a class
    private class SuccessAddTaskScreenDeveloper extends JFrame implements ActionListener {

        Developer currentDeveloper;

        // constructs a new success add task screen for the given admin
        public SuccessAddTaskScreenDeveloper(Developer developer) {
            super("Success Screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            currentDeveloper = developer;

            setLayout(new GridLayout(2,1));
            add(new JButton(new GoBack()));
            add(new Label("Task successfully added"));

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("Okay");
            }

            // MODIFIES: this
            // EFFECT: go back to the developer page for the provided user
            @Override
            public void actionPerformed(ActionEvent evt) {
                new DeveloperPage(currentDeveloper);
                setVisible(false);
            }
        }

        // EFFECT: do nothing
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // a screen for settings for the given developer page
    private class SettingDeveloperPage extends JFrame implements ActionListener {

        Developer currentDeveloper;

        // constructs a new setting developer page
        public SettingDeveloperPage(Developer user) {
            super("Settings");

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            currentDeveloper = user;

            add(addButtonPanel());

            pack();
            setVisible(true);
        }

        // EFFECT: creates the buttons available
        private JPanel addButtonPanel() {
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(3,2));
            buttonPanel.add(new JButton(new GoBack()));
            buttonPanel.add(new JButton(new ChangePassword()));
            buttonPanel.add(new JButton(new LogOut()));
            return buttonPanel;
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("Back");
            }

            // MODIFIES: this
            // EFFECT: go back to the admin page and sets the current screen's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new DeveloperPage(currentDeveloper);
                setVisible(false);
            }
        }

        // a logout action
        private class LogOut extends AbstractAction {

            // constructs a new log out action
            LogOut() {
                super("Log out");
            }

            // MODIFIES: this
            // EFFECT: resets all filters applied to the task list and go back to the home screen while
            //         setting the current screen's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new HomeScreen();
                setVisible(false);
            }
        }

        // a change password action
        private class ChangePassword extends AbstractAction {

            // constructs a new change password action
            ChangePassword() {
                super("Change Password");
            }

            // MODIFIES: this
            // EFFECT: opens the screen to change dev password, setting the current screen's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new ChangeDeveloperPasswordScreen(currentDeveloper);
                setVisible(false);
            }
        }

        // EFFECT: do nothing
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // a screen to change password
    private class ChangeDeveloperPasswordScreen extends JFrame implements ActionListener {

        JTextField passwordField;
        Developer developer;

        // constructs a new change password screen
        public ChangeDeveloperPasswordScreen(Developer developer) {
            super("Change password screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));

            this.developer = developer;

            passwordField = new JTextField(5);
            JButton btn = new JButton("Change Password");
            btn.setActionCommand("change");
            btn.addActionListener(this);

            setLayout(new GridLayout(4,1));

            add(new JButton(new GoBack()));
            add(new Label("What is the new password"));
            add(passwordField);
            add(btn);

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("go back");
            }

            // MODIFIES: this
            // EFFECT: to back to the admin page for the specified user, setting the current screen's
            //         visibility to false@Override
            public void actionPerformed(ActionEvent evt) {
                new DeveloperPage(developer);
                setVisible(false);
            }
        }

        // MODIFIES: this
        // EFFECT: changes the password from the given input, then go back to the settings page for the specified
        //         admin, setting the current screen's visibility to false
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals("change")) {
                String inputPassword = passwordField.getText();
                developer.changePassword(inputPassword);
                new SettingDeveloperPage(developer);
                setVisible(false);
            }
        }
    }

    // Modify screen for developer
    private class ModifyTaskScreenDeveloper extends JFrame implements ActionListener {

        Developer currentDeveloper;

        // constructs new screen to modifying tasks
        public ModifyTaskScreenDeveloper(Developer developer) {
            super("Modify Task Screen");
            setPreferredSize(new Dimension(1400, 1400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            currentDeveloper = developer;

            setLayout(new BoxLayout((JPanel) getContentPane(), BoxLayout.PAGE_AXIS));
            add(new JButton(new GoBack()));
            add(printedModifyingTaskList());

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs the go back action
            GoBack() {
                super("Okay");
            }

            // MODIFIES: this
            // EFFECT: go back to the developer page for the given user
            @Override
            public void actionPerformed(ActionEvent evt) {
                new DeveloperPage(currentDeveloper);
                setVisible(false);
            }
        }

        // EFFECT: returns the proper view of the task list with proper editing tools
        @SuppressWarnings("methodlength")
        private JPanel printedModifyingTaskList() {

            JPanel taskListPanel = new JPanel();

            taskListPanel.setLayout(new GridLayout(listOfData.getToDoList().getLength() + 1,7));
            taskListPanel.add(new Label("name "));
            taskListPanel.add(new Label("date of creation "));
            taskListPanel.add(new Label("due date "));
            taskListPanel.add(new Label("urgency "));
            taskListPanel.add(new Label("required hours "));
            taskListPanel.add(new Label("status "));
            taskListPanel.add(new Label("assigned developer "));

            for (int i = 0; i < listOfData.getToDoList().getLength(); i++) {

                Task task = listOfData.getToDoList().getTaskList().get(i);

                JComboBox<String> statusList = modifyTaskStatus(task);
                statusList.setActionCommand("changeStatus" + i);
                statusList.addActionListener(this);

                taskListPanel.add(new Label(task.getName()));
                taskListPanel.add(new Label(getDateAsString(task.getDateOfCreation())));
                taskListPanel.add(new Label(getDateAsString(task.getDateOfCompletion())));
                taskListPanel.add(new Label(Integer.toString(task.getUrgency())));
                taskListPanel.add(new Label(Integer.toString(task.getHoursRequiredForCompletion())));

                if (task.getAssignedDeveloper().equals(currentDeveloper.getUserID())) {
                    taskListPanel.add(statusList);
                } else {
                    taskListPanel.add(new Label(task.getStatus()));

                }

                if (task.getAssignedDeveloper().equals("none")) {
                    taskListPanel.add(new JButton(new AssignSelf(task.getName())));
                } else {
                    taskListPanel.add(new Label(task.getAssignedDeveloper()));
                }
            }

            return taskListPanel;
        }

        // EFFECT: create the menu for the status of the task
        private JComboBox<String> modifyTaskStatus(Task task) {
            String[] statusChoices = {"Not started", "In progress", "Completed"};
            JComboBox<String> statusList = new JComboBox<>(statusChoices);

            switch (task.getStatus()) {
                case "Not started":
                    statusList.setSelectedIndex(0);
                    break;
                case "In progress":
                    statusList.setSelectedIndex(1);
                    break;
                case "Completed":
                    statusList.setSelectedIndex(2);
                    break;
            }

            return statusList;
        }

        // an assign self action
        private class AssignSelf extends AbstractAction {

            String taskToModify;

            // constructs a assign self action for the given task
            AssignSelf(String taskName) {
                super("take on task");
                taskToModify = taskName;
            }

            // MODIFIES: this
            // EFFECT: assigns self to the task and refreshes the task screen
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentDeveloper.takeOnTask(taskToModify);
                new ModifyTaskScreenDeveloper(currentDeveloper);
                setVisible(false);
            }
        }

        // MODIFIES: this
        // EFFECT: depending on the object changed, change the corresponding value in the task
        @Override
        public void actionPerformed(ActionEvent e) {

            for (int i = 0; i < 20; i++) {

                JComboBox cb = (JComboBox)e.getSource();

                if (e.getActionCommand().equals("changeStatus" + i)) {
                    String newStatus = (String)cb.getSelectedItem();
                    Task task = listOfData.getToDoList().getTaskList().get(i);
                    currentDeveloper.changeStatus(task.getName(), newStatus);
                }

            }
        }
    }

    // Everything (ish) Visitor

    // Visitor screen
    private class VisitorPage extends JFrame implements ActionListener {

        Visitor currentVisitor;

        // constructs a new visitor page for the given visitor
        public VisitorPage(Visitor visitor) {
            super("Visitor Home Screen");

            currentVisitor = visitor;

            setPreferredSize(new Dimension(1400, 1400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new BoxLayout((JPanel) getContentPane(), BoxLayout.PAGE_AXIS));

            add(addMenuBar());
            add(printedTaskList());

            pack();
            setVisible(true);
        }

        // EFFECT: creates menu bar
        private JMenuBar addMenuBar() {
            JMenuBar menuBar = new JMenuBar();
            menuBar.setSize(1400, 50);
            menuBar.add(addFilterMenu());
            menuBar.add(new JMenuItem(new Setting()));

            return menuBar;
        }

        // EFFECT: creates a menu for filter task page
        private JMenu addFilterMenu() {
            JMenu filterMenu = new JMenu("filter tasks");
            filterMenu.add(new JMenuItem(new Reset()));
            filterMenu.add(new JMenuItem(new Overdue()));
            filterMenu.add(new JMenuItem(new NotStarted()));
            filterMenu.add(new JMenuItem(new InProgress()));
            filterMenu.add(new JMenuItem(new Completed()));
            filterMenu.add(new JMenuItem(new NotAssigned()));
            filterMenu.add(addUrgencyMenu());

            return filterMenu;
        }

        // EFFECT: creates menu for the urgency for menu
        private JMenu addUrgencyMenu() {
            JMenu urgencyMenu = new JMenu("urgency");
            urgencyMenu.add(new JMenuItem(new FilterUrgency(0)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(1)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(2)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(3)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(4)));
            urgencyMenu.add(new JMenuItem(new FilterUrgency(5)));
            return urgencyMenu;
        }

        // setting screen
        private class Setting extends AbstractAction {

            // constructs new setting page
            Setting() {
                super("Settings");
            }

            // MODIFIES: this
            // EFFECT: opens the corresponding settings screen for visitor, turns current page's
            //         visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new SettingVisitorPage(currentVisitor);
                setVisible(false);
            }
        }

        // reset task action
        private class Reset extends AbstractAction {

            // constructs new reset action
            Reset() {
                super("reset");
            }

            // MODIFIES: this
            // EFFECT: opens the corresponding visitor screen, turns current page's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentVisitor.filterToDoList("reset");
                new ProgressTrackerGUI.VisitorPage(currentVisitor);
                setVisible(false);
            }
        }

        // action to filter task not yet started
        private class NotStarted extends AbstractAction {

            // constructs the filter action
            NotStarted() {
                super("not started");
            }

            // MODIFIES: this
            // EFFECT: filters all not started tasks, and turns current page's visibility to false, and refreshes
            //         the visitor page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentVisitor.filterToDoList("Not started");
                new ProgressTrackerGUI.VisitorPage(currentVisitor);
                setVisible(false);
            }
        }

        // action to filter tasks already in progress
        private class InProgress extends AbstractAction {

            // constructs the filter action
            InProgress() {
                super("in progress");
            }

            // MODIFIES: this
            // EFFECT: filters all in progress tasks, and turns current page's visibility to false, and refreshes
            //         the visitor page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentVisitor.filterToDoList("In progress");
                new ProgressTrackerGUI.VisitorPage(currentVisitor);
                setVisible(false);
            }
        }

        // action to filter tasks already completed
        private class Completed extends AbstractAction {

            // constructs the filter action
            Completed() {
                super("completed");
            }

            // MODIFIES: this
            // EFFECT: filters all completed tasks, and turns current page's visibility to false, and refreshes
            //         the visitor page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentVisitor.filterToDoList("Completed");
                new ProgressTrackerGUI.VisitorPage(currentVisitor);
                setVisible(false);
            }
        }

        // action to filter tasks overdue
        private class Overdue extends AbstractAction {

            // constructs the filter action
            Overdue() {
                super("overdue");
            }

            // MODIFIES: this
            // EFFECT: filters overdue tasks, and turns current page's visibility to false, and refreshes
            //         the visitor page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentVisitor.filterToDoList("overdue");
                new ProgressTrackerGUI.VisitorPage(currentVisitor);
                setVisible(false);
            }
        }

        // action to filter tasks not assigned to any developer
        private class NotAssigned extends AbstractAction {

            // constructs the filter action
            NotAssigned() {
                super("open to development");
            }

            // MODIFIES: this
            // EFFECT: filters tasks not yet assigned, and turns current page's visibility to false, and refreshes
            //         the visitor page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentVisitor.filterToDoList("not assigned yet");
                new ProgressTrackerGUI.VisitorPage(currentVisitor);
                setVisible(false);
            }
        }

        // action to filter tasks based on urgency
        private class FilterUrgency extends AbstractAction {

            int urgencyLevel;

            // constructs the filter action
            FilterUrgency(int urgencyFiltered) {
                super(Integer.toString(urgencyFiltered));
                urgencyLevel = urgencyFiltered;
            }

            // MODIFIES: this
            // EFFECT: filters tasks on urgency, and turns current page's visibility to false, and refreshes
            //         the visitor page
            @Override
            public void actionPerformed(ActionEvent evt) {
                currentVisitor.filterToDoList(urgencyLevel);
                new ProgressTrackerGUI.VisitorPage(currentVisitor);
                setVisible(false);
            }
        }

        // EFFECT: do nothing
        @Override
        public void actionPerformed(ActionEvent e) {}
    }

    // a screen to change the date of completion
    private class SettingVisitorPage extends JFrame implements ActionListener {

        Visitor currentVisitor;

        // constructs a new setting visitor page
        public SettingVisitorPage(Visitor user) {
            super("Settings");

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new FlowLayout());

            currentVisitor = user;

            add(addButtonPanel());

            pack();
            setVisible(true);
        }

        // EFFECT: creates the buttons available
        private JPanel addButtonPanel() {
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(3,2));
            buttonPanel.add(new JButton(new GoBack()));
            buttonPanel.add(new JButton(new ChangePassword()));
            buttonPanel.add(new JButton(new LogOut()));
            return buttonPanel;
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("Back");
            }

            // MODIFIES: this
            // EFFECT: go back to the visitor page and sets the current screen's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new VisitorPage(currentVisitor);
                setVisible(false);
            }
        }

        // a logout action
        private class LogOut extends AbstractAction {

            // constructs a new log out action
            LogOut() {
                super("Log out");
            }

            // MODIFIES: this
            // EFFECT: resets all filters applied to the task list and go back to the home screen while
            //         setting the current screen's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new HomeScreen();
                setVisible(false);
            }
        }

        // a change password action
        private class ChangePassword extends AbstractAction {

            // constructs a new change password action
            ChangePassword() {
                super("Change Password");
            }

            // MODIFIES: this
            // EFFECT: opens the screen to change visitor password, setting the current screen's visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new ChangeVisitorPasswordScreen(currentVisitor);
                setVisible(false);
            }
        }

        // EFFECT: do nothing
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    // a screen to change password
    private class ChangeVisitorPasswordScreen extends JFrame implements ActionListener {

        JTextField passwordField;
        Visitor visitor;

        // constructs a new change password screen
        public ChangeVisitorPasswordScreen(Visitor visitor) {
            super("Change password screen");
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));

            this.visitor = visitor;

            passwordField = new JTextField(5);
            JButton btn = new JButton("Change Password");
            btn.setActionCommand("change");
            btn.addActionListener(this);

            setLayout(new GridLayout(4,1));

            add(new JButton(new GoBack()));
            add(new Label("What is the new password"));
            add(passwordField);
            add(btn);

            pack();
            setVisible(true);
        }

        // a go back action
        private class GoBack extends AbstractAction {

            // constructs a new go back action
            GoBack() {
                super("go back");
            }

            // MODIFIES: this
            // EFFECT: to back to the visitor page for the specified user, setting the current screen's
            //         visibility to false
            @Override
            public void actionPerformed(ActionEvent evt) {
                new VisitorPage(visitor);
                setVisible(false);
            }
        }

        // MODIFIES: this
        // EFFECT: changes the password from the given input, then go back to the settings page for the specified
        //         admin, setting the current screen's visibility to false
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals("change")) {
                String inputPassword = passwordField.getText();
                visitor.changePassword(inputPassword);
                new SettingVisitorPage(visitor);
                setVisible(false);
            }
        }
    }

    // Random stuff

    // EFFECT: returns the task list in proper format
    private JPanel printedTaskList() {
        JPanel taskListPanel = new JPanel();

        taskListPanel.setLayout(new GridLayout(getNumRowNeeded(),7));
        taskListPanel.add(new Label("name "));
        taskListPanel.add(new Label("date of creation "));
        taskListPanel.add(new Label("due date "));
        taskListPanel.add(new Label("urgency "));
        taskListPanel.add(new Label("required hours "));
        taskListPanel.add(new Label("status "));
        taskListPanel.add(new Label("assigned developer "));

        for (Task task : listOfData.getToDoList().getTaskList()) {
            if (task.getVisibility()) {
                taskListPanel.add(new Label(task.getName()));
                taskListPanel.add(new Label(getDateAsString(task.getDateOfCreation())));
                taskListPanel.add(new Label(getDateAsString(task.getDateOfCompletion())));
                taskListPanel.add(new Label(Integer.toString(task.getUrgency())));
                taskListPanel.add(new Label(Integer.toString(task.getHoursRequiredForCompletion())));
                taskListPanel.add(new Label(task.getStatus()));
                taskListPanel.add(new Label(task.getAssignedDeveloper()));
            }
        }

        return taskListPanel;
    }

    // EFFECT: get the number of rows needed for the printed task list
    private int getNumRowNeeded() {
        int numRowsNeeded = 1;
        for (Task task : listOfData.getToDoList().getTaskList()) {
            if (task.getVisibility()) {
                numRowsNeeded++;
            }
        }
        return numRowsNeeded;
    }

    // EFFECT: takes in a date and returns in the format of a string
    public String getDateAsString(Date date) {
        return date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth();
    }
}

