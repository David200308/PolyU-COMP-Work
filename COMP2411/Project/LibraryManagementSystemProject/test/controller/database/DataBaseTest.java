package controller.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {
    DataBase db = DataBase.getDataBase();

    @Test
    void queryTest1() {
        String sql = "SELECT accountID, accountStatus, NOTIFICATION FROM USER_ACCOUNT";
        try{
            ResultSet resultSet = db.query(sql);
            while (resultSet.next()){
                String accountID = resultSet.getInt("accountID")+ "";
                System.out.println(accountID);
                String accountStatus = resultSet.getString("accountStatus").trim();
                System.out.println(accountStatus);
                String notice = resultSet.getString("NOTIFICATION").trim();
                System.out.println(notice);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void containsTest() {
        try {
            assertFalse(db.contains("HAS_RENT","bookID",5));
//            assertFalse(db.contains("STUDENT","STUDENT_NAME","Dog"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testContains() {
        
    }

    @Disabled
    void createUserTable() {
        String createUSERTable = "CREATE TABLE USER_ACCOUNT(" +
                                "accountID NUMBER(10) NOT NULL," +
                                "accountStatus CHAR(1) CHECK (accountStatus in ( 'T', 'F' )) NOT NULL," +
                                "notification VARCHAR(8000) NOT NULL,"+
                                "PRIMARY KEY (accountID))";
        try {
            db.query(createUSERTable);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Disabled
    void createBookTable() {
        String createBookTable = "CREATE TABLE BOOK(" +
                "bookID NUMBER(15) NOT NULL, " +
                "ISBN VARCHAR (15) NOT NULL," +
                "bookName VARCHAR(100) NOT NULL," +
                "author VARCHAR(100) NOT NULL," +
                "bookCategory VARCHAR(100) NOT NULL," +
                "bookRentNum NUMBER(5) NOT NULL," +
                "bookWantNum NUMBER(5) NOT NULL," +
                "PRIMARY KEY (bookID))";
        try {
            db.query(createBookTable);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Disabled
    void createWantBookTable() {
        String createWantBookTable = "CREATE TABLE WANT_BOOK(" +
                "accountID NUMBER(10) NOT NULL," +
                "ISBN VARCHAR (15) NOT NULL," +
                "wantTime VARCHAR(10) NOT NULL," +
                "PRIMARY KEY (ISBN,accountID)," +
                "FOREIGN KEY (accountID) REFERENCES USER_ACCOUNT(accountID))";
        try {
            db.query(createWantBookTable);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Disabled
    void createRentBookTable() {
        String createRentBookTable = "CREATE TABLE HAS_RENT(" +
                "accountID NUMBER(10) NOT NULL," +
                "bookID NUMBER(15) NOT NULL," +
                "rentTime VARCHAR(10) NOT NULL," +
                "PRIMARY KEY (bookID,accountID)," +
                "FOREIGN KEY (bookID) REFERENCES BOOK(bookID)," +
                "FOREIGN KEY (accountID) REFERENCES USER_ACCOUNT(accountID))";
        try {
            db.query(createRentBookTable);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Disabled
    void createPlacedBookTable() {
        String createRentBookTable = "CREATE TABLE HAS_PLACED(" +
                "accountID NUMBER(10) NOT NULL," +
                "bookID NUMBER(15) NOT NULL," +
                "placeTime VARCHAR(10) NOT NULL," +
                "PRIMARY KEY (bookID,accountID)," +
                "FOREIGN KEY (bookID) REFERENCES BOOK(bookID)," +
                "FOREIGN KEY (accountID) REFERENCES USER_ACCOUNT(accountID))";
        try {
            db.query(createRentBookTable);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}