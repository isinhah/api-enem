package api.enem.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoQuestionsFoundException extends RuntimeException {
    public NoQuestionsFoundException(String message) {
        super(message);
    }
}
