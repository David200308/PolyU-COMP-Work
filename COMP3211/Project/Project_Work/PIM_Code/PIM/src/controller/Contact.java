package controller;

import model.SimpleDatabase;

import static controller.Auth.getUserId;

import java.util.ArrayList;


public class Contact {
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String address;
    private int userId;

    /**
     * Contact Contract
     * @param lastName: the last name of contact
     * @param firstName: the first name of contact
     * @param phoneNumber: the phone number of contact
     * @param address: the address of contact
     */
    public Contact(String firstName, String lastName, String phoneNumber, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.userId = getUserId();
    }

    /**
     * Create Contact Function (Public)
     * @param contactInfo: the contact info
     */
    public static void createContact(Contact contactInfo) {
        createContact(contactInfo.firstName, contactInfo.lastName, contactInfo.phoneNumber, contactInfo.address, contactInfo.userId);
    }

    /**
     * Create Contact Function
     * @param lastName: The last name of the contact
     * @param firstName: The first name of the contact
     * @param phoneNumber: The Phone number of the contact
     * @param address: The address of the contact
     * @param userId: The ID of the user
     */
    private static void createContact(String firstName, String lastName, String phoneNumber, String address, int userId) {
        try {
            if (lastName.isEmpty() || firstName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
                System.out.println("Please enter all information!");
                return;
            }

            int contactId = SimpleDatabase.getNewID("contacts.csv");

            String[][] newContactData = {
                    {String.valueOf(contactId), String.valueOf(userId), firstName, lastName, phoneNumber, address}
            };

            new SimpleDatabase("insert", "contacts.csv", newContactData);

        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }

    /**
     * Get All Contacts Function
     */
    public static String[][] getAllContacts() {
        try{
            String[][] data = SimpleDatabase.get("contacts.csv");
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
     * Get One Contact Function
     * @param contactId: the id of contact
     * @return String[]: the contact data
     */
    public static String[] getOneContact(String contactId) {
        try{
            String[][] data = SimpleDatabase.get("contacts.csv");

            for (int i = 0; i < data.length; i++) {
                if (data[i][0].equals(contactId) & data[i][1].equals(String.valueOf(getUserId()))) {
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
     * Modify Contact Function (Public)
     * @param contactInfo: the contact info
     * @param contactId: the id of contact
     */
    public static void modifyContact(Contact contactInfo, String contactId) {
        modifyContact(contactInfo.firstName, contactInfo.lastName, contactInfo.phoneNumber, contactInfo.address, contactInfo.userId, contactId);
    }

    /**
     * Modify Contact Function
     * @param lastName: The last name of the contact
     * @param firstName: The first name of the contact
     * @param phoneNumber: The Phone number of the contact
     * @param address: The address of the contact
     * @param userId: The ID of the user
     * @param contactId: The ID of the contact
     */
    private static void modifyContact(String firstName, String lastName, String phoneNumber, String address, int userId, String contactId) {
        try {
            String[] dataWantUpdate = {contactId, String.valueOf(userId), firstName, lastName, phoneNumber, address};
            new SimpleDatabase("update", "contacts.csv", Integer.parseInt(contactId), dataWantUpdate);
            System.out.println("Update Successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }

    /**
     * Remove Contact Function (Public)
     * @param contactId: the id of contact
     */
    public static void removeContact(String contactId) {
        removeContact(Integer.parseInt(contactId));
    }

    /**
     * Remove Contact Function
     * @param contactId: The ID of the contact
     */
    private static void removeContact(int contactId) {
        try {
            new SimpleDatabase("remove", "contacts.csv", getUserId(), contactId);
            System.out.println("Remove Successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }

}
