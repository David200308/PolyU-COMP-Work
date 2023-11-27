package controller;

import model.SimpleDatabase;

import java.util.ArrayList;

import static controller.Auth.getUserId;

public class Event {
    private String eventName;
    private String eventStartTime;
    private String eventAlarm;
    private String eventDescription;
    private int userId;

    /**
     * Event Contract
     * @param eventName: the name of event
     * @param eventStartTime: the start time of event
     * @param eventAlarm: the alarm of event
     * @param eventDescription: the description of event
     */
    public Event(String eventName, String eventStartTime, String eventAlarm, String eventDescription) {
        this.eventName = eventName;
        this.eventStartTime = eventStartTime;
        this.eventAlarm = eventAlarm;
        this.eventDescription = eventDescription;
        this.userId = getUserId();
    }


    /**
     * Create Event Function (Public)
     * @param eventInfo: the event info
     */
    public static void createEvent(Event eventInfo) {
        createEvent(eventInfo.eventName, eventInfo.eventStartTime, eventInfo.eventAlarm, eventInfo.eventDescription, eventInfo.userId);
    }

    /**
     * Create Event Function
     * @param eventName: The name of the event
     * @param eventStartTime: The start time of the event
     * @param eventAlarm: The alarm of the event
     * @param eventDescription: The description of the event
     */
    private static void createEvent(String eventName, String eventStartTime, String eventAlarm, String eventDescription, int userId) {
        try {
            if (eventName.isEmpty() || eventStartTime.isEmpty() || eventAlarm.isEmpty() || eventDescription.isEmpty()) {
                System.out.println("Please enter all information!");
                return;
            }

            int eventId = SimpleDatabase.getNewID("events.csv");

            String[][] newEventData = {
                    {String.valueOf(eventId), String.valueOf(userId), eventName, eventDescription, eventStartTime, eventAlarm}
            };

            new SimpleDatabase("insert", "events.csv", newEventData);

        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }

    /**
     * Get All Events Function
     */
    public static String[][] getAllEvents() {
        try{
            String[][] data = SimpleDatabase.get("events.csv");
            String userId = String.valueOf(getUserId());
            // Filter data based on userID
            ArrayList<String[]> filteredList = new ArrayList<>();
            for (int i = 0; i < data.length - 1; i++) {
                if (data[i][1].equals(userId)) {
                    String[] filteredLine = { data[i][0], data[i][1], data[i][2], data[i][3], data[i][4], data[i][5] };
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
     * Get One Event Function
     * @param eventId: the id of event
     * @return: the event data
     */
    public static String[] getOneEvent(String eventId) {
        try{
            String[][] data = SimpleDatabase.get("events.csv");

            for (int i = 0; i < data.length - 1; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    if (data[i][0].equals(eventId) & data[i][1].equals(String.valueOf(Auth.getUserId()))) {
                        return data[i];
                    }
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
     * Modify Event Function (Public)
     * @param eventInfo: the event info
     * @param eventId: the id of event
     */
    public static void modifyEvent(Event eventInfo, String eventId) {
        modifyEvent(eventInfo.eventName, eventInfo.eventStartTime, eventInfo.eventAlarm, eventInfo.eventDescription, eventInfo.userId, eventId);
    }

    /**
     * Modify Event Function
     * @param eventName: The name of the event
     * @param eventStartTime: The start time of the event
     * @param eventAlarm: The alarm of the event
     * @param eventDescription: The description of the event
     * @param userId: The user id of the event
     * @param eventId: The id of the event
     */
    private static void modifyEvent(String eventName, String eventStartTime, String eventAlarm, String eventDescription, int userId, String eventId) {
        try{
            String [] dataWantUpdate = {eventId, String.valueOf(userId), eventName, eventDescription, eventStartTime, eventAlarm};
            new SimpleDatabase("update", "events.csv", Integer.parseInt(eventId), dataWantUpdate);
            System.out.println("Update Successfully!");
        }catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }


    /**
     * Remove Event Function (Public)
     * @param eventId: the id of event
     */
    public static void removeEvent(String eventId) {
        removeEvent(Integer.parseInt(eventId));
    }

    /**
     * Remove Event Function
     * @param eventId: the id of event
     */
    private static void removeEvent(int eventId) {
        try {
            new SimpleDatabase("remove", "events.csv", Auth.getUserId(), eventId);
            System.out.println("Remove Successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }

}
