package model;

import controller.database.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User implements SQLModel {
    private String accountID;
    private boolean accountStatus;
    private String noticeString;
    private int reserveCount;
    private final int MAX_WANT_BOOK = 8;
    
    public User(String accountID) {
        this.accountID = accountID;
    }

    public User(String accountID, boolean accountStatus, String noticeString) {
        this.accountID = accountID;
        this.accountStatus = accountStatus;
        this.noticeString = StringFilter(noticeString);
    }

    private String StringFilter(String inString) {
        String[] strList = new String[]{"'", "\"", ";", "=", ">", "<","*"};
        String newStr = "_";
        for (String str : strList) {
            if (inString.contains(str)) {
                inString = inString.replace(str, newStr);
            }
        }
        return inString;
    }

    /**
     * getter methods
     */
    public String getAccountID() {
        return accountID;
    }

    public boolean getAccountStatus() {
        return accountStatus;
    }

    public String getNoticeString(){
        return noticeString;
    }

    /**
     * setter methods
     */
    public void setAccountID(String inAccountID) {
        this.accountID = inAccountID;
    }

    public void setAccountStatus(boolean inAccountStatus) {
        this.accountStatus = inAccountStatus;
    }

    public void setNoticeString(String noticeString){this.noticeString = noticeString;}

    public int getReserveCount() {
        return reserveCount;
    }

    /**
     *  show related information
     */
    public String showInfo(){
        return "[Account ID]: " + getAccountID() + " [Account Status]: " + getAccountStatus() +" [Notice String]: " + getNoticeString();
    }

    /**
     * increment the bookReserveNum by 1
     */
    public boolean increaseReserveCount() {
        if (reserveCount < MAX_WANT_BOOK) {
            reserveCount++;
            return true;
        }
        return false;
    }

    /**
     * decrement the bookReserveNum by 1
     */
    public boolean decreaseReserveCount() {
        if (reserveCount > 0) {
            reserveCount--;
            return true;
        }
        return false;
    }

    /**
     * Set the bookReserveNum
     */
    public void setReserveCount(int inNum) {
        if (inNum>=0) {
            reserveCount = inNum;
        }
    }

    /**
     * Implement the SQLModel interface methods
     */
    public SQLModel pullFromDatabase() throws SQLException {
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        String sql = "SELECT * FROM USER_ACCOUNT WHERE accountID = " + accountID;

        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                accountID = resultSet.getInt("accountID")+ "";
                String temp = resultSet.getString("accountStatus").trim();
                accountStatus = temp.equals("T");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Implement the SQLModel interface methods
     */
    public SQLModel pushToDatabase() throws SQLException {
        DataBase db = DataBase.getDataBase();
        if (db.contains("USER_ACCOUNT","accountID",Integer.parseInt(accountID))){
            String temp = accountStatus ? "T" : "F";
            String sql = "UPDATE USER_ACCOUNT SET accountStatus = \'" + temp + "\'"+ ","+
                    "NOTIFICATION = \'" + noticeString + "\'"+
                    "WHERE accountID = " + accountID;
            try {
                db.update(sql);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        else {
            String temp = accountStatus ? "T" : "F";
            String sql = "INSERT INTO USER_ACCOUNT VALUES (" + accountID + ",\'" + temp + "\',\'" + noticeString + "\')";
            try {
                db.insert(sql);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     * Implement the SQLModel interface methods
     */
    public void deleteFromDatabase () throws SQLException {
        DataBase db = DataBase.getDataBase();
        String sql = "DELETE FROM USER_ACCOUNT WHERE accountID = " + accountID;
        try {
            db.query(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
