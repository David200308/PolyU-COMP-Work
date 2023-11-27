package controller;

import model.SimpleDatabase;

import java.io.FileWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Export {
    /**
     * Export Function
     */
    public static void export() {
        String contacts = "contacts.csv";
        String notes = "notes.csv";
        String events = "events.csv";
        String tasks = "tasks.csv";

        String userName = Auth.getUserName();

        try {
            String[][] contactsData = SimpleDatabase.get(contacts);
            String[][] notesData = SimpleDatabase.get(notes);
            String[][] eventsData = SimpleDatabase.get(events);
            String[][] tasksData = SimpleDatabase.get(tasks);

            String exportBaseUrl = "./src/pim_file";
            File dir = new File(exportBaseUrl);
            if (!dir.exists()) dir.mkdirs();

            File file = new File(exportBaseUrl + "/" + userName + "_export.pim");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter exportFile = new FileWriter(file);
            System.out.println("Contacts");
            exportFile.write("Contacts\n");
            for (int i = 0; i < contactsData.length - 1; i++) {
                if (String.valueOf(Auth.getUserId()).equals(contactsData[i][1])) {
                    for (int j = 0; j < contactsData[i].length; j++) {
                        if (j == 0) {
                            System.out.print(contactsData[i][j] + " | ");
                            exportFile.write(contactsData[i][j]);
                        } else if (j == contactsData[i].length - 1) {
                            System.out.print(contactsData[i][j] + " | ");
                            exportFile.write("," + contactsData[i][j]);
                        } else {
                            System.out.print(contactsData[i][j] + " | ");
                            exportFile.write("," + contactsData[i][j]);
                        }
                    }
                    System.out.println();
                    exportFile.write("\n");
                }
            }
            exportFile.write("End of Contacts");
            exportFile.write("\n");
            System.out.println("Notes");
            exportFile.write("Notes\n");
            for (int i = 0; i < notesData.length - 1; i++) {
                if (String.valueOf(Auth.getUserId()).equals(notesData[i][1])) {
                    for (int j = 0; j < notesData[i].length; j++) {
                        if (j == 0) {
                            System.out.print(notesData[i][j] + " | ");
                            exportFile.write(notesData[i][j]);
                        } else if (j == notesData[i].length - 1) {
                            System.out.print(notesData[i][j] + " | ");
                            exportFile.write("," + notesData[i][j]);
                        } else {
                            System.out.print(notesData[i][j] + " | ");
                            exportFile.write("," + notesData[i][j]);
                        }
                    }
                    System.out.println();
                    exportFile.write("\n");
                }
            }
            exportFile.write("End of Notes");
            exportFile.write("\n");
            System.out.println("Events");
            exportFile.write("Events\n");
            for (int i = 0; i < eventsData.length - 1; i++) {
                if (String.valueOf(Auth.getUserId()).equals(eventsData[i][1])) {
                    for (int j = 0; j < eventsData[i].length; j++) {
                        if (j == 0) {
                            System.out.print(eventsData[i][j] + " | ");
                            exportFile.write(eventsData[i][j]);
                        } else if (j == eventsData[i].length - 1) {
                            System.out.print(eventsData[i][j] + " | ");
                            exportFile.write("," + eventsData[i][j]);
                        } else {
                            System.out.print(eventsData[i][j] + " | ");
                            exportFile.write("," + eventsData[i][j]);
                        }
                    }
                    System.out.println();
                    exportFile.write("\n");
                }
            }
            exportFile.write("End of Events");
            exportFile.write("\n");
            System.out.println("Tasks");
            exportFile.write("Tasks\n");
            for (int i = 0; i < tasksData.length - 1; i++) {
                if (String.valueOf(Auth.getUserId()).equals(tasksData[i][1])) {
                    for (int j = 0; j < tasksData[i].length; j++) {
                        if (j == 0) {
                            System.out.print(tasksData[i][j] + " | ");
                            exportFile.write(tasksData[i][j]);
                        } else if (j == tasksData[i].length - 1) {
                            System.out.print(tasksData[i][j] + " | ");
                            exportFile.write("," + tasksData[i][j]);
                        } else {
                            System.out.print(tasksData[i][j] + " | ");
                            exportFile.write("," + tasksData[i][j]);
                        }
                    }
                    System.out.println();
                    exportFile.write("\n");
                }
            }
            exportFile.write("End of Tasks");
            exportFile.write("\n");
            exportFile.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }
}
