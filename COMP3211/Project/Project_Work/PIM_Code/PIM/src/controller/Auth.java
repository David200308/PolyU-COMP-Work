package controller;

import model.SimpleDatabase;
public class Auth {
    private static int userId;
    private static String username;

    /**
     * Verify Account Function - Login (Public)
     * @param userName: the username of account
     * @param password: the password of account
     */
    public static boolean login(String userName, String password) {
        return verifyAccount(userName, password);
    }

    /**
     * Verify Account Function - Login
     * @param userName: the username of account
     * @param password: the password of account
     */
    private static boolean verifyAccount(String userName, String password) {
        try {
            String[][] data = SimpleDatabase.get("user.csv");
            for (int i = 0; i < data.length; i++) {
                if (data[i][1].equals(userName) && data[i][2].equals(password)) {
                    System.out.println("Login Success!");
                    userId = Integer.parseInt(data[i][0]);
                    username = userName;
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while verifying the account:");
//            e.printStackTrace();
            System.out.println("Please try again or contact support.");
            return false;
        }
        return false;
    }

    /**
     * Create Account Function - Signup (Public)
     * @param userName: the username of account
     * @param password: the password of account
     */
    public static void signup(String userName, String password) {
        createAccount(userName, password);
    }

    /**
     * Create Account Function - Signup
     * @param userName: the username of account
     * @param password: the password of account
     */
    private static void createAccount(String userName, String password) {
        try {
            int userId = SimpleDatabase.getNewID("user.csv");

            String[][] newUserData = {
                    {String.valueOf(userId), userName, password}
            };

            new SimpleDatabase("insert", "user.csv", newUserData);

        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.out.println("Please try again!");
        }
    }


    /**
     * Get User ID Function
     * @return userId: the user id of account
     */
    public static int getUserId() {
        return userId;
    }

    /**
     * Get User Name Function
     * @return username: the username of account
     */
    public static String getUserName() {
        return username;
    }
}