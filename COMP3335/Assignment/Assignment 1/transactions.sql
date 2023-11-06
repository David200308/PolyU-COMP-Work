CREATE TABLE SalesTransaction (
    TxID INT NOT NULL,
    ProductID INT NOT NULL,
    ProductName VARCHAR(255) NOT NULL,
    UnitPrice DECIMAL(10, 2) NOT NULL,
    Qty INT NOT NULL,
    TotalPrice DECIMAL(10, 2) NOT NULL,
    CustomerName VARCHAR(255) NOT NULL,
    Salesperson VARCHAR(255) NOT NULL,
    CommRate VARCHAR(10) NOT NULL,
    PRIMARY KEY (TxID, ProductID)
);
