-- COMP2411 Database Systems (Fall 2022)
-- Group 4
-- Library Management System 
-- Testing Data

CREATE TABLE USER_ACCOUNT(
    accountID NUMBER(10) NOT NULL,
    accountStatus CHAR(1) CHECK (accountStatus in ( 'T', 'F' )) NOT NULL,
    notification VARCHAR(8000) NOT NULL,
    PRIMARY KEY (accountID)
);

CREATE TABLE BOOK(
    bookID NUMBER(15) NOT NULL, 
    ISBN VARCHAR (15) NOT NULL, 
    bookName VARCHAR(100) NOT NULL, 
    author VARCHAR(100) NOT NULL,
    bookCategory VARCHAR(100) NOT NULL,
    bookRentNum NUMBER(5) NOT NULL,
    bookWantNum NUMBER(5) NOT NULL,
    PRIMARY KEY (bookID)
);

CREATE TABLE WANT_BOOK(
    accountID NUMBER(10) NOT NULL,
    ISBN VARCHAR (15) NOT NULL, 
    wantTime VARCHAR(10) NOT NULL,
    PRIMARY KEY (ISBN,accountID),
    FOREIGN KEY (accountID) REFERENCES USER_ACCOUNT(accountID)
);

CREATE TABLE HAS_RENT(
    accountID NUMBER(10) NOT NULL,
    bookID NUMBER(15) NOT NULL, 
    rentTime VARCHAR(10) NOT NULL,
    PRIMARY KEY (bookID,accountID),
    FOREIGN KEY (bookID) REFERENCES BOOK(bookID),
    FOREIGN KEY (accountID) REFERENCES USER_ACCOUNT(accountID)
);

CREATE TABLE HAS_PLACED(
    accountID NUMBER(10) NOT NULL,
    bookID NUMBER(15) NOT NULL, 
    placeTime VARCHAR(10) NOT NULL,
    PRIMARY KEY (bookID,accountID),
    FOREIGN KEY (bookID) REFERENCES BOOK(bookID),
    FOREIGN KEY (accountID) REFERENCES USER_ACCOUNT(accountID)
);


INSERT INTO USER_ACCOUNT VALUES (1001, 'T', 'Notification: ');
INSERT INTO USER_ACCOUNT VALUES (1002, 'T', 'Notification: ');
INSERT INTO USER_ACCOUNT VALUES (1003, 'T', 'Notification: ');
INSERT INTO USER_ACCOUNT VALUES (1004, 'T', 'Notification: ');
INSERT INTO USER_ACCOUNT VALUES (1005, 'T', 'Notification: ');
INSERT INTO USER_ACCOUNT VALUES (1006, 'T', 'Notification: ');
INSERT INTO USER_ACCOUNT VALUES (1007, 'F', 'Notification: ');


