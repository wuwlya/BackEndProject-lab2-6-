package back.example.project.exception;

import org.springframework.http.HttpStatus;

public class CreateException extends ApiException {
    public CreateException(String message){
        super(HttpStatus.BAD_REQUEST,message);
    }
}
