package controller;

import model.SimpleDatabase;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class PIM {
    public static void main(String[] args) throws IOException {
        SimpleDatabase.isDatabaseExist();
        view.Pages.startPage();
        Input.setInput();
        if (Input.getInput().equals("1")) {
            mainConnector();
        } else if (Input.getInput().equals("2")) {
            view.Pages.signupPage();
            String[] loginSignupInput = loginSignupConnector();
            Auth.signup(loginSignupInput[0], loginSignupInput[1]);
            mainConnector();
        } else {
            System.out.println("Exit System!");
        }
    }

    /**
     * Check Date Format Function
     * @param date: the date to be checked
     * @return: the boolean value of the date format is correct or not
     */
    private static Boolean checkDateFormat(String date) {
        try {
            //LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Please input the correct date format!");
            return false;
        }
    }

    /**
     * Login or Signup Connector Function
     * @return the login or signup input
     */
    public static String[] loginSignupConnector() {
        view.Pages.userNameInput();
        Input.setInput();
        String userName = Input.getInput();
        view.Pages.userPasswordInput();
        Input.setInput();
        String password = Input.getInput();

        return new String[]{
                userName, password
        };
    }

    /**
     * Note Connector Function
     * @param loginSignupInput: the login or signup input
     */
    public static void NoteConnector(String[] loginSignupInput) {
        while (true) {
            view.Pages.notePage(loginSignupInput[0]);
            Input.setInput();
            String noteChoice = Input.getInput();
            if (noteChoice.equals("1")) {
                view.Pages.noteTitle();
                Input.setInput();
                String noteTitle = Input.getInput();
                view.Pages.noteContent();
                Input.setInput();
                String noteContent = Input.getInput();
                view.Pages.newNotePage();
                Input.setInput();
                if (Input.getInput().equals("1")) {
                    Note noteInfo = new Note(noteTitle, noteContent);
                    Note.createNote(noteInfo);
                } else if (Input.getInput().equals("2")) {
                    view.Pages.notePage(loginSignupInput[0]);
                }

            } else if (noteChoice.equals("2")) {
                while (true){
                    view.Pages.readPage();
                    Input.setInput();
                    String readChoice = Input.getInput();
                    if (readChoice.equals("1")) {
                        view.Pages.noteList();
                        for (int i = 0; i < Objects.requireNonNull(Note.getAllNotes()).length - 1; i++) {
                            if (Auth.getUserId() == Integer.parseInt(Note.getAllNotes()[i][1])) {
                                System.out.printf("%-15s%-30s\n", Note.getAllNotes()[i][0], Note.getAllNotes()[i][2]);
                            }
                        }
                        view.Pages.readOne();
                        Input.setInput();
                        String noteID = Input.getInput();
                        String[] note = Note.getOneNote(noteID);
                        view.Pages.noteTitle();
                        System.out.println(Objects.requireNonNull(note)[2]);
                        view.Pages.noteContent();
                        System.out.println(note[3]);
                        view.Pages.noteLastModifyTime();
                        System.out.println(note[4]);
                    } else if (readChoice.equals("2")) {
                        String[][] allNote = Note.getAllNotes();
                        for (int i = 0; i < Objects.requireNonNull(allNote).length - 1; i++) {
                            view.Pages.noteTitle();
                            System.out.println(Objects.requireNonNull(allNote)[i][2]);
                            view.Pages.noteContent();
                            System.out.println(allNote[i][3]);
                            view.Pages.noteLastModifyTime();
                            System.out.println(allNote[i][4]);
                        }
                    } else if (readChoice.equals("-1")) {
                        break;
                    }
                }
            } else if (noteChoice.equals("3")) {
                view.Pages.searchPage();
                Input.setInput();
                if (Input.getInput().equals("1")) {
                    view.Pages.searchByKeywordPage();
                    Input.setInput();
                    String keyword = Input.getInput();
                    String[][] searchResult = Search.search(keyword, "notes.csv");
                    System.out.println("Search Result:");
                    System.out.println("----------------------------------------------------------------");
                    for (String[] x : searchResult) {
                        for (int i = 2; i < x.length; i++) {
                            System.out.print(x[i] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                } else if (Input.getInput().equals("2")) {
                    view.Pages.searchByTimePage();
                    Input.setInput();
                    String date = Input.getInput();
                    String[][] searchResult = Search.searchByTime(date, "notes.csv");
                    System.out.println("Search Result:");
                    System.out.println("----------------------------------------------------------------");
                    for (String[] x : searchResult) {
                        for (int i = 2; i < x.length; i++) {
                            System.out.print(x[i] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                } else if (Input.getInput().equals("3")) {
                    view.Pages.searchWithLogicConnectorPage();
                    Input.setInput();
                    String logicStatement = Input.getInput();
                    String[][] searchResult = Search.searchWithLogicalConnectors(logicStatement, "notes");
                    System.out.println("Search Result:");
                    System.out.println("----------------------------------------------------------------");
                    for (String[] x : searchResult) {
                        for (int i = 2; i < x.length; i++) {
                            System.out.print(x[i] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                } else if (Input.getInput().equals("-1")) {
                    break;
                }

            } else if (noteChoice.equals("4")) {
                view.Pages.updatePage();
                Input.setInput();
                String updateChoice = Input.getInput();
                if (updateChoice.equals("1")) {
                    view.Pages.noteList();
                    for (int i = 0; i < Objects.requireNonNull(Note.getAllNotes()).length - 1; i++) {
                        if (Auth.getUserId() == Integer.parseInt(Note.getAllNotes()[i][1])) {
                            System.out.printf("%-15s%-30s\n", Note.getAllNotes()[i][0], Note.getAllNotes()[i][2]);
                        }
                    }
                    view.Pages.updateChoicePage();
                    Input.setInput();
                    String updateId = Input.getInput();
                    String[] note = Note.getOneNote(updateId);
                    view.Pages.noteTitle();
                    System.out.println(Objects.requireNonNull(note)[2]);
                    view.Pages.noteContent();
                    System.out.println(note[3]);
                    view.Pages.noteLastModifyTime();
                    System.out.println(note[4]);
                    view.Pages.noteTitle();
                    Input.setInput();
                    String noteTitle = Input.getInput();
                    view.Pages.noteContent();
                    Input.setInput();
                    String noteContent = Input.getInput();
                    Note noteInfo = new Note(noteTitle, noteContent);
                    Note.modifyNote(noteInfo, updateId);
                } else if (updateChoice.equals("-1")) {
                    break;
                }
            } else if (noteChoice.equals("5")) {
                while(true) {
                    view.Pages.removePage();
                    Input.setInput();
                    String removeChoice = Input.getInput();
                    if (removeChoice.equals("1")) {
                        view.Pages.noteList();
                        for (int i = 0; i < Objects.requireNonNull(Note.getAllNotes()).length - 1; i++) {
                            if (Auth.getUserId() == Integer.parseInt(Note.getAllNotes()[i][1])) {
                                System.out.printf("%-15s%-30s\n", Note.getAllNotes()[i][0], Note.getAllNotes()[i][2]);
                            }
                        }
                        view.Pages.removePage();
                        Input.setInput();
                        String removeId = Input.getInput();
                        Note.removeNote(removeId);
                    } else if (removeChoice.equals("-1")) {
                        break;
                    }
                }
            } else if (noteChoice.equals("-1")) {
                break;
            } else {
                System.out.println("Please input the correct number!");
            }
        }
    }

    /**
     * Contact Connector Function
     * @param loginSignupInput: the login or signup input
     */
    public static void ContactConnector(String[] loginSignupInput) {
        while(true) {
            view.Pages.contactPage(loginSignupInput[0]);
            Input.setInput();
            if (Input.getInput().equals("1")) {
                view.Pages.contactFirstName();
                Input.setInput();
                String contactFirstName = Input.getInput();
                view.Pages.contactLastName();
                Input.setInput();
                String contactLastName = Input.getInput();
                String contactName = Input.getInput();
                view.Pages.contactPhoneNumber();
                Input.setInput();
                String contactPhoneNumber = Input.getInput();
                view.Pages.contactAddress();
                Input.setInput();
                String contactAddress = Input.getInput();
                view.Pages.newContactPage();
                Input.setInput();
                if (Input.getInput().equals("1")) {
                    Contact contactInfo = new Contact(contactFirstName, contactLastName, contactPhoneNumber, contactAddress);
                    Contact.createContact(contactInfo);
                } else if (Input.getInput().equals("2")) {
                    view.Pages.contactPage(loginSignupInput[0]);
                }
            } else if (Input.getInput().equals("2")) {
                while (true){
                    view.Pages.lookForPage();
                    Input.setInput();
                    String lookForChoice = Input.getInput();
                    if (lookForChoice.equals("1")) {
                        view.Pages.contactList();
                        for (int i = 0; i < Objects.requireNonNull(Contact.getAllContacts()).length - 1; i++) {
                            if (Auth.getUserId() == Integer.parseInt(Contact.getAllContacts()[i][1])) {
                                System.out.printf("%-15s%-30s\n", Contact.getAllContacts()[i][0], Contact.getAllContacts()[i][3] + " " + Contact.getAllContacts()[i][2]);
                            }
                        }
                        view.Pages.lookForOne();
                        Input.setInput();
                        String contactID = Input.getInput();
                        String[] contact = Contact.getOneContact(contactID);
                        view.Pages.contactFirstName();
                        System.out.println(Objects.requireNonNull(contact)[2]);
                        view.Pages.contactLastName();
                        System.out.println(contact[3]);
                        view.Pages.contactPhoneNumber();
                        System.out.println(contact[4]);
                        view.Pages.contactAddress();
                        System.out.println(contact[5]);
                    } else if (lookForChoice.equals("2")) {
                        String[][] allContact = Contact.getAllContacts();
                        for (int i = 0; i < Objects.requireNonNull(allContact).length - 1; i++) {
                            view.Pages.contactFirstName();
                            System.out.println(Objects.requireNonNull(allContact)[i][2]);
                            view.Pages.contactLastName();
                            System.out.println(allContact[i][3]);
                            view.Pages.contactPhoneNumber();
                            System.out.println(allContact[i][4]);
                            view.Pages.contactAddress();
                            System.out.println(allContact[i][5]);
                        }
                    } else if (lookForChoice.equals("-1")) {
                        break;
                    }
                }
            } else if (Input.getInput().equals("3")) {
                view.Pages.searchContactOnlyPage();
                Input.setInput();
                if (Input.getInput().equals("1")) {
                    view.Pages.searchByKeywordPage();
                    Input.setInput();
                    String keyword = Input.getInput();
                    String[][] searchResult = Search.search(keyword, "contacts.csv");
                    System.out.println("Search Result:");
                    System.out.println("----------------------------------------------------------------");
                    for (String[] x : searchResult) {
                        for (int i = 2; i < x.length; i++) {
                            System.out.print(x[i] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                } else if (Input.getInput().equals("2")) {
                    view.Pages.searchWithLogicConnectorPage();
                    Input.setInput();
                    String logicStatement = Input.getInput();
                    String[][] searchResult = Search.searchWithLogicalConnectors(logicStatement, "contacts");
                    System.out.println("Search Result:");
                    System.out.println("----------------------------------------------------------------");
                    for (String[] x : searchResult) {
                        for (int i = 2; i < x.length; i++) {
                            System.out.print(x[i] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                } else if (Input.getInput().equals("-1")) {
                    break;
                }
            } else if (Input.getInput().equals("4")) {
                view.Pages.updatePage();
                Input.setInput();
                String updateChoice = Input.getInput();
                if (updateChoice.equals("1")) {
                    view.Pages.contactList();
                    for (int i = 0; i < Objects.requireNonNull(Contact.getAllContacts()).length - 1; i++) {
                        if (Auth.getUserId() == Integer.parseInt(Contact.getAllContacts()[i][1])) {
                            System.out.printf("%-15s%-30s\n", Contact.getAllContacts()[i][0], Contact.getAllContacts()[i][2] + " " +Contact.getAllContacts()[i][3]);
                        }
                    }
                    view.Pages.updateChoicePage();
                    Input.setInput();
                    String updateId = Input.getInput();
                    String[] contact = Contact.getOneContact(updateId);
                    view.Pages.contactFirstName();
                    System.out.println(Objects.requireNonNull(contact)[2]);
                    view.Pages.contactLastName();
                    System.out.println(contact[3]);
                    view.Pages.contactPhoneNumber();
                    System.out.println(contact[4]);
                    view.Pages.contactAddress();
                    System.out.println(contact[5]);
                    view.Pages.contactFirstName();
                    Input.setInput();
                    String contactFirstName = Input.getInput();
                    view.Pages.contactLastName();
                    Input.setInput();
                    String contactLastName = Input.getInput();
                    String contactName = Input.getInput();
                    view.Pages.contactPhoneNumber();
                    Input.setInput();
                    String contactPhoneNumber = Input.getInput();
                    view.Pages.contactAddress();
                    Input.setInput();
                    String contactAddress = Input.getInput();
                    Contact contactInfo = new Contact(contactFirstName, contactLastName, contactPhoneNumber, contactAddress);
                    Contact.modifyContact(contactInfo, updateId);
                } else if (updateChoice.equals("-1")) {
                    break;
                }
            } else if (Input.getInput().equals("5")) {
                while(true) {
                    view.Pages.removePage();
                    Input.setInput();
                    String removeChoice = Input.getInput();
                    if (removeChoice.equals("1")) {
                        view.Pages.contactList();
                        for (int i = 0; i < Objects.requireNonNull(Contact.getAllContacts()).length - 1; i++) {
                            if (Auth.getUserId() == Integer.parseInt(Contact.getAllContacts()[i][1])) {
                                System.out.printf("%-15s%-30s\n", Contact.getAllContacts()[i][0], Contact.getAllContacts()[i][2]);
                            }
                        }
                        view.Pages.removePage();
                        Input.setInput();
                        String removeId = Input.getInput();
                        Contact.removeContact(removeId);
                    } else if (removeChoice.equals("-1")) {
                        break;
                    }
                }
            } else if (Input.getInput().equals("-1")) {
                break;
            } else {
                System.out.println("Please input the correct number!");
            }
        }
    }

    /**
     * Task Connector Function
     * @param loginSignupInput: the login or signup input
     */
    public static void TodoConnector(String[] loginSignupInput) {
        while(true) {
            view.Pages.todoPage(loginSignupInput[0]);
            Input.setInput();
            if (Input.getInput().equals("1")) {
                view.Pages.todoName();
                Input.setInput();
                String todoName = Input.getInput();
                view.Pages.todoDDL();
                Input.setInput();
                String todoDDL = Input.getInput();
                view.Pages.todoDescription();
                Input.setInput();
                String todoDescription = Input.getInput();
                view.Pages.newTodoPage();
                Input.setInput();
                if (Input.getInput().equals("1")) {
                    if (!checkDateFormat(todoDDL)) {
                        continue;
                    }
                    Todo taskInfo = new Todo(todoName, todoDDL, todoDescription);
                    Todo.createTask(taskInfo);
                } else if (Input.getInput().equals("2")) {
                    view.Pages.todoPage(loginSignupInput[0]);
                }
            } else if (Input.getInput().equals("2")) {
                while (true){
                    view.Pages.lookForPage();
                    Input.setInput();
                    String lookForChoice = Input.getInput();
                    if (lookForChoice.equals("1")) {
                        view.Pages.todoList();
                        for (int i = 0; i < Objects.requireNonNull(Todo.getAllTasks()).length - 1; i++) {
                            if (Auth.getUserId() == Integer.parseInt(Todo.getAllTasks()[i][1])) {
                                System.out.printf("%-15s%-30s\n", Todo.getAllTasks()[i][0], Todo.getAllTasks()[i][2]);
                            }
                        }
                        view.Pages.lookForOne();
                        Input.setInput();
                        String taskID = Input.getInput();
                        String[] task = Todo.getOneTask(taskID);
                        view.Pages.todoName();
                        System.out.println(Objects.requireNonNull(task)[2]);
                        view.Pages.todoDescription();
                        System.out.println(task[3]);
                        view.Pages.todoDDL();
                        System.out.println(task[4]);
                    } else if (lookForChoice.equals("2")) {
                        String[][] allTask = Todo.getAllTasks();
                        for (int i = 0; i < Objects.requireNonNull(allTask).length - 1; i++) {
                            view.Pages.todoName();
                            System.out.println(Objects.requireNonNull(allTask)[i][2]);
                            view.Pages.todoDescription();
                            System.out.println(allTask[i][3]);
                            view.Pages.todoDDL();
                            System.out.println(allTask[i][4]);
                        }
                    } else if (lookForChoice.equals("-1")) {
                        break;
                    }
                }
            } else if (Input.getInput().equals("3")) {
                view.Pages.searchPage();
                Input.setInput();
                if (Input.getInput().equals("1")) {
                    view.Pages.searchByKeywordPage();
                    Input.setInput();
                    String keyword = Input.getInput();
                    String[][] searchResult = Search.search(keyword, "tasks.csv");
                    System.out.println("Search Result:");
                    System.out.println("----------------------------------------------------------------");
                    for (String[] x : searchResult) {
                        for (int i = 2; i < x.length; i++) {
                            System.out.print(x[i] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                } else if (Input.getInput().equals("2")) {
                    view.Pages.searchByTimePage();
                    Input.setInput();
                    String date = Input.getInput();
                    String[][] searchResult = Search.searchByTime(date, "tasks.csv");
                    System.out.println("Search Result:");
                    System.out.println("----------------------------------------------------------------");
                    for (String[] x : searchResult) {
                        for (int i = 2; i < x.length; i++) {
                            System.out.print(x[i] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                } else if (Input.getInput().equals("3")) {
                    view.Pages.searchWithLogicConnectorPage();
                    Input.setInput();
                    String logicStatement = Input.getInput();
                    String[][] searchResult = Search.searchWithLogicalConnectors(logicStatement, "tasks");
                    System.out.println("Search Result:");
                    System.out.println("----------------------------------------------------------------");
                    for (String[] x : searchResult) {
                        for (int i = 2; i < x.length; i++) {
                            System.out.print(x[i] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                } else if (Input.getInput().equals("-1")) {
                    break;
                }
            } else if (Input.getInput().equals("4")) {
                view.Pages.updatePage();
                Input.setInput();
                String updateChoice = Input.getInput();
                if (updateChoice.equals("1")) {
                    view.Pages.todoList();
                    for (int i = 0; i < Objects.requireNonNull(Todo.getAllTasks()).length - 1; i++) {
                        if (Auth.getUserId() == Integer.parseInt(Todo.getAllTasks()[i][1])) {
                            System.out.printf("%-15s%-30s\n", Todo.getAllTasks()[i][0], Todo.getAllTasks()[i][2]);
                        }
                    }
                    view.Pages.updateChoicePage();
                    Input.setInput();
                    String updateId = Input.getInput();
                    String[] tasks = Todo.getOneTask(updateId);
                    view.Pages.todoName();
                    System.out.println(Objects.requireNonNull(tasks)[2]);
                    view.Pages.todoDescription();
                    System.out.println(tasks[3]);
                    view.Pages.todoDDL();
                    System.out.println(tasks[4]);
                    view.Pages.todoName();
                    Input.setInput();
                    String todoName = Input.getInput();
                    view.Pages.todoDescription();
                    Input.setInput();
                    String todoDDL = Input.getInput();
                    view.Pages.todoDDL();
                    Input.setInput();
                    String todoDescription = Input.getInput();
                    Todo taskInfo = new Todo(todoName, todoDDL, todoDescription);
                    Todo.modifyTask(taskInfo, updateId);
                } else if (updateChoice.equals("-1")) {
                    break;
                }
            } else if (Input.getInput().equals("5")) {
                while(true) {
                    view.Pages.removePage();
                    Input.setInput();
                    String removeChoice = Input.getInput();
                    if (removeChoice.equals("1")) {
                        view.Pages.todoList();
                        for (int i = 0; i < Objects.requireNonNull(Todo.getAllTasks()).length - 1; i++) {
                            if (Auth.getUserId() == Integer.parseInt(Todo.getAllTasks()[i][1])) {
                                System.out.printf("%-15s%-30s\n", Todo.getAllTasks()[i][0], Todo.getAllTasks()[i][2]);
                            }
                        }
                        view.Pages.removePage();
                        Input.setInput();
                        String removeId = Input.getInput();
                        Todo.removeTask(removeId);
                    } else if (removeChoice.equals("-1")) {
                        break;
                    }
                }
            } else if (Input.getInput().equals("-1")) {
                break;
            } else {
                System.out.println("Please input the correct number!");
            }
        }
    }

    /**
     * Event Connector Function
     * @param loginSignupInput: the login or signup input
     */
    public static void EventConnector(String[] loginSignupInput) {
        while(true) {
            view.Pages.eventPage(loginSignupInput[0]);
            Input.setInput();
            if (Input.getInput().equals("1")) {
                view.Pages.eventName();
                Input.setInput();
                String eventName = Input.getInput();
                view.Pages.eventStartTime();
                Input.setInput();
                String eventStartTime = Input.getInput();
                view.Pages.eventAlarm();
                Input.setInput();
                String eventAlarm = Input.getInput();
                view.Pages.eventDescription();
                Input.setInput();
                String eventDescription = Input.getInput();
                view.Pages.newEventPage();
                Input.setInput();
                if (Input.getInput().equals("1")) {
                    if (!checkDateFormat(eventStartTime) || !checkDateFormat(eventAlarm)) {
                        continue;
                    }
                    Event eventInfo = new Event(eventName, eventStartTime, eventAlarm, eventDescription);
                    Event.createEvent(eventInfo);
                } else if (Input.getInput().equals("2")) {
                    view.Pages.eventPage(loginSignupInput[0]);
                }
            } else if (Input.getInput().equals("2")) {
                while (true){
                    view.Pages.lookForPage();
                    Input.setInput();
                    String lookForChoice = Input.getInput();
                    if (lookForChoice.equals("1")) {
                        view.Pages.eventList();
                        for (int i = 0; i < Event.getAllEvents().length - 1; i++) {
                            if (Auth.getUserId() == Integer.parseInt(Event.getAllEvents()[i][1])) {
                                System.out.printf("%-15s%-30s\n", Event.getAllEvents()[i][0], Event.getAllEvents()[i][2]);
                            }
                        }
                        view.Pages.lookForOne();
                        Input.setInput();
                        String eventID = Input.getInput();
                        String[] event = Event.getOneEvent(eventID);
                        view.Pages.eventName();
                        System.out.println(event[2]);
                        view.Pages.eventStartTime();
                        System.out.println(event[4]);
                        view.Pages.eventAlarm();
                        System.out.println(event[5]);
                        view.Pages.eventDescription();
                        System.out.println(event[3]);
                    } else if (lookForChoice.equals("2")) {
                        String[][] allEvent = Event.getAllEvents();
                        for (int i = 0; i < allEvent.length - 1; i++) {
                            view.Pages.eventName();
                            System.out.println(allEvent[i][2]);
                            view.Pages.eventStartTime();
                            System.out.println(allEvent[i][4]);
                            view.Pages.eventAlarm();
                            System.out.println(allEvent[i][5]);
                            view.Pages.eventDescription();
                            System.out.println(allEvent[i][3]);
                        }
                    } else if (lookForChoice.equals("-1")) {
                        break;
                    }
                }
            } else if (Input.getInput().equals("3")) {
                view.Pages.searchPage();
                Input.setInput();
                if (Input.getInput().equals("1")) {
                    view.Pages.searchByKeywordPage();
                    Input.setInput();
                    String keyword = Input.getInput();
                    String[][] searchResult = Search.search(keyword, "events.csv");
                    System.out.println("Search Result:");
                    System.out.println("----------------------------------------------------------------");
                    for (String[] x : searchResult) {
                        for (int i = 2; i < x.length; i++) {
                            System.out.print(x[i] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                } else if (Input.getInput().equals("2")) {
                    view.Pages.searchByTimePage();
                    Input.setInput();
                    String date = Input.getInput();
                    String[][] searchResult = Search.searchByTime(date, "events.csv");
                    System.out.println("Search Result:");
                    System.out.println("----------------------------------------------------------------");
                    for (String[] x : searchResult) {
                        for (int i = 2; i < x.length; i++) {
                            System.out.print(x[i] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                } else if (Input.getInput().equals("3")) {
                    view.Pages.searchWithLogicConnectorPage();
                    Input.setInput();
                    String logicStatement = Input.getInput();
                    String[][] searchResult = Search.searchWithLogicalConnectors(logicStatement, "events");
                    System.out.println("Search Result:");
                    System.out.println("----------------------------------------------------------------");
                    for (String[] x : searchResult) {
                        for (int i = 2; i < x.length; i++) {
                            System.out.print(x[i] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                } else if (Input.getInput().equals("-1")) {
                    break;
                }
            } else if (Input.getInput().equals("4")) {
                view.Pages.updatePage();
                Input.setInput();
                String updateChoice = Input.getInput();
                if (updateChoice.equals("1")) {
                    view.Pages.eventList();
                    for (int i = 0; i < Objects.requireNonNull(Event.getAllEvents()).length - 1; i++) {
                        if (Auth.getUserId() == Integer.parseInt(Event.getAllEvents()[i][1])) {
                            System.out.printf("%-15s%-30s\n", Event.getAllEvents()[i][0], Event.getAllEvents()[i][2]);
                        }
                    }
                    view.Pages.updateChoicePage();
                    Input.setInput();
                    String updateId = Input.getInput();
                    String[] events = Event.getOneEvent(updateId);
                    view.Pages.eventName();
                    System.out.println(Objects.requireNonNull(events)[2]);
                    view.Pages.eventDescription();
                    System.out.println(events[3]);
                    view.Pages.eventStartTime();
                    System.out.println(events[4]);
                    view.Pages.eventAlarm();
                    System.out.println(events[5]);
                    view.Pages.eventName();
                    Input.setInput();
                    String eventName = Input.getInput();
                    view.Pages.eventDescription();
                    Input.setInput();
                    String eventStartTime = Input.getInput();
                    view.Pages.eventStartTime();
                    Input.setInput();
                    String eventAlarm = Input.getInput();
                    view.Pages.eventAlarm();
                    Input.setInput();
                    String eventDescription = Input.getInput();
                    Event eventInfo = new Event(eventName, eventStartTime, eventAlarm, eventDescription);
                    Event.modifyEvent(eventInfo, updateId);
                } else if (updateChoice.equals("-1")) {
                    break;
                }
            } else if (Input.getInput().equals("5")) {
                while(true) {
                    view.Pages.removePage();
                    Input.setInput();
                    String removeChoice = Input.getInput();
                    if (removeChoice.equals("1")) {
                        view.Pages.eventList();
                        for (int i = 0; i < Objects.requireNonNull(Event.getAllEvents()).length - 1; i++) {
                            if (Auth.getUserId() == Integer.parseInt(Event.getAllEvents()[i][1])) {
                                System.out.printf("%-15s%-30s\n", Event.getAllEvents()[i][0], Event.getAllEvents()[i][2]);
                            }
                        }
                        view.Pages.removePage();
                        Input.setInput();
                        String removeId = Input.getInput();
                        Event.removeEvent(removeId);
                    } else if (removeChoice.equals("-1")) {
                        break;
                    }
                }
            } else if (Input.getInput().equals("-1")) {
                break;
            } else {
                System.out.println("Please input the correct number!");
            }
        }
    }

    /**
     * Import Connector Function
     */
    public static void importConnector() {
        while(true) {
            view.Pages.importPage();
            Input.setInput();
            String importChoice = Input.getInput();
            if (importChoice.equals("1")) {
                view.Pages.importFileNamePage();
                Input.setInput();
                String fileName = Input.getInput();
                Import.importPIMFile(fileName);
                break;
            } else if (importChoice.equals("-1")) {
                break;
            }
        }
    }

    /**
     * Export Connector Function
     */
    public static void exportConnector() {
        while(true) {
            view.Pages.exportPage();
            Input.setInput();
            String exportChoice = Input.getInput();
            if (exportChoice.equals("1")) {
                Export.export();
                System.out.println("Export Successfully!");
                break;
            } else if (exportChoice.equals("-1")) {
                break;
            }
        }
    }

    /**
     * Menu Connector Function
     * @param loginSignupInput: the login or signup input
     */
    public static void MenuConnector(String[] loginSignupInput) {
        while(true) {
            view.Pages.mainPage(loginSignupInput[0]);
            Input.setInput();
            String menuChoice = Input.getInput();

            // Notes
            if (menuChoice.equals("1")) {
                NoteConnector(loginSignupInput);
            }

            // Contacts
            else if (menuChoice.equals("2")) {
                ContactConnector(loginSignupInput);
            }

            // To-do Lists
            else if (menuChoice.equals("3")) {
                TodoConnector(loginSignupInput);
            }

            // Events
            else if (menuChoice.equals("4")) {
                EventConnector(loginSignupInput);
            }

            // Load .PIM File
            else if (menuChoice.equals("5")) {
                try {
                    importConnector();
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                    e.printStackTrace();
                }
            }

            // Export .PIM File
            else if (menuChoice.equals("6")) {
                try {
                    exportConnector();
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                    e.printStackTrace();
                }
            }

            // Exit System
            else if (menuChoice.equals("-1")) {
                System.out.println("Exit System!");
                break;
            }

            else {
                System.out.println("Please input the correct number!");
            }
        }
    }

    /**
     * Main Connector Function
     */
    public static void mainConnector() {
        view.Pages.loginPage();
        String[] loginSignupInput = loginSignupConnector();
        if (Auth.login(loginSignupInput[0], loginSignupInput[1])) {
            MenuConnector(loginSignupInput);
        } else {
            System.out.println("Login Failed!");
        }

    }

}