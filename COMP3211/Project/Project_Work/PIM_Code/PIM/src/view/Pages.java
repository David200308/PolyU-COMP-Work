package view;

import controller.Auth;
import controller.Import;
import controller.Note;

import java.util.ArrayList;
import java.util.Objects;

public class Pages {

    /**
     * The Start Page
     */
    public static void startPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("        Welcome to the Personal Information Manager (PIM)!");
        System.out.println("             COMP3211 - Software Engineering");
        System.out.println("                       Project Group 13");
        System.out.println();
        System.out.println("- [1] Login");
        System.out.println("- [2] Signup");
        System.out.println("- [-1] Exit System");
        System.out.println();
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * The Login Page
     */
    public static void loginPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("        Login");
        System.out.println();
    }

    /**
     * The Signup Page
     */
    public static void signupPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("        Signup");
        System.out.println();
    }

    /**
     * The Signup Page
     */
    public static void userNameInput() {
        System.out.print("Username: ");
    }

    /**
     * The Signup Page
     */
    public static void userPasswordInput() {
        System.out.print("Password: ");
    }


    /**
     * The Main Function Page
     * @param userName: The username of the account
     */
    public static void mainPage(String userName) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("        Welcome " + userName);
        System.out.println();
        System.out.println("----------------------------------------------------------------");
        System.out.println();
        System.out.println("- [1] Notes");
        System.out.println("- [2] Contacts");
        System.out.println("- [3] To-do Lists");
        System.out.println("- [4] Events");
        System.out.println("- [5] Load .PIM File");
        System.out.println("- [6] Export .PIM File");
        System.out.println("- [-1] Exit System");
        System.out.println();
        System.out.print(">>> Please select the above options x in [x]: ");
    }


    /**
     * The Note Page
     * @param userName: The username of the account
     */
    public static void notePage(String userName) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("        Notes - " + userName);
        System.out.println();
        System.out.println("- [1] New Notes");
        System.out.println("- [2] Read Note");
        System.out.println("- [3] Search Note");
        System.out.println("- [4] Modify Note");
        System.out.println("- [5] Remove Note");
        System.out.println("- [-1] Back to last page");
        System.out.println();
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * Note title
     */
    public static void noteTitle() {
        System.out.print("Note Title: ");
    }

    /**
     * Note content
     */
    public static void noteContent() {
        System.out.println("Note Content: ");
        System.out.println("----------------------------------------------------------------");
    }

    /**
     * Note last modify time
     */
    public static void noteLastModifyTime() {
        System.out.println("----------------------------------------------------------------");
        System.out.print("Note last modify time: ");
    }

    /**
     * New Note Page
     */
    public static void newNotePage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println();
        System.out.println("- [1] Save Note");
        System.out.println("- [2] Discard Note");
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * Note List
     */
    public static void noteList() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Your List of Notes: (sort by last modify time in descending order)");
        System.out.println("Note ID        Note Title");
    }

    /**
     * Read Page
     */
    public static void readPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println();
        System.out.println("- [1] Choose which one to read");
        System.out.println("- [2] Read All");
        System.out.println("- [-1] Back to last page");
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * Update Page
     */
    public static void updatePage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println();
        System.out.println("- [1] Choose which one to update");
        System.out.println("- [-1] Back to last page");
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * Update Choice Page
     */
    public static void updateChoicePage() {
        System.out.print(">>> Please input the ID you want to update: ");
    }

    /**
     * Remove Page
     */
    public static void lookForPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println();
        System.out.println("- [1] Choose which one to look for");
        System.out.println("- [2] Look For All");
        System.out.println("- [-1] Back to last page");
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * read one page
     */
    public static void readOne() {
        System.out.print(">>> Please input the class ID you want to read: ");
    }

    /**
     * update one page
     */
    public static void lookForOne() {
        System.out.print(">>> Please input the class ID you want to look for: ");
    }



    /**
     * The Contact Page
     * @param userName: The username of the account
     */
    public static void contactPage(String userName) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("        Contacts - " + userName);
        System.out.println();
        System.out.println("- [1] Create New Contact");
        System.out.println("- [2] All Contacts");
        System.out.println("- [3] Search Contact");
        System.out.println("- [4] Modify Contact");
        System.out.println("- [5] Remove Contact");
        System.out.println("- [-1] Back to last page");
        System.out.println();
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * Contact firstname
     */
    public static void contactFirstName() {
        System.out.print("First Name: ");
    }

    /**
     * Contact lastname
     */
    public static void contactLastName() {
        System.out.print("Last Name: ");
    }

    /**
     * Contact phonenumber
     */
    public static void contactPhoneNumber() {
        System.out.print("Phone Number: ");
    }

    /**
     * Contact Address
     */
    public static void contactAddress() {
        System.out.print("Address: ");
    }

    /**
     * New Contact Page
     */
    public static void newContactPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println();
        System.out.println("- [1] Save Contact");
        System.out.println("- [2] Discard Contact");
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * Contact List
     */
    public static void contactList() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Your List of Contacts: ");
        System.out.println("Contact ID     Contact Name");
    }


    /**
     * The To-do List Page
     * @param userName: The username of the account
     */
    public static void todoPage(String userName) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("        To-do Lists - " + userName);
        System.out.println();
        System.out.println("- [1] Create New Task");
        System.out.println("- [2] All Tasks");
        System.out.println("- [3] Search Task");
        System.out.println("- [4] Modify Task");
        System.out.println("- [5] Remove Task");
        System.out.println("- [-1] Back to last page");
        System.out.println();
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * todo name
     */
    public static void todoName() {
        System.out.print("Task Name: ");
    }

    /**
     * todo ddl
     */
    public static void todoDDL() {
        System.out.print("Task Deadline (YYYY-MM-DD HH:MM:SS): ");
    }

    /**
     * todo alarm
     */
    public static void todoDescription() {
        System.out.print("Task Description: ");
    }

    /**
     * New todo Page
     */
    public static void newTodoPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println();
        System.out.println("- [1] Save Task");
        System.out.println("- [2] Discard Task");
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * todo List
     */
    public static void todoList() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Your List of Tasks: (sort by deadline in ascending order)");
        System.out.println("Task ID        Task Title");
    }


    /**
     * The Event Page
     * @param userName: The username of the account
     */
    public static void eventPage(String userName) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("        Events - " + userName);
        System.out.println();
        System.out.println("- [1] Create New Event");
        System.out.println("- [2] All Events");
        System.out.println("- [3] Search Event");
        System.out.println("- [4] Modify Event");
        System.out.println("- [5] Remove Event");
        System.out.println("- [-1] Back to last page");
        System.out.println();
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * event name
     */
    public static void eventName() {
        System.out.print("Event Name: ");
    }

    /**
     * event start time
     */
    public static void eventStartTime() {
        System.out.print("Event Start Time (YYYY-MM-DD HH:MM:SS): ");
    }

    /**
     * event alarm
     */
    public static void eventAlarm() {
        System.out.print("Event Alarm (YYYY-MM-DD HH:MM:SS): ");
    }

    /**
     * event description
     */
    public static void eventDescription() {
        System.out.println("Event Description: ");
        System.out.println("----------------------------------------------------------------");
    }

    /**
     * new event page
     */
    public static void newEventPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println();
        System.out.println("- [1] Save Event");
        System.out.println("- [2] Discard Event");
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * event list
     */
    public static void eventList() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Your List of Events: (sort by start time in ascending order)");
        System.out.println("Event ID       Event Title");
    }

    /**
     * The search Page
     */
    public static void searchPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("                           Search");
        System.out.println("- [1] Search by Keyword");
        System.out.println("- [2] Search by Time");
        System.out.println("- [3] Search with Logic Connector");
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    public static void searchContactOnlyPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("                           Search");
        System.out.println("- [1] Search by Keyword");
        System.out.println("- [2] Search with Logic Connector");
        System.out.print(">>> Please select the above options x in [x]: ");
    }


    /**
     * The search By Keyword Page
     */
    public static void searchByKeywordPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("                           Search");
        System.out.print("Searching Keyword(s): ");
    }

    /**
     * The search By Keyword Page
     */
    public static void searchWithLogicConnectorPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("                           Search");
        System.out.print("Searching with logic connector(s): ");
    }

    /**
     * The search By Keyword Page
     */
    public static void searchByTimePage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("                           Search");
        System.out.print("Searching by Time: ");
    }

    /**
     * The Modify Page
     */
    public static void removePage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("                            Remove");
        System.out.println();
        System.out.println("- [1 or ID] Choose which one to remove");
        System.out.println("- [-1] Back to last page");
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * The Modify Page
     */
    public static void exportPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("                           Export");
        System.out.println();
        System.out.println("- [1] Confirm export to .PIM File");
        System.out.println("- [-1] Back to last page");
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * The Modify Page
     */
    public static void importPage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("                           Import");
        System.out.println();
        System.out.println("The file(s) in Input folder:");
        ArrayList<String> files = Import.getFilesInInputFolder();
        for (String file : files) {
            System.out.println(file);
        }
        System.out.println("----------------------------------------------------------------");
        System.out.println("- [1] Choose the PIM File");
        System.out.println("- [-1] Back to last page");
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    /**
     * The Modify Page
     */
    public static void importFileNamePage() {
        System.out.println("----------------------------------------------------------------");
        System.out.print(">>> Please input the file name: ");
    }


}
