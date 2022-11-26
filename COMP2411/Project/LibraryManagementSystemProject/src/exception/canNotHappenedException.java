package exception;

import java.sql.SQLException;

public class canNotHappenedException extends SQLException {
    public canNotHappenedException() {
        super();
    }
    public canNotHappenedException(String warn){
        super(warn);
    }

    public canNotHappenedException(String warn, Throwable cause){
        super(warn, cause);
    }

    public canNotHappenedException(Throwable cause){
        super(cause);
    }
}