INSERT INTO BOOK VALUES (10001, '0-01', 'The Lord of the Rings', 'J.R.R. Tolkien', 'Fantasy', 12, 10);
-- rent
INSERT INTO BOOK VALUES (10002, '0-02', 'Harry Potter and the Philosopher Stone', 'J.K. Rowling', 'Fantasy', 31, 2);   
-- placed
INSERT INTO BOOK VALUES (10003, '0-03', 'The Hobbit', 'J.R.R. Tolkien', 'Fantasy', 3, 12);
-- available
INSERT INTO BOOK VALUES (10004, '0-04', 'The Chronicles of Narnia', 'C.S. Lewis', 'Fantasy', 5, 0);
-- rent
INSERT INTO BOOK VALUES (10005, '0-05', 'The Lion, the Witch and the Wardrobe', 'C.S. Lewis', 'Fantasy', 1, 10);
-- placed
INSERT INTO BOOK VALUES (10006, '0-06', 'The Little Prince', 'Antoine de Saint', 'Fantasy', 5, 5);
-- available
INSERT INTO BOOK VALUES (10007, '0-03', 'The Hobbit', 'J.R.R. Tolkien', 'Fantasy', 23, 1);
-- rent
INSERT INTO BOOK VALUES (10008, '1-07', 'Pride and Prejudice', 'Jane Austen', 'Novel', 12, 5);
--available
INSERT INTO BOOK VALUES (10009, '1-07', 'Pride and Prejudice', 'Jane Austen', 'Novel', 2, 1);
--placed
INSERT INTO BOOK VALUES (10010, '1-07', 'Pride and Prejudice', 'Jane Austen', 'Novel', 23, 9);
--rent
INSERT INTO BOOK VALUES (10011, '1-08', 'Butterball', 'Henry-Albert-Guy de Maupassant', 'Novel', 2, 5);
--available
INSERT INTO BOOK VALUES (10012, '1-08', 'Butterball', 'Henry-Albert-Guy de Maupassant', 'Novel', 12, 1);
--placed
INSERT INTO BOOK VALUES (10013, '1-08', 'Butterball', 'Henry-Albert-Guy de Maupassant', 'Novel', 10, 2);
--rent
INSERT INTO BOOK VALUES (10014, '1-09', 'The Adventures of Sherlock Holmes', 'Arthur Conan Doyle', 'Story', 13, 5);
--available
INSERT INTO BOOK VALUES (10015, '1-10', 'The Adventures of Tom Sawyer', 'Mark Twain', 'Story', 10, 9);
--available
INSERT INTO BOOK VALUES (10016, '1-11', 'The Adventures of Huckleberry Finn', 'Mark Twain', 'Story', 7, 6);
--available
INSERT INTO BOOK VALUES (10017, '1-10', 'The Adventures of Tom Sawyer', 'Mark Twain', 'Story', 13, 4);
--available


INSERT INTO WANT_BOOK VALUES (1001, '0-05', '2022-11-14'); 
-- 1 want 0-05
INSERT INTO WANT_BOOK VALUES (1003, '0-05', '2022-11-12'); 
-- 3 want 0-05
INSERT INTO WANT_BOOK VALUES (1001, '0-04', '2022-11-14'); 
-- 1 want 0-04
INSERT INTO WANT_BOOK VALUES (1002, '0-04', '2022-11-12'); 
-- 2 want 0-04
INSERT INTO WANT_BOOK VALUES (1003, '1-07', '2022-11-15');
-- 3 want 1-07
INSERT INTO WANT_BOOK VALUES (1004, '1-08', '2022-11-17');
-- 4 want 1-08


INSERT INTO HAS_RENT VALUES (1001, 10001, '2022-11-12');
INSERT INTO HAS_RENT VALUES (1004, 10007, '2022-11-15');
INSERT INTO HAS_RENT VALUES (1005, 10004, '2022-11-18');
INSERT INTO HAS_RENT VALUES (1007, 10010, '2022-9-1');
INSERT INTO HAS_RENT VALUES (1006, 10013, '2022-11-12');


INSERT INTO HAS_PLACED VALUES (1003, 10002, '2022-11-14');
INSERT INTO HAS_PLACED VALUES (1005, 10005, '2022-11-16');
INSERT INTO HAS_PLACED VALUES (1007, 10009, '2022-11-18');
INSERT INTO HAS_PLACED VALUES (1006, 10012, '2022-11-18');


-- Commented opeartions for easy testing and debugging:

-- DElETE FROM WANT_BOOK;
-- DElETE FROM HAS_RENT;
-- DElETE FROM HAS_PLACED;
-- DElETE FROM USER_ACCOUNT;
-- DElETE FROM BOOK;

-- DROP TABLE USER_ACCOUNT;
-- DROP TABLE BOOK;
-- DROP TABLE WANT_BOOK;
-- DROP TABLE HAS_RENT;
-- DROP TABLE HAS_PLACED;

-- SELECT * FROM USER_ACCOUNT;
-- SELECT * FROM BOOK;
-- SELECT * FROM WANT_BOOK;
-- SELECT * FROM HAS_RENT;
-- Select * FROM HAS_PLACED;

-- UPDATE USER_ACCOUNT SET Notification = 'Notification: ';