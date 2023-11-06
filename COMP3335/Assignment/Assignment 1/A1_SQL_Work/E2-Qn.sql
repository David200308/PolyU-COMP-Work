## 1
GRANT SELECT, INSERT, UPDATE, DELETE ON db.Salesperson TO 'Alice'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON db.Product TO 'Alice'@'%';
GRANT GRANT OPTION ON db.Salesperson TO 'Alice'@'%';
GRANT GRANT OPTION ON db.Product TO 'Alice'@'%';

## 2
GRANT SELECT, INSERT, UPDATE, DELETE ON db.Product TO 'Catherine'@'%';

## 3
GRANT SELECT ON db.SalesTransaction, db.SalesProducts TO 'David'@'%';

## 4
GRANT ALL PRIVILEGES ON db.* TO'Ewing'@'192.168.123.%';