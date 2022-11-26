package controller;

import model.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ModelControllerTest {
    ModelController modelController = new ModelController();

    /**
     * add book data to the DB
     * */
    @Test
    @Order(1)
    void addBookRecordTest() {
        Book book1 = new Book("1", "0-01", "The Lord of the Rings","J.R.R. Tolkien", "Fantasy",1,0);
        Book book2 = new Book("2","0-02","Harry Potter and the Philosopher Stone","J.K. Rowling","Fantasy",0,1);
        Book book3 = new Book("3","0-03", "The Hobbit", "J.R.R. Tolkien", "Fantasy",0,0);
        Book book4 = new Book("4", "0-04", "The Chronicles of Narnia", "C.S. Lewis", "Fantasy",1,0);
        Book book5 = new Book("5", "0-05", "The Lion, the Witch and the Wardrobe", "C.S. Lewis", "Fantasy",1,1);
        Book book6 = new Book("6", "0-06", "The Little Prince", "Antoine de Saint-Exupéry", "Fantasy",0,0);
        Book book7 = new Book("7","0-03", "The Hobbit", "J.R.R. Tolkien", "Fantasy",1,0);
        modelController.addRecord(book1);
        modelController.addRecord(book2);
        modelController.addRecord(book3);
        modelController.addRecord(book4);
        modelController.addRecord(book5);
        modelController.addRecord(book6);
        modelController.addRecord(book7);
    }

    /**
     * add user data to the DB
     * */
    @Test
    @Order(2)
    void addUserRecord() {
        User user1 = new User("1",true,"Notification \n");
        User user2 = new User("2",true,"Notification \n");
        User user3 = new User("3",true,"Notification \n");
        User user4 = new User("4",true,"Notification \n");
        User user5 = new User("5",true,"Notification \n");
        User user6 = new User("6",true,"Notification \n");
        User user7 = new User("7",false,"Notification \n");
        modelController.addRecord(user1);
        modelController.addRecord(user2);
        modelController.addRecord(user3);
        modelController.addRecord(user4);
        modelController.addRecord(user5);
        modelController.addRecord(user6);
        modelController.addRecord(user7);
    }

    /**
     * add rent book data to the DB
     * */
    @Test
    @Order(3)
    void addRentBookRecord() {
        RentBook rentBook1 = new RentBook("1","1",2022,10,30);
        RentBook rentBook2 = new RentBook("4","7",2022,11,2);
        RentBook rentBook3 = new RentBook("5","4",2022,11,5);
        RentBook rentBook4 = new RentBook("7","6",2022,9,5);

        modelController.addRecord(rentBook1);
        modelController.addRecord(rentBook2);
        modelController.addRecord(rentBook3);
        modelController.addRecord(rentBook4);

    }

    /**
     * add want book data to the DB
     * */
    @Test
    @Order(4)
    void addWantBookRecord() {
        WantBook wantBook1 = new WantBook("1","0-05",2022, 11, 1);
        WantBook wantBook2 = new WantBook("3","0-05",2022, 10, 30);

        WantBook wantBook3 = new WantBook("1","0-04",2022, 11, 1);
        WantBook wantBook4 = new WantBook("2","0-04",2022, 10, 30);

        modelController.addRecord(wantBook1);
        modelController.addRecord(wantBook2);
        modelController.addRecord(wantBook3);
        modelController.addRecord(wantBook4);

        assertEquals("2",modelController.wantBookBuffer.get("0-04").peek().getUserAccountID());
        assertEquals("3",modelController.wantBookBuffer.get("0-05").peek().getUserAccountID());
    }

    /**
     * add placed book data to the DB
     * */
    @Test
    @Order(5)
    void addPlacedBookRecord() {
        PlacedBook placedBook1 = new PlacedBook("3","2",2022,11,1);
        PlacedBook placedBook2 = new PlacedBook("5","5",2022,11,3);
        modelController.addRecord(placedBook1);
        modelController.addRecord(placedBook2);
    }

    @Disabled
    void deleteBookRecord() {
        assertTrue(modelController.deleteBookRecord("2"));
    }

    @Disabled
    void deleteUserRecord() {
        assertTrue(modelController.deleteUserRecord("1"));
    }

    @Disabled
    void deleteWantBookRecord() {
        assertTrue(modelController.deleteWantBookRecord("0-05","1"));
        assertFalse(modelController.deleteWantBookRecord("0-05","1"));
    }

    @Disabled
    void deleteRentBookRecord() {
        assertTrue(modelController.deleteRentBookRecord("1"));
        assertFalse(modelController.deleteRentBookRecord("1"));
    }

    @Disabled
    void deletePlacedBookRecord() {
        assertTrue(modelController.deletePlacedBookRecord("2"));
        assertFalse(modelController.deletePlacedBookRecord("2"));
    }

    @Test
    @Order(6)
    void searchBookOnBookName() {
        try {
            List<Book> books = modelController.searchBookOnBookName("The Hobbit");
            assertEquals(2,books.size());
            assertEquals("The Hobbit", books.get(0).getBookName());
            assertEquals("The Hobbit", books.get(1).getBookName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(7)
    void searchBookOnBookID() {
        try {
            List<Book> books = modelController.searchBookOnBookID("6");
            assertEquals("The Little Prince", books.get(0).getBookName());
            assertEquals("6", books.get(0).getBookID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(8)
    void searchBookOnBookAuthor() {
        try {
            List<Book> books = modelController.searchBookOnBookAuthor("J.R.R. Tolkien");
            assertEquals(3,books.size());
            for (Book book : books) {
                System.out.println(book.getAuthor());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(9)
    void searchBookOnBookCategory() {
        try {
            List<Book> books = modelController.searchBookOnBookCategory("Fantasy");
            assertEquals(7,books.size());
            for (Book book : books) {
                System.out.println(book.getCategory());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(10)
    void searchBookOnBookISBN() {
        try {
            List<Book> books = modelController.searchBookOnBookISBN("0-03");
            assertEquals(2,books.size());
            for (Book book : books) {
                assertEquals("The Hobbit", book.getBookName());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(11)
    void searchRentBookOnBookID() {
        try {
            List<RentBook> rentBooks = modelController.searchRentBookOnBookID("7");
            assertEquals(1,rentBooks.size());
            for (RentBook rentBook : rentBooks) {
                assertEquals("7", rentBook.getBookID());
                assertEquals("4", rentBook.getAccountID());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(12)
    void searchRentBookOnAccountID() {
        try {
            List<RentBook> rentBooks = modelController.searchRentBookOnAccountID("2");
            assertEquals(1,rentBooks.size());
            for (RentBook rentBook : rentBooks) {
                assertEquals("2", rentBook.getAccountID());
                assertEquals("4", rentBook.getBookID());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(13)
    void searchWantBookOnAccountID() {
        try {
            List<String> wantBooks = modelController.searchWantBookOnAccountID("1");
            assertEquals(2,wantBooks.size());
            for (String wantBook : wantBooks) {
                System.out.println(wantBook);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(14)
    void searchUserFromWantBookOnISBN() {
        try {
            List<User> wantBooks = modelController.searchUserFromWantBookOnISBN("0-05");
            assertEquals(2,wantBooks.size());
            for (User wantBook : wantBooks) {
                System.out.println(wantBook.getAccountID());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(15)
    void searchPlacedBookOnAccountID() {
        try {
            List<PlacedBook> placedBooks = modelController.searchPlacedBookOnAccountID("3");
            assertEquals(1,placedBooks.size());
            for (PlacedBook placedBook : placedBooks) {
                assertEquals("2", placedBook.getBookID());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(16)
    void searchPlacedBookOnBookID() {
        try {
            List<PlacedBook> placedBooks = modelController.searchPlacedBookOnBookID("5");
            assertEquals(1,placedBooks.size());
            for (PlacedBook placedBook : placedBooks) {
                assertEquals("5", placedBook.getAccountID());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(17)
    void searchUserOnAccountID() {
        try {
            List<User> users = modelController.searchUserOnAccountID("1");
            assertEquals(1,users.size());
            for (User user : users) {
                assertEquals("1", user.getAccountID());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(18)
    void deactivateUser() {
        modelController.deactivateUser("1");
        try{
            assertFalse(modelController.searchUserOnAccountID("1").get(0).getAccountStatus());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(19)
    void activateUser() {
        modelController.activateUser("1");
        try{
            assertTrue(modelController.searchUserOnAccountID("1").get(0).getAccountStatus());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(20)
    void reserveBook() {
        try {
            assertTrue(modelController.reserveBook("7","0-01"));
            assertFalse(modelController.reserveBook("1","0-03"));
            assertEquals(2,modelController.searchWantBookOnAccountID("1").size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(21)
    void cancelReservedBook() {
        try {
            assertTrue(modelController.cancelReservedBook("0-04","1"));
            assertEquals(1,modelController.searchWantBookOnAccountID("1").size());
            assertTrue(modelController.reserveBook("1","0-04"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(22)
    void cancelPlacedBook() {
        try {
            assertTrue(modelController.cancelPlacedBook("5","5"));
            assertEquals(0,modelController.searchPlacedBookOnAccountID("5").size());
            assertEquals(0,modelController.searchWantBookOnAccountID("3").size());
            assertEquals(2, modelController.searchPlacedBookOnAccountID("3").size());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(23)
    void getExpiredRentBook() {
        List<RentBook> rentBooks = modelController.getExpiredRentBook();
        for (RentBook rentBook: rentBooks) {
            System.out.println(rentBook.getBookID());
        }
    }

    @Test
    @Order(24)
    void returnBookFromUser() {
        try {
            assertTrue(modelController.returnBookFromUser("4")); // bookID
            assertEquals(1, modelController.searchPlacedBookOnBookID("4").size());
            assertEquals(1, modelController.searchUserFromWantBookOnISBN("0-04").size());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    void refreshExpiredPlacedBook() {
        try {
            modelController.refreshExpiredPlacedBook();
            assertEquals(0, modelController.searchPlacedBookOnBookID("2").size());
            assertEquals(1, modelController.searchPlacedBookOnBookID("5").size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    @Test
    void rentBookFromUser() {
        try{
            modelController.rentBookFromUser("1","3");
            assertFalse(modelController.rentBookFromUser("2","1"));
            assertEquals(1,modelController.searchRentBookOnBookID("1").size());
            assertEquals(1,modelController.searchRentBookOnBookID("3").size());
            assertEquals(2,modelController.searchRentBookOnAccountID("1").size());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void rentBookFromPlacedBook(){
        try{
            modelController.rentBookFromPlacedBook("3","2");
            assertEquals(0,modelController.searchPlacedBookOnAccountID("3").size());
            assertEquals(1,modelController.searchRentBookOnAccountID("3").size());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testActivateAfterReturn() {
        try{
            User user = modelController.searchUserOnAccountID("7").get(0);
            assertFalse(user.getAccountStatus());
            assertEquals(1, modelController.getExpiredRentBook(user.getAccountID()).size());
            modelController.returnBookFromUser("6");
            assertTrue(user.getAccountStatus());
            assertEquals(0, modelController.getExpiredRentBook(user.getAccountID()).size());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testDeactivateUserForExpiredRentBook() {
        try {
            User user = modelController.searchUserOnAccountID("7").get(0);
            assertTrue(user.getAccountStatus());
            modelController.deactivateUserForExpiredRentBook();
            assertFalse(user.getAccountStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}