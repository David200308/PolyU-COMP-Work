package model;

import controller.database.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WantBook implements SQLModel {
    private String accountID;
    private String wantISBN;
    private int size;

    private int wantYear;
    private int wantMonth;
    private int wantDay;

    public WantBook(String inAccountID, String InWantISBN, int inWantYear, int inWantMonth, int inWantDay) {
        this.accountID = inAccountID;
        this.wantISBN = InWantISBN;
        this.wantYear = inWantYear;
        this.wantMonth = inWantMonth;
        this.wantDay = inWantDay;
        this.size = 0;
    }

    /**
     *  show related information
     */
    public String showInfo(){
        return " [Account ID]: " + accountID + " [ISBN]: " + wantISBN + " [Year]: " + wantYear + " [Month]: " + wantMonth + " [Day]: " + wantDay;
    }

    /**
     * getter methods
     */
    public String getUserAccountID() {
        return this.accountID;
    }

    public String getWantISBNs() {
        return this.wantISBN;
    }

    public String getWantDate() {
        String yyyy = wantYear+"";
        String mm = wantMonth<10?"0"+wantMonth:wantMonth+"";
        String dd = wantDay<10?"0"+wantDay:wantDay+"";
        return yyyy+"-"+mm+"-"+dd;
    }

    /**
     * Implement the SQLModel interface methods
     */
    public SQLModel pullFromDatabase() throws SQLException {
        DataBase db = DataBase.getDataBase();
        ResultSet resultSet;
        ResultSet resultSet2;

        String sql =
                "SELECT accountID, ISBN, wantTime FROM WANT_BOOK WHERE ISBN =\'" + wantISBN+"\' AND accountID = " + accountID;
        String sql2 = "SELECT COUNT(*) FROM WANT_BOOK WHERE accountID = " + accountID;
        try{
            resultSet = db.query(sql);
            resultSet2 = db.query(sql2);
            while (resultSet.next()){
                wantISBN = resultSet.getString("ISBN").trim();
                accountID = resultSet.getInt("accountID")+"";
                String temp = resultSet.getString("wantTime").trim();
                String[] temp2 = temp.split("-");
                wantYear = Integer.parseInt(temp2[0]);
                wantMonth = Integer.parseInt(temp2[1]);
                wantDay = Integer.parseInt(temp2[2]);
            }
            while (resultSet2.next()){
                size = resultSet2.getInt(1);
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
        if (!db.contains("WANT_BOOK","ISBN","accountID",wantISBN,accountID)){
            String sql = "INSERT INTO WANT_BOOK VALUES(" +accountID  + ",\'" + wantISBN + "\',\'" + getWantDate()+"\')";
            db.insert(sql);
        }

        return this;
    }

    /**
     * Implement the SQLModel interface methods
     */
    public void deleteFromDatabase () {
        DataBase db = DataBase.getDataBase();
        String sql = "DELETE FROM WANT_BOOK WHERE ISBN = \'" + wantISBN + "\' AND accountID = " + accountID;
        try {
            db.query(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check whether is the same wantBook
     * @param inw
     * @return
     */
    public boolean equals (WantBook inw) {
        if (this.getUserAccountID().equals(inw.getUserAccountID()) && this.getWantISBNs().equals(inw.getWantISBNs())) {
            return true;
        }
        return false;
    }
}

