package controller;

import model.SimpleDatabase;
import static controller.Auth.getUserId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Note {
    private String noteTitle;
    private String noteContent;
    private String lastModifyTime;
    private int userId;

    /**
     * Note Contract
     * @param noteTitle: the title of note
     * @param noteContent: the content of note
     */
    public Note(String noteTitle, String noteContent) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.lastModifyTime = String.valueOf(dateTime);
        this.userId = getUserId();
    }

    public Note(String noteTitle, String noteContent, String lastModifyTime) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.lastModifyTime = lastModifyTime;
        this.userId = getUserId();
    }

    /**
     * Create Note Function (Public)
     * @param noteInfo: the note info
     */
    public static void createNote(Note noteInfo) {
        createNote(noteInfo.noteTitle, noteInfo.noteContent, noteInfo.lastModifyTime, noteInfo.userId);
    }

    /**
     * Create Note Function
     * @param noteTitle: The title of the note
     * @param noteContent: The content of the note
     * @param lastModifyTime: The last modify time of the note
     * @param userId: The ID of the user
     */
    private static void createNote(String noteTitle, String noteContent, String lastModifyTime, int userId) {
        try {
            if (noteTitle.isEmpty() || noteContent.isEmpty() || lastModifyTime.isEmpty()) {
                System.out.println("Please enter all information!");
                return;
            }

            int noteId = SimpleDatabase.getNewID("notes.csv");

            String[][] newNoteData = {
                    {String.valueOf(noteId), String.valueOf(userId), noteTitle, noteContent, lastModifyTime}
            };

            new SimpleDatabase("insert", "notes.csv", newNoteData);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }

    /**
     * Get All Notes Function
     */
    public static String[][] getAllNotes() {
        try{
            String[][] data = SimpleDatabase.get("notes.csv");
            String userId = String.valueOf(getUserId());
            // Filter data based on userID
            ArrayList<String[]> filteredList = new ArrayList<>();
            for (int i = 0; i < data.length - 1; i++) {
                if (data[i][1].equals(userId)) {
                    String[] filteredLine = { data[i][0], data[i][1], data[i][2], data[i][3], data[i][4]};
                    filteredList.add(filteredLine);
                }
            }

            // Convert filtered contacts to 2D String array
            String[][] filteredData = new String[filteredList.size() + 1][];
            filteredData = filteredList.toArray(filteredData);

            return filteredData;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
        return null;
    }

    /**
     * Get One Note Function
     * @return String[]: the data of one note
     */
    public static String[] getOneNote(String noteId) {
        try {
            String[][] data = SimpleDatabase.get("notes.csv");

            for (int i = 0; i < data.length; i++) {
                if (data[i][0].equals(noteId) && data[i][1].equals(String.valueOf(Auth.getUserId()))) {
                    return data[i];
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
            return null;
        }

        return null;
    }


    /**
     * Modify Note Function (Public)
     * @param noteInfo: the note info
     * @param noteId: the id of note
     */
    public static void modifyNote(Note noteInfo, String noteId) {
        modifyNote(noteInfo.noteTitle, noteInfo.noteContent, noteInfo.lastModifyTime, noteInfo.userId, noteId);
    }

    /**
     * Modify Note Function
     * @param noteTitle: The title of the note
     * @param noteContent: The content of the note
     * @param lastModifyTime: The last modify time of the note
     * @param userId: The ID of the user
     * @param noteId: The ID of the note
     */
    private static void modifyNote(String noteTitle, String noteContent, String lastModifyTime, int userId, String noteId) {
        try {
            String[] dataWantUpdate = {noteId, String.valueOf(userId), noteTitle, noteContent, lastModifyTime};
            new SimpleDatabase("update", "notes.csv", Integer.parseInt(noteId), dataWantUpdate);
            System.out.println("Update Successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }


    /**
     * Remove Note Function (Public)
     * @param noteId: the id of note
     */
    public static void removeNote(String noteId) {
        removeNote(Integer.parseInt(noteId));
    }

    /**
     * Remove Note Function
     * @param noteId: The ID of the note
     */
    private static void removeNote(int noteId) {
        try {
            new SimpleDatabase("remove", "notes.csv", Auth.getUserId(), noteId);
            System.out.println("Remove Successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }
}
