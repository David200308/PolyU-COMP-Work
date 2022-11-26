package model;

import java.sql.SQLException;

public interface SQLModel {
    /**
     * push the class instance field data to the database
     * may apply the insert or update sql statement
     * @return the instance after push
     * @throws SQLException
     */
    public SQLModel pushToDatabase () throws SQLException;

    /**
     * pull and update the class instance field data from the database
     * apply the update sql statement
     * @return the instance after pull and update
     * @throws SQLException
     */
    public SQLModel pullFromDatabase () throws SQLException;

    /**
     * delete the class instance from the database
     * @throws SQLException
     */
    public void deleteFromDatabase() throws SQLException;
}
