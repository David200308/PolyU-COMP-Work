package view;

import controller.LMSController;
import controller.ModelController;
import model.Book;
import java.util.*;

/**
 * The class MainView is the main view part of the application.
 */
public class MainView {

    ModelController modelController = LMSController.getModelController();

    public void welcomePage() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("        Welcome to the Library Management System (LMS)!");
        System.out.println("             COMP2411 Database System (Fall 2022)");
        System.out.println("                       Project Group 4");
        System.out.println();
        System.out.println("- [L] Manager Login");
        System.out.println("- [-1] Exit System");
        System.out.print(">>> Please select the above options x in [x]:");
    }

    public void mainPageWelcome(){
        System.out.println("----------------------Welcome Manager-----------------------");

    }

    public void mainPage() {
        System.out.println("\n----------------------------------------------------------------");
        System.out.println("----------------------Today is "+modelController.getDate()+"-----------------------");
        System.out.println("- Operation on System");
        System.out.println("    - [A] Add Book");
        System.out.println("    - [B] Add User");
        System.out.println("    - [C] Delete Book");
        System.out.println("    - [D] Delete User");
        System.out.println("- Operation on User");
        System.out.println("    - [E] Rent a Book from Library");
        System.out.println("    - [F] Rent a Book from Placed");
        System.out.println("    - [G] Want a Book");
        System.out.println("    - [H] Cancel a Reserve Book");
        System.out.println("    - [I] Cancel a Placed book");
        System.out.println("    - [J] Search a Book");
        System.out.println("    - [K] Search a User");
        System.out.println("    - [L] Return a Book");
        System.out.println("- Data Refresh");
        System.out.println("    - [M] Refresh Expired Placed Book");
        System.out.println("    - [N] Refresh Deactivate User");
        System.out.println("- Data View");
        System.out.println("    - [O] View All Book");
        System.out.println("    - [P] View All Rent Book");
        System.out.println("    - [Q] View All Want Book");
        System.out.println("    - [R] View All Placed Book");
        System.out.println("    - [S] View All User");
        System.out.println("- [T] Analysis Report");
        System.out.println("- [U] Set Current Date");
        System.out.println("- [X] Reset from Database");
        System.out.println("- [-1] Exit System");
        System.out.println("----------------------------------------------------------------");
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    public void processUserSearchPage() {
        System.out.println("\n- [A] Search a Book by ISBN");
        System.out.println("- [B] Search a Book by Name");
        System.out.println("- [C] Search a Book by Author");
        System.out.println("- [D] Search a Book by Category");
        System.out.println("- [Back] Back to previous page");
        System.out.println("----------------------------------------------------------------");
        System.out.print(">>> Please select the above options x in [x]: ");
    }

    public void processUserRentPage() {
        System.out.print(">>> Please input the book ID to rent (or 'Back' to back): ");
    }

    public void processUserReturnPage() {
        System.out.print(">>> Please input the return Book ID (or 'Back' to back): ");
    }

    public void processUserWantPage() {
        System.out.print(">>> Please input ISBN (or 'Back' to back): ");
    }

    public void processUserCancelReservePage() {
        System.out.print(">>> Please input ISBN: ");
    }

    public void processUserCancelPlacedPage() {
        System.out.print(">>> Please input bookID: ");
    }

    public void errorPage() {
        System.out.println("Wrong option! Please enter again!");
    }

    public void accountBannedPage() {
        System.out.println("Deactivated account, no function allowed, return expired books first");
    }
    
    public void linePage() {
        System.out.println("----------------------------------------------------------------");
    }

    public void breakPointPage() {
        System.out.print(">>> Press Enter/Return to continue:");
    }

    public void successPage() {
        System.out.println("Execute successfully\n");
    }

    public void unSuccessPage() {
        System.out.println("Execute unsuccessfully\n");
    }

    public void inputUserPage() {
        System.out.print(">>> Please enter accountID (or 'Back' to back): ");
    }

    public void inputISBNPage() {
        System.out.print(">>> Please enter the book ISBN (or 'Back' to back): ");
    }

    public void inputNamePage() {
        System.out.print(">>> Please enter the book name (or 'Back' to back): ");
    }

    public void inputAuthorPage() {
        System.out.print(">>> Please enter the author name (or 'Back' to back): ");
    }

    public void inputCategoryPage() {
        System.out.print(">>> Please enter the book category (or 'Back' to back): ");
    }

    public void inputBookIDPage() {
        System.out.print(">>> Please enter the bookID (or 'Back' to back): ");
    }

    // when the book return by a user and the isbn is somebody want, notify the person who want the book
    public void canBeRentNotification(String ISBN, String accountID) {
        System.out.println("[Dear Manager, the following message has been sent to the user "+accountID+" via email:");
        System.out.println("    \"Dear User " +accountID);
        System.out.println("    The book (ISBN: "+ISBN+") you want now is available, you can get it in library by yourself!");
        System.out.println("    Remember the MAX PLACED DAY is 7 day, you have better to get it in time");
        System.out.println("    Or you are supposed to reserve this book by another application!\"]");
    }

    // A book can be rent for up to 14 day
    public void outOfMaxRentDayNotification(String bookID, String accountID) {
        System.out.println("[Dear Manager, the following message has been sent to the user "+accountID+" via email:");
        System.out.println("    \"Dear User " +accountID);
        System.out.println("    The book (ID: "+bookID+") has been rent is out of the MAX RENT DAY 14 days!");
        System.out.println("    Your account is deactivated until you return the book!\"]");
    }

    // A book can be reserved for up to 7 days
    public void outOfMaxPlacedDayNotification(String bookID, String accountID) {
        System.out.println("[Dear Manager, the following message has been sent to the user "+accountID+" via email:");
        System.out.println("    \"Dear User " +accountID);
        System.out.println("    The book (ID: "+bookID+") has been placed on library out of the MAX PLACED DAY - 7 days!");
        System.out.println("    You are supposed to reserve this book by another application!\"]");
    }

    public void emptyPage() {
        System.out.println("The return list is empty");
    }

    public void showListPage() {
        System.out.println("----------------------------------------------------------------");
    }

    public void showTitle(){
        System.out.print("Here is(are) information about ");
    }

    public void analysisReportPage(int totalBookNumber, int totalRentBookNumber, List<String> mostRentBookISBN, List<Book> mostRentBookID,
                                   List<String> leastRentBookISBN, List<Book> leastRentBookID, List<String> mostWantBookISBN, List<Book> mostWantBookID,
                                   List<String> leastWantBookISBN, List<Book> leastWantBookID, List<String> mostRentBookISBNByCategory, List<String> mostRentBookISBNByAuthor,
                                   List<String> mostWantBookISBNByCategory, List<String> mostWantBookISBNByAuthor) {
        String timeStamp = modelController.getDate();

        System.out.println("----------------------------------------------------------------");
        System.out.println("                    " + "ANALYSIS REPORT");
        System.out.println("                " + "Create Time: " + timeStamp);
        System.out.println("----------------------------------------------------------------");
        System.out.println("                " + "Books Status Information");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Total Number of Books: " + totalBookNumber);
        System.out.println("Total Number of Books Already Borrowed: " + totalRentBookNumber);
        System.out.println("Most Rent Book: ");
        for (String line : mostRentBookISBN){
            System.out.println("  - "+line);
        }
        System.out.println("Most Rent Book Instance: ");
        for (Book book : mostRentBookID){
            System.out.println("  - "+book.showInfo());
        }
        System.out.println("Least Rent Book: ");
        for (String line: leastRentBookISBN){
            System.out.println("  - "+line);
        }
        System.out.println("Least Rent Book Instance: ");
        for (Book book: leastRentBookID) {
            System.out.println("  - "+book.showInfo());
        }
        System.out.println("Most Want Book: ");
        for (String line : mostWantBookISBN) {
            System.out.println("  - "+line);
        }
        System.out.println("Most Want Book Instance: ");
        for (Book book : mostWantBookID){
            System.out.println("  - "+book.showInfo());
        }
        System.out.println("Least Want Book");
        for (String line : leastWantBookISBN){
            System.out.println("  - "+line);
        }
        System.out.println("Least Want Book Instance: ");
        for (Book book : leastWantBookID) {
            System.out.println("  - "+book.showInfo());
        }
        System.out.println("Most Rent Book By Category: ");
        for (String line : mostRentBookISBNByCategory) {
            System.out.println("  - "+line);
        }
        System.out.println("Most Rent Book By Author: ");
        for (String line : mostRentBookISBNByAuthor) {
            System.out.println("  - "+line);
        }
        System.out.println("Most Want Book By Category: ");
        for (String line : mostWantBookISBNByCategory) {
            System.out.println("  - "+line);
        }
        System.out.println("Most Want Book By Author: ");
        for (String line : mostWantBookISBNByAuthor) {
            System.out.println("  - "+line);
        }
    }
}
