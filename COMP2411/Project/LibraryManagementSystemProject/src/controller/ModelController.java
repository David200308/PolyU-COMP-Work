package controller;

import controller.database.DataBase;
import model.*;
import view.MainView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ModelController {
    private HashMap<String, Book> bookBuffer; // key: bookId, value: Book object
    private HashMap<String, User> userBuffer; // key: accountID, value: User object
    private HashMap<String, RentBook> rentBookBuffer; // Key: bookId, value: RendBook record
    public HashMap<String, Queue<WantBook>> wantBookBuffer; // key: ISBN, value: User Queue
    private HashMap<String, PlacedBook> placedBookBuffer; // key: bookId, value: PlacedBook record

    private final int MAX_RENT_DAY = 14;
    private final int MAX_PLACED_DAY = 7;
    private final int MAX_WANT_BOOK = 8;
    private int year;
    private int month;
    private int day;

    private DataBase db = DataBase.getDataBase();

    public ModelController() {
        bookBuffer = new HashMap<>();
        userBuffer = new HashMap<>();
        rentBookBuffer = new HashMap<>();
        wantBookBuffer = new HashMap<>();
        placedBookBuffer = new HashMap<>();
        db.reConnect();
        initializeBuffers();
    }

    /**
     * Close the database connection
     */
    public void closeDB() {
        try {
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get the current date
     * @return the current date in the format of "yyyy-MM-dd"
     */
    public String getDate() {
        String yyyy = year+"";
        String mm = month<10?"0"+month:month+"";
        String dd = day<10?"0"+day:day+"";
        return yyyy+"-"+mm+"-"+dd;
    }

    /**
     * Set the current date
     * @param inYear the year
     * @param inMonth the month
     * @param inDay the day
     */
    public void setDate(String inYear, String inMonth, String inDay) {
        this.year = Integer.parseInt(inYear);
        this.month = Integer.parseInt(inMonth);
        this.day = Integer.parseInt(inDay);
    }

    /**
     * Setup and refresh the buffers from DB
     */
    public void initializeBuffers() {
        // refresh the bookBuffer
        ResultSet resultSet;
        String sql =
                "SELECT bookID, ISBN, bookName, author, bookCategory, bookRentNum, bookWantNum" +
                        " FROM BOOK";
        try{
            resultSet = db.query(sql);
            while (resultSet.next()){
                String bookID = resultSet.getInt("bookID")+ "";
                String ISBN = resultSet.getString("ISBN").trim();
                String bookName = resultSet.getString("bookName").trim();
                String author = resultSet.getString("author").trim();
                String category = resultSet.getString("bookCategory").trim();
                int bookRentNum = resultSet.getInt("bookRentNum");
                int bookWantNum = resultSet.getInt("bookWantNum");
                Book book = new Book(bookID, ISBN, bookName, author, category, bookRentNum, bookWantNum);
                bookBuffer.put(book.getBookID(), book);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        // refresh the userBuffer
        ResultSet resultSet2;
        String sql2 =
                "SELECT accountID, accountStatus, NOTIFICATION FROM USER_ACCOUNT";
        try{
            resultSet2 = db.query(sql2);
            while (resultSet2.next()){
                String accountID = resultSet2.getInt("accountID")+ "";
                String accountStatus = resultSet2.getString("accountStatus").trim();
                String notice = resultSet2.getString("NOTIFICATION").trim();
                User user = new User(accountID, accountStatus.equals("T"),notice);
                userBuffer.put(user.getAccountID(),user);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        // refresh the rentBookBuffer
        ResultSet resultSet3;
        String sql3 =
                "SELECT bookID, accountID, rentTime" +
                        " FROM HAS_RENT";
        try{
            resultSet3 = db.query(sql3);
            while (resultSet3.next()){
                String bookID = resultSet3.getInt("bookID")+ "";
                String accountID = resultSet3.getInt("accountID")+ "";
                String rentTime = resultSet3.getString("rentTime").trim();
                String[] temp2 = rentTime.split("-");
                int year = Integer.parseInt(temp2[0]);
                int month = Integer.parseInt(temp2[1]);
                int day = Integer.parseInt(temp2[2]);
                RentBook rentBook = new RentBook(accountID, bookID,year, month, day);
                try{
                    rentBookBuffer.put(rentBook.getBookID(),rentBook);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        // refresh the wantBookBuffer
        ResultSet resultSet4;
        String sql4 =
                "SELECT accountID, ISBN, wantTime" +
                        " FROM WANT_BOOK ORDER BY ISBN, wantTime"; // maintain the order in WantBook Queue
        try{
            resultSet4 = db.query(sql4);
            while (resultSet4.next()){
                String accountID = resultSet4.getInt("accountID")+ "";
                String isbn = resultSet4.getString("ISBN").trim();
                String rentTime = resultSet4.getString("wantTime").trim();
                String[] temp3 = rentTime.split("-");
                int year = Integer.parseInt(temp3[0]);
                int month = Integer.parseInt(temp3[1]);
                int day = Integer.parseInt(temp3[2]);
                WantBook wantBook = new WantBook(accountID, isbn,year, month, day);
                userBuffer.get(accountID).increaseReserveCount();
                try{
                    Queue<WantBook> queue; // Queue of User
                    if (wantBookBuffer.containsKey(wantBook.getWantISBNs())) {
                        queue = wantBookBuffer.get(wantBook.getWantISBNs());
                    }
                    else {
                        queue = new LinkedList<>();
                    }
                    // add the user to the queue with that ISBN
                    queue.add(wantBook);
                    wantBookBuffer.put(isbn, queue);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        // refresh the placedBookBuffer
        ResultSet resultSet5;
        String sql5 =
                "SELECT bookID, accountID, placeTime" +
                        " FROM HAS_PLACED";
        try{
            resultSet5 = db.query(sql5);
            while (resultSet5.next()){
                String bookID = resultSet5.getInt("bookID")+ "";
                String accountID = resultSet5.getInt("accountID")+ "";
                String rentTime = resultSet5.getString("placeTime").trim();
                String[] temp2 = rentTime.split("-");
                int year = Integer.parseInt(temp2[0]);
                int month = Integer.parseInt(temp2[1]);
                int day = Integer.parseInt(temp2[2]);
                PlacedBook placedBook = new PlacedBook(accountID, bookID,year, month, day);
                try{
                    placedBookBuffer.put(placedBook.getBookID(),placedBook);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    // ----------------- Add on HashMap buffer -----------------

    /**
     * Add Book record into bookBuffer and update to the DB
     * @param book: the book to add
     */
    public boolean addRecord(Book book) {
        try{
            if (bookBuffer.containsKey(book.getBookID()) || !isInteger(book.getBookID())){
                return false;
            }
            book.pushToDatabase();
            bookBuffer.put(book.getBookID(), book);
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    /**
     * Add User record into userBuffer and update to the DB
     * @param user: the uer to add
     */
    public boolean addRecord(User user) {
        try {
            if (userBuffer.containsKey(user.getAccountID()) || !isInteger(user.getAccountID())){
                return false;
            }
            user.pushToDatabase();
            userBuffer.put(user.getAccountID(),user);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    /**
     * Add rentBook record into rentBookBuffer and update to the DB
     * @param rentBook: the rentBook to add
     */
    public boolean addRecord(RentBook rentBook) {
        try {
            if (!isInteger(rentBook.getAccountID()) || !isInteger(rentBook.getBookID())){
                return false;
            }
            rentBook.pushToDatabase();
            rentBookBuffer.put(rentBook.getBookID(),rentBook);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Add wantBook record into wantBookBuffer and update to the DB
     * @param wantBook the wantBook to add
     */
    public boolean addRecord(WantBook wantBook) {
        try {

            if (!isInteger(wantBook.getUserAccountID())){
                return false;
            }
            wantBook.pushToDatabase();

            Queue<WantBook> queue; // Queue of User
            if (wantBookBuffer.containsKey(wantBook.getWantISBNs())) {
                queue = wantBookBuffer.get(wantBook.getWantISBNs());
            }
            else {
                queue = new LinkedList<>();
            }
            // add the user to the queue with that ISBN
            for (WantBook want: queue) {
                if (want.equals(wantBook)) {
                    return false;
                }
            }
            queue.add(wantBook);
            wantBookBuffer.put(wantBook.getWantISBNs(), queue);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Add placedBook record into placedBookBuffer and update to the DB
     * @param placedBook: the placedBook to add
     */
    public boolean addRecord(PlacedBook placedBook) {

        if (!isInteger(placedBook.getAccountID()) || !isInteger(placedBook.getBookID())){
            return false;
        }
        try {
            placedBook.pushToDatabase();
            placedBookBuffer.put(placedBook.getBookID(),placedBook);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    // ----------------- Delete on HashMap buffer -----------------
    /**
     * Delete Book record from bookBuffer and update to the DB
     * @param inBookID: the book to remove
     */
    public boolean deleteBookRecord(String inBookID) {
        if (!bookBuffer.containsKey(inBookID)) {
            return false;
        }
        try {
            bookBuffer.get(inBookID).deleteFromDatabase();
            bookBuffer.remove(inBookID);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Delete User record from userBuffer and update to the DB
     * @param inAccountID: the user to remove
     */
    public boolean deleteUserRecord(String inAccountID) {
        if (!userBuffer.containsKey(inAccountID)) {
            return false;
        }
        try {
            userBuffer.get(inAccountID).deleteFromDatabase();
            userBuffer.remove(inAccountID);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Delete wantBook record from rentBookBuffer and update to the DB
     * @param inISBN: the want book with the ISBN
     * @param inAccountID: the want user with the accountID
     */
    public boolean deleteWantBookRecord(String inISBN, String inAccountID) {
        if (!wantBookBuffer.containsKey(inISBN)) {
            return false;
        }
        try {
            Queue<WantBook> queue = wantBookBuffer.get(inISBN);
            Queue<WantBook> out = new LinkedList<>();
            if (queue.size() == 0) {return false;}
            for (WantBook wantBook: queue){
                if ((wantBook).getUserAccountID().equals(inAccountID)) {
                    wantBook.deleteFromDatabase();
                }
                else{
                    out.add(wantBook);
                }
            }
            if(queue.size() == out.size()) {return false;}
            wantBookBuffer.put(inISBN, out);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Delete rent book from buffer and DB
     * @param inBookID: the placed book with the bookID
     */
    public boolean deleteRentBookRecord(String inBookID) {
        if (!rentBookBuffer.containsKey(inBookID)) {
            return false;
        }
        try {
            rentBookBuffer.get(inBookID).deleteFromDatabase();
            rentBookBuffer.remove(inBookID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * delete placed book from buffer and DB
     * @param inBookID: the placed book with the bookID
     */

    public boolean deletePlacedBookRecord(String inBookID) {
        if (!placedBookBuffer.containsKey(inBookID)) {
            return false;
        }
        try {
            placedBookBuffer.get(inBookID).deleteFromDatabase();
            placedBookBuffer.remove(inBookID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // --------------- Search on Book ---------------

    /**
     * get all the books in the buffer
     * @return all the books in the buffer
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        books.addAll(bookBuffer.values());
        return books;
    }

    /**
     * Search book on name inName
     * @param inName: the name of the book
     * @return List<Book>: a list of book with the name inName
     * @throws Exception: if the book is not found
     * */
    public List<Book> searchBookOnBookName(String inName) {
        List<Book> result = new ArrayList<>();
        for (SQLModel book : bookBuffer.values()) {
            if (((Book) book).getBookName().equals(inName)) {
                result.add((Book)book);
            }
        }
        return result;
    }

    /**
     * Search book on ISBN
     * @param inBookID: the ISBN of the book
     * return List<Book>: a list of book with the id
     */
    public List<Book> searchBookOnBookID(String inBookID) {
        List<Book> result = new ArrayList<>();
        if (bookBuffer.containsKey(inBookID)) {
            result.add(bookBuffer.get(inBookID));
        }
        return result;
    }

    /**
     * Search book on Author
     * @param inAuthor: the author of the book
     * return List<Book>: a list of book with the author
     */
    public List<Book> searchBookOnBookAuthor(String inAuthor) {
        List<Book> result = new ArrayList<>();
        for (SQLModel book : bookBuffer.values()) {
            if (((Book) book).getAuthor().equals(inAuthor)) {
                result.add((Book)book);
            }
        }
        return result;
    }

    /**
     * Search book on category
     * @param inCategory: the category of the book
     * return List<Book>: a list of book with the category
     */
    public List<Book> searchBookOnBookCategory(String inCategory) {
        List<Book> result = new ArrayList<>();
        for (SQLModel book : bookBuffer.values()) {
            if (((Book) book).getCategory().equals(inCategory)) {
                result.add((Book)book);
            }
        }
        return result;
    }

    /**
     * Search book on ISBN
     * @param inISBN: the ISBN of the book
     * return List<Book>: a list of book with the category
     */
    public List<Book> searchBookOnBookISBN(String inISBN) {
        List<Book> result = new ArrayList<>();
        for (SQLModel book : bookBuffer.values()) {
            if (((Book) book).getISBN().equals(inISBN)) {
                result.add((Book)book);
            }
        }
        return result;
    }


    // --------------- Search on Rent Book ---------------

    /**
     * get all the rent books in the buffer
     * @return all the rent books in the buffer
     */
    public List<RentBook> getAllRentBooks() {
        List<RentBook> rentBooks = new ArrayList<>();
        rentBooks.addAll(rentBookBuffer.values());
        return rentBooks;
    }

    /**
     * Search rent book on bookID
     * @param inBookID the bookID of the rent book
     * @return List<RentBook>: a list of rent book with the bookID
     * @throws Exception if the rent book is not found
     */
    public List<RentBook> searchRentBookOnBookID(String inBookID) {
        List<RentBook> result = new ArrayList<>();
        if (rentBookBuffer.containsKey(inBookID)){
            result.add(rentBookBuffer.get(inBookID));
        }
        return result;
    }

    /**
     * Search rent book on accountID
     * @param inAccountID the accountID of the rent book
     * @return List<RentBook>: a list of rent book with the accountID
     * @throws Exception if the rent book is not found
     */
    public List<RentBook> searchRentBookOnAccountID(String inAccountID) {
        List<RentBook> result = new ArrayList<>();
        for (SQLModel rentBook : rentBookBuffer.values()) {
            if (((RentBook) rentBook).getAccountID().equals(inAccountID)) {
                result.add((RentBook) rentBook);
            }
        }
        return result;
    }

    // --------------- Search on Want Book ---------------

    /**
     * get all the want books in the buffer
     * @return all the want books in the buffer
     */
    public List<WantBook> getAllWantBooks() {
        List<WantBook> out = new ArrayList<>();
        for (Queue<WantBook> wantBooks: wantBookBuffer.values()) {
            out.addAll(wantBooks);
        }
        return out;
    }
    /**
     * Search want book on accountID
     * @param inAccountID the accountID of the user want the book
     * @return List<WantBook>: a list of want book ISBN
     * @throws Exception if the want book is not found
     */
    public List<String> searchWantBookOnAccountID(String inAccountID) {
        List<String> result = new ArrayList<>();
        for (String isbn : wantBookBuffer.keySet()) {
            for (WantBook wantBook: wantBookBuffer.get(isbn)) {
                if (wantBook.getUserAccountID().equals(inAccountID)) {
                    result.add(isbn);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Search want user form want book ISBN
     * @param inISBN the ISBN of the book
     * @return List<User>: a list of user want the book
     * @throws Exception if the want book is not found
     */
    public List<User> searchUserFromWantBookOnISBN(String inISBN) {
        List<User> result = new ArrayList<>();
        if (wantBookBuffer.containsKey(inISBN)){
            for (WantBook user : wantBookBuffer.get(inISBN)){
                result.add(userBuffer.get(user.getUserAccountID()));
            }
        }
        return result;
    }


    // --------------- Search on Placed Book ---------------

    /**
     * get all the placed books in the buffer
     * @return all the placed books in the buffer
     */
    public List<PlacedBook> getAllPlacedBooks() {
        List<PlacedBook> out = new ArrayList<>();
        out.addAll(placedBookBuffer.values());
        return out;
    }

    /**
     * Search placed book on accountID
     * @param inAccountID the accountID of the user that placed the book for
     * @return List<PlacedBook>: a list of placed book with the accountID
     * @throws Exception
     */
    public List<PlacedBook> searchPlacedBookOnAccountID(String inAccountID) {
        List<PlacedBook> result = new ArrayList<>();
        for (SQLModel placedBook : placedBookBuffer.values()) {
            if (((PlacedBook) placedBook).getAccountID().equals(inAccountID)) {
                result.add((PlacedBook) placedBook);
            }
        }
        return result;
    }

    /**
     * Search placed book on bookID
     * @param inBookID the bookID of the placed book
     * @return List<PlacedBook>: a list of placed book with the bookID
     * @throws Exception
     */
    public List<PlacedBook> searchPlacedBookOnBookID(String inBookID) {
        List<PlacedBook> result = new ArrayList<>();
        if (placedBookBuffer.containsKey(inBookID)){
            result.add(placedBookBuffer.get(inBookID));
        }
        return result;
    }


    // --------------- Search on User ---------------

    /**
     * get all the users in the buffer
     * @return all the users in the buffer
     */
    public List<User> getAllUsers() {
        List<User> out = new ArrayList<>();
        out.addAll(userBuffer.values());
        return out;
    }

    /**
     * Search user on accountID
     * @param inAccountID the accountID of the user
     * @return List<User>: the user with the accountID
     * @throws Exception
     */
    public List<User> searchUserOnAccountID(String inAccountID) {
        List<User> result = new ArrayList<>();
        if (userBuffer.containsKey(inAccountID)){
            result.add(userBuffer.get(inAccountID));
        }
        return result;
    }


    // ----------------- de/activate User -----------------

    /**
     * deactivates a user by accountID
     * @param inAccountID: the accountID of the user to be deactivated
     * @return boolean: true if the user is successfully deactivated, false if the user is already deactivated
     */
    public boolean deactivateUser(String inAccountID) {
        if (userBuffer.containsKey(inAccountID)) {
            try{
                User user = userBuffer.get(inAccountID);
                if (user.getAccountStatus()) {
                    user.setAccountStatus(false);
                    StringBuilder sb = new StringBuilder();
                    sb.append(user.getNoticeString());
                    sb.append("\n\t[").append(getDate()).append("]: ");
                    sb.append("Your account has been deactivated.");
                    user.setNoticeString(sb.toString());
                    user.pushToDatabase();
                    return true;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * activates a user by accountID
     * @param inAccountID: the accountID of the user to be activated
     * @return boolean: true if the user is successfully activated, false if the user is already activated
     */
    public boolean activateUser(String inAccountID) {
        if (userBuffer.containsKey(inAccountID)) {
            try{
                User user =  userBuffer.get(inAccountID);
                if (!user.getAccountStatus()) {
                    user.setAccountStatus(true);
                    StringBuilder sb = new StringBuilder();
                    sb.append(user.getNoticeString());
                    sb.append("\n\t[").append(getDate()).append("]: ");
                    sb.append("Your account has been activated.");
                    user.setNoticeString(sb.toString());
                    user.pushToDatabase();
                    return true;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }


    // ---------------- reverse book -----------
    /**
     * Reserve a book with a ISBN whether it is available in the Library or not,
     * if not available, put the pair of <User ID, ISBN> object into wantBook array;
     * if available, tell the user to go to the library to find it.
     * @param inAccountID: the user ID of the user who wants to reserve a book
     * @param inISBN: the ISBN of the book the user wants to reserve
     * @return boolean: true if the book is successfully reserved, false if the book is not available
     */
    public boolean reserveBook(String inAccountID, String inISBN) {
        // The book is in the library for sure
        List<Book> foundBooks;
        try{
            foundBooks = searchBookOnBookISBN(inISBN);
        }
        catch (Exception e){
            return false;
        }

        boolean canBeReserved = true;
        if (!userBuffer.containsKey(inAccountID) || foundBooks.size() == 0) { // check if the ISBN is in the library
            canBeReserved = false;
        }
        for (Book book : foundBooks) {
            if (!rentBookBuffer.containsKey(book.getBookID()) && !placedBookBuffer.containsKey(book.getBookID())) {
                canBeReserved = false;
                break;
            }
        }
        if (canBeReserved) {
            WantBook wantBook = new WantBook(inAccountID, inISBN, year, month, day);
            try{
                User user = userBuffer.get(inAccountID);
                if (!user.getAccountStatus()) {
                    System.out.println("Deactivated account, no function allowed, return expired books first");
                    return false;
                }
                int temp = user.getReserveCount();
                // check the reserve count < MAX_WANT_BOOK
                if (temp<MAX_WANT_BOOK) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(user.getNoticeString());
                    sb.append("\n\t[").append(getDate()).append("]: ");
                    sb.append("You have successfully reserved the book with ISBN: ").append(inISBN);
                    user.setNoticeString(sb.toString());
                    user.increaseReserveCount();
                    addRecord(wantBook);
                }
                else {
                    System.out.println("You have reached the maximum number of reservations.");
                    return false;
                }
            }
            catch (Exception e){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * Cancel a reservation of a book with a ISBN
     * Only a reserved book can be cancelled
     * contains the inISBN and User pair in wantBookBuffer
     * check whether the book is reserved by the user
     * @param inISBN the ISBN of the book the user wants to cancel
     * @param inAccountID the user ID of the user who wants to cancel a reservation
     * @return boolean: true if the book is successfully cancelled, false if the cancellation fails
     */
    public boolean cancelReservedBook(String inISBN, String inAccountID) {
        List<Book> foundBooks;
        try{
            foundBooks = searchBookOnBookISBN(inISBN);
        }
        catch (Exception e){
            return false;
        }
        if (!userBuffer.containsKey(inAccountID) || foundBooks.size() == 0) { // check if the ISBN is in the library
           return false;
        }
        try {
            searchWantBookOnAccountID(inAccountID);
        } catch (Exception e) {
            return false;
        }

        try{
            User user = userBuffer.get(inAccountID);
            user.decreaseReserveCount();
            deleteWantBookRecord(inISBN, inAccountID);
            StringBuilder sb = new StringBuilder();
            sb.append(user.getNoticeString());
            sb.append("\n\t[").append(getDate()).append("]: ");
            sb.append("You have successfully cancelled the reservation of the book with ISBN: ").append(inISBN);
            user.setNoticeString(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Cancel a placed book with a bookID and accountID
     * @param inBookID the bookID of the book the user wants to cancel
     * @param inAccountID the user ID of the user who wants to cancel a placed book
     * @return boolean: true if the book is successfully cancelled, false if the cancellation fails
     */
    public boolean cancelPlacedBook(String inBookID, String inAccountID) {
        if (!userBuffer.containsKey(inAccountID) || !bookBuffer.containsKey(inBookID)) {
            return false;
        }
        try {
            searchPlacedBookOnAccountID(inAccountID);
        } catch (Exception e) {
            return false;
        }
        try{
            List<Book> books = searchBookOnBookID(inBookID);
            Book book = books.get(0);
            StringBuilder sb = new StringBuilder();
            User nowUser = userBuffer.get(inAccountID);
            sb.append(nowUser.getNoticeString());
            sb.append("\n\t[").append(getDate()).append("]: ");
            sb.append("The book with BookID: ").append(book.getBookID()).append(" is canceled for placed now.");
            nowUser.setNoticeString(sb.toString());
            deletePlacedBookRecord(inBookID);
            String isbn = book.getISBN();
            int size = searchUserFromWantBookOnISBN(isbn).size();

            if (size > 0){
                WantBook nextWantUser = wantBookBuffer.get(isbn).peek();
                PlacedBook placedBook = new PlacedBook(nextWantUser.getUserAccountID(), inBookID, this.year, this.month, this.day);
                MainView mainView = new MainView();
                mainView.canBeRentNotification(isbn,nextWantUser.getUserAccountID());
                StringBuilder sb1 = new StringBuilder();
                User nextUser = userBuffer.get(nextWantUser.getUserAccountID());
                sb1.append(nextUser.getNoticeString());
                sb1.append("\n\t[").append(getDate()).append("]: ");
                sb1.append("The book with ISBN: ").append(isbn).append(" is available now.");
                nextUser.setNoticeString(sb1.toString());
                book.addWantBookCount();
                book.pushToDatabase();
                addRecord(placedBook);
                deleteWantBookRecord(isbn,nextWantUser.getUserAccountID());
            }
        }catch(Exception e){
            return false;
        }
        return true;
    }

    /**
     * put expired placed book to available and notice their user
     */
    public void refreshExpiredPlacedBook(){
        List<PlacedBook> list = new ArrayList<>();
        for (PlacedBook placedBook: placedBookBuffer.values()){
            list.add(placedBook);
        }
        for (PlacedBook placedBook: list){
            try{
                int year = placedBook.getDateArray()[0];
                int month = placedBook.getDateArray()[1];
                int day = placedBook.getDateArray()[2];
                int placedDay = DayCalculator.dayApart(day, month, year,this.day,this.month, this.year);
                if (placedDay>MAX_PLACED_DAY){
                    cancelPlacedBook(placedBook.getBookID(), placedBook.getAccountID());
                    MainView mainView = new MainView();
                    mainView.outOfMaxPlacedDayNotification(placedBook.getBookID(),placedBook.getAccountID());
                    StringBuilder sb = new StringBuilder();
                    User user = userBuffer.get(placedBook.getAccountID());
                    sb.append(user.getNoticeString());
                    sb.append("\n\t[").append(getDate()).append("]: ");
                    sb.append("The book with bookID: ").append(placedBook.getBookID()).append(" which placed in library is expired.");
                    user.setNoticeString(sb.toString());
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
    * check the expired rent books and return the list of expired rent books
    * @return List<RentBook> expired rent Book and User
    * */
    public List<RentBook> getExpiredRentBook() {
        List<RentBook> output = new ArrayList<>();
        for (RentBook rentBook: rentBookBuffer.values()) {
            try {
                int y = rentBook.getDateArray()[0];
                int m = rentBook.getDateArray()[1];
                int d = rentBook.getDateArray()[2];
                int rentDays = DayCalculator.dayApart(d, m, y, day, month, year);
                if (rentDays > MAX_RENT_DAY) {
                    MainView mainView = new MainView();
                    mainView.outOfMaxRentDayNotification(rentBook.getBookID(), rentBook.getAccountID());
                    StringBuilder sb = new StringBuilder();
                    User user = userBuffer.get(rentBook.getAccountID());
                    sb.append(user.getNoticeString());
                    sb.append("\n\t[").append(getDate()).append("]: ");
                    sb.append("The book with Book ID: ").append(rentBook.getBookID()).append(" you rent is expired.");
                    user.setNoticeString(sb.toString());
                    output.add(rentBook);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    /**
     * deactivates the user account due to overdue rent books
     * and return the list of deactivated users
     * @return List<User> deactivated users
     */
    public List<User> deactivateUserForExpiredRentBook() {
        List<User> output = new ArrayList<>();
        List<RentBook> expiredRentBook = getExpiredRentBook();
        for (RentBook rentBook: expiredRentBook) {
            try {
                User user = userBuffer.get(rentBook.getAccountID());
                if (!user.getAccountStatus()) {
                    output.add(user);
                    continue;
                }
                user.setAccountStatus(false);
                user.pushToDatabase();
                output.add(user);
                StringBuilder sb = new StringBuilder();
                sb.append(user.getNoticeString());
                sb.append("\n\t[").append(getDate()).append("]: ");
                sb.append("Your account is deactivated for overdue books");
                user.setNoticeString(sb.toString());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    /**
     * check the expired rent books of a user and return the list of expired rent books
     * @param inAccountID: the user who wants to check the expired rent books
     * @return List<RentBook> expired rent Book of this user
     */
    public List<RentBook> getExpiredRentBook(String inAccountID) {
        List<RentBook> output = new ArrayList<>();
        if (!userBuffer.containsKey(inAccountID)) {
            return output;
        }
        for (RentBook rentBook: rentBookBuffer.values()) {
            try {
                if (rentBook.getAccountID().equals(inAccountID)) {
                    int y = rentBook.getDateArray()[0];
                    int m = rentBook.getDateArray()[1];
                    int d = rentBook.getDateArray()[2];
                    int rentDays = DayCalculator.dayApart(d, m, y, day, month, year);
                    if (rentDays > MAX_RENT_DAY) {
                        output.add(rentBook);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    /**
     * Rent a book from library by a user
     * @param accountID the user who wants to rent a book
     * @param bookID the book which the user wants to rent
     * @return true if the rent is successful, false if the rent is failed
     */
    public boolean rentBookFromUser(String accountID, String bookID) {
        if (!userBuffer.containsKey(accountID) || !bookBuffer.containsKey(bookID)) {
            return false;
        }
        if (!userBuffer.get(accountID).getAccountStatus()) {
            System.out.println("Deactivated account, no function allowed, return expired books first");
            return false;
        }
        List<Book> searchRentBooks;
        try {
            searchRentBooks = searchBookOnBookID(bookID);
            for (Book book : searchRentBooks) {
                if (!rentBookBuffer.containsKey(book.getBookID()) && !placedBookBuffer.containsKey(book.getBookID())) {
                    RentBook rentBook = new RentBook(accountID, bookID, year, month, day);
                    try {
                        book.addRentBookCount();
                        book.pushToDatabase();
                        StringBuilder sb = new StringBuilder();
                        User user = userBuffer.get(accountID);
                        sb.append(user.getNoticeString());
                        sb.append("\n\t[").append(getDate()).append("]: ");
                        sb.append("The book with BookID: ").append(book.getBookID()).append(" is rented by ").append(accountID);
                        user.setNoticeString(sb.toString());
                        addRecord(rentBook);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Return a book to library by a user
     * @param bookID the book which the user wants to return
     * @return true if the return is successful, false if the return is failed
     */
    public boolean returnBookFromUser(String bookID) {
        if (!bookBuffer.containsKey(bookID)) {
            return false;
        }
        try {
            if (rentBookBuffer.containsKey(bookID)) {
                Book book = bookBuffer.get(bookID);
                Queue<WantBook> wantBookUsers = wantBookBuffer.get(book.getISBN());
                if (wantBookUsers != null && wantBookUsers.size() > 0) {
                    User nextUser = userBuffer.get(wantBookUsers.peek().getUserAccountID());
                    try {
                        PlacedBook placedBook = new PlacedBook(nextUser.getAccountID(), bookID, year, month, day);
                        book.addWantBookCount();
                        StringBuilder sb = new StringBuilder();
                        sb.append(nextUser.getNoticeString());
                        sb.append("\n\t[").append(getDate()).append("]: ");
                        sb.append("The book with BookID: ").append(book.getISBN()).append(" is available now.");
                        nextUser.setNoticeString(sb.toString());
                        addRecord(placedBook);
                        book.addWantBookCount();
                        book.pushToDatabase();
                        deleteWantBookRecord(book.getISBN(), nextUser.getAccountID());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                User user = userBuffer.get(rentBookBuffer.get(bookID).getAccountID());
                deleteRentBookRecord(bookID);
                if (!user.getAccountStatus() && getExpiredRentBook(user.getAccountID()).size() == 0) {
                    user.setAccountStatus(true);
                    user.pushToDatabase();
                    StringBuilder sb = new StringBuilder();
                    sb.append(user.getNoticeString());
                    sb.append("\n\t[").append(getDate()).append("]: ");
                    sb.append("Your account is activated back.");
                    user.setNoticeString(sb.toString());
                    System.out.println("[Dear Manager, the following message has been sent to the user "+user.getAccountID()+" via email:");
                    System.out.println("    \"Dear User " +user.getAccountID());
                    System.out.println("    Your account is activated back!");
                }
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Rent a book from the placed book by a user
     * @param accountID the user who wants to rent a book
     * @param bookID the book which the user wants to rent
     * @return true if the rent is successful, false if the rent is failed
     */
    public boolean rentBookFromPlacedBook(String accountID,String bookID) {
        if (!userBuffer.containsKey(accountID) || !bookBuffer.containsKey(bookID)) {
            return false;
        }
        if (!userBuffer.get(accountID).getAccountStatus()) {
            System.out.println("Deactivated account, no function allowed, return expired books first");
            return false;
        }
        try {
            if (placedBookBuffer.containsKey(bookID)) {
                if (placedBookBuffer.get(bookID).getAccountID().equals(accountID)) {
                    deletePlacedBookRecord(bookID);
                    RentBook rentBook = new RentBook(accountID, bookID, year, month, day);
                    bookBuffer.get(bookID).addRentBookCount();
                    bookBuffer.get(bookID).pushToDatabase();
                    addRecord(rentBook);
                    StringBuilder sb = new StringBuilder();
                    User user = userBuffer.get(accountID);
                    sb.append(user.getNoticeString());
                    sb.append("[").append(getDate()).append("]: ");
                    sb.append("You can take the book with bookID: ").append(bookID).append("from placed book.");
                    user.setNoticeString(sb.toString());
                    return true;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * check whether a String is in int format
     * @param s the String to be checked
     * @return true if the String is in int format, false if the String is not in int format
     */
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
}
