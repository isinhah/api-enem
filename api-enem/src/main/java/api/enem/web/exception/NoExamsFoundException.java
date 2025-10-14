package api.enem.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoExamsFoundException extends RuntimeException {
    public NoExamsFoundException() {
        super("No exams found in the database. Please save exams before fetching questions.");
    }

    public NoExamsFoundException(String message) {
        super(message);
    }
}
