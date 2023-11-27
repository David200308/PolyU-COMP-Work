package controller;

import model.SimpleDatabase;

import java.util.ArrayList;

import static controller.Auth.getUserId;

public class Todo {
    private String taskName;
    private String taskDDL;
    private String taskDescription;
    private int userId;

    /**
     * Todo Contract
     *
     * @param taskName:        the name of task
     * @param taskDDL:         the deadline of task
     * @param taskDescription: the description of task
     */
    public Todo(String taskName, String taskDDL, String taskDescription) {
        this.taskName = taskName;
        this.taskDDL = taskDDL;
        this.taskDescription = taskDescription;
        this.userId = getUserId();
    }

    /**
     * Create Task Function (Public)
     * @param taskInfo: the task info
     */
    public static void createTask(Todo taskInfo) {
        createTask(taskInfo.taskName, taskInfo.taskDDL, taskInfo.taskDescription, taskInfo.userId);
    }

    /**
     * Create Task Function
     * @param taskName: the name of task
     * @param taskDDL: the deadline of task
     * @param taskDescription: the description of task
     * @param userId: the id of user
     */
    private static void createTask(String taskName, String taskDDL, String taskDescription, int userId) {
        try {
            if (taskName.isEmpty() || taskDDL.isEmpty() || taskDescription.isEmpty()) {
                System.out.println("Please enter all information!");
                return;
            }

            int taskId = SimpleDatabase.getNewID("tasks.csv");

            String[][] newTaskData = {
                    {String.valueOf(taskId), String.valueOf(userId), taskName, taskDescription, taskDDL}
            };

            new SimpleDatabase("insert", "tasks.csv", newTaskData);

        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }

    /**
     * Get All Tasks Function
     */
    public static String[][] getAllTasks() {
        try {
            String[][] data = SimpleDatabase.get("tasks.csv");
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
     * Get One Task Function
     *
     * @param taskId: the id of task
     * @return String[]: the task data
     */
    public static String[] getOneTask(String taskId) {
        try {
            String[][] data = SimpleDatabase.get("tasks.csv");

            for (int i = 0; i < data.length; i++) {
                if (data[i][0].equals(taskId) & data[i][1].equals(String.valueOf(Auth.getUserId()))) {
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
     * Modify Task Function (Public)
     * @param taskInfo: the task info
     * @param taskId:  the id of task
     */
    public static void modifyTask(Todo taskInfo, String taskId) {
        modifyTask(taskInfo.taskName, taskInfo.taskDDL, taskInfo.taskDescription, taskInfo.userId, taskId);
    }

    /**
     * Modify Task Function
     * @param taskName: the name of task
     * @param taskDDL: the deadline of task
     * @param taskDescription: the description of task
     * @param userId: the id of user
     * @param taskId: the id of task
     */
    private static void modifyTask(String taskName, String taskDDL, String taskDescription, int userId, String taskId) {
        try {
            String[] dataWantUpdate = {taskId, String.valueOf(userId), taskName, taskDescription, taskDDL};

            new SimpleDatabase("update", "tasks.csv", Integer.parseInt(taskId), dataWantUpdate);
            System.out.println("Update Successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }


    /**
     * Remove Task Function (Public)
     * @param taskId: the id of task
     */
    public static void removeTask(String taskId) {
        removeTask(Integer.parseInt(taskId));
    }

    /**
     * Remove Task Function
     * @param taskId: the id of task
     */
    private static void removeTask(int taskId) {
        try {
            new SimpleDatabase("remove", "tasks.csv", Auth.getUserId(), taskId);
            System.out.println("Remove Successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }

}